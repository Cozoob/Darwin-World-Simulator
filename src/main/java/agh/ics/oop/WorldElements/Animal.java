package agh.ics.oop.WorldElements;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.EnumClasses.MapDirection;
import agh.ics.oop.EnumClasses.MoveDirection;
import agh.ics.oop.Maps.WrappedMap;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection mapDirection;
    private final AbstractWorldMap map;
    private final ArrayList<IPositionChangeObserver> positionObservers = new ArrayList<>();
    private int width = 50;
    private int height = 70; // RATIO 5:7 -> WIDTH : HEIGHT
    public int energy; // private
    public ArrayList<Integer> genotype = new ArrayList<>();
    public boolean isAlive = true; // protected
    public ArrayList<Animal> children = new ArrayList<>(); // protected

    public Animal(AbstractWorldMap map, Vector2d initialPosition, int energy){
        this.map = map;
        this.position = initialPosition;
        this.energy = energy;
        this.mapDirection = MapDirection.NORTH;
        // each animal needs to have at least one gene of each kind
        for(int i = 0; i < 8; i++){
            this.genotype.add(i);
        }
        addObserver(map);
    }

    // getter
    public Vector2d getPosition() {return position;}

    // getter
    public MapDirection getMapDirection() {return mapDirection;}

    // getter
    public IWorldMap getMap() {return map;}

    public String toString(){return mapDirection.toString();}

    public void addEnergy(int energy){this.energy = Math.min(this.energy + energy, this.map.maxAnimalEnergy);}

    public void removeEnergy(int energy) {
        this.energy = Math.max(this.energy - energy, 0);
        if(this.energy == 0){
            this.isAlive = false;
        }
    }

    public void move() {
        if(!isAlive){ // extra safety
            return;
        }
        removeEnergy(1); // each day is minus 1 energy for the animal
        if(!isAlive) {
            map.removeDeadAnimal(this);
            return;
        }

        boolean canMoveTo = false;
        MoveDirection direction = chooseNewDirection();
        Vector2d oldPosition = this.position;
        switch (direction){
           case FORWARD -> {
               Vector2d newPosition = this.position.add(this.mapDirection.toUnitVector());
               canMoveTo = map.canMoveTo(newPosition);

               if(!canMoveTo && map instanceof WrappedMap){
                   // "wrap" the position if needed
                   Vector2d wrappedPosition = wrapPosition(newPosition);
                   newPosition.x = wrappedPosition.x;
                   newPosition.y = wrappedPosition.y;
                   canMoveTo = true;
               }

               if(canMoveTo){
                    this.position = newPosition;
               }
           }
           case BACKWARD -> {
               Vector2d newPosition = this.position.add(this.mapDirection.toUnitVector().opposite());
//               System.out.println(newPosition);
               canMoveTo = map.canMoveTo(this.position.add(newPosition));

               if(!canMoveTo && map instanceof WrappedMap){
                   // "wrap" the position if needed
                   Vector2d wrappedPosition = wrapPosition(newPosition);
                   newPosition.x = wrappedPosition.x;
                   newPosition.y = wrappedPosition.y;
                   canMoveTo = true;
               }

               if(canMoveTo){
                   this.position = newPosition;
               }
           }

           case FORWARDLEFT -> this.mapDirection = this.mapDirection.previous();
           case LEFT -> this.mapDirection = this.mapDirection.previous().previous();
           case BACKWARDLEFT -> this.mapDirection = this.mapDirection.previous().previous().previous();

           case FORWARDRIGHT -> this.mapDirection = this.mapDirection.next();
           case RIGHT -> this.mapDirection = this.mapDirection.next().next();
           case BACKWARDRIGHT -> this.mapDirection = this.mapDirection.next().next().next();
        }
        positionChanged(oldPosition);
        if(canMoveTo && this.energy > 0){
            // animal has moved
            this.map.updateFreePositions(oldPosition, this.getPosition());
            this.map.updateListOfAnimals(this, oldPosition);
        }
    }

    private Vector2d wrapPosition(Vector2d newPosition){
        Vector2d upperRight = map.getUpperRight();
        Vector2d lowerLeft = map.getLowerLeft();
        if (newPosition.x < lowerLeft.x) {
            newPosition.x = upperRight.x;
        }
        if (newPosition.x > upperRight.x) {
            newPosition.x = lowerLeft.x;
        }
        if (newPosition.y < lowerLeft.y) {
            newPosition.y = upperRight.y;
        }
        if (newPosition.y > upperRight.y) {
            newPosition.y = lowerLeft.y;
        }
        return newPosition;
    }

    // jesli dobrze rozumiem zwierze nie wykonuje obrotu o 0 stopni ani o 180... gdy jest gen 0 lub 4
    // (odpowiednio 0 lub 180 stopni) to zwierze albo idzie do przodu albo do tylu
    private MoveDirection chooseNewDirection(){
        Collections.shuffle(this.genotype);
        int gene = this.genotype.get(0) * 45; // choose the random (because of the previous shuffle) gene [degrees]

        switch (gene){
            case 0 -> {return MoveDirection.FORWARD;}
            case 45 -> {return MoveDirection.FORWARDRIGHT;}
            case 90 -> {return MoveDirection.RIGHT;}
            case 135 -> {return MoveDirection.BACKWARDRIGHT;}
            case 180 -> {return MoveDirection.BACKWARD;}
            case 225 -> {return MoveDirection.BACKWARDLEFT;}
            case 270 -> {return MoveDirection.LEFT;}
            case 315 -> {return MoveDirection.FORWARDLEFT;}
            default -> {return MoveDirection.BACKWARD;} // tutaj wyrzuc wyjatkiem!
        }
    }

    public void addObserver(IPositionChangeObserver observer){this.positionObservers.add(observer);}

    public void removeObserver(IPositionChangeObserver observer){this.positionObservers.remove(observer);}

    private void positionChanged(Vector2d oldPosition){
        for (IPositionChangeObserver observer : positionObservers){
            try {
                observer.positionChanged(oldPosition, this.position, this);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public ImageView getImageView() throws FileNotFoundException {
        String url;

        switch(this.getMapDirection()){
            case NORTH -> url = "src/main/resources/animal/icon-north.png";
            case NORTHEAST -> url = "src/main/resources/animal/icon-northeast.png";
            case EAST -> url = "src/main/resources/animal/icon-east.png";
            case SOUTHEAST -> url = "src/main/resources/animal/icon-southeast.png";
            case SOUTH -> url = "src/main/resources/animal/icon-south.png";
            case SOUTHWEST ->  url = "src/main/resources/animal/icon-southwest.png";
            case WEST -> url = "src/main/resources/animal/icon-west.png";
            case NORTHWEST ->  url = "src/main/resources/animal/icon-northwest.png";
            default -> throw new IllegalStateException("Unexpected value: " + this.getMapDirection());
        }


        Image image = new Image(new FileInputStream(url));

        // musze zmienic tak zeby kazde zdjecie mialo ten sam rozmiar...
        // wtedy nie bedzie problemu ze skalowaniem tego animala
        ImageView imageView = new ImageView(image);
        if (this.getMapDirection().equals(MapDirection.NORTH) || this.getMapDirection().equals(MapDirection.SOUTH)){
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
        } else {
            imageView.setFitHeight(width);
            imageView.setFitWidth(height);
        }

        return imageView;
    }

    @Override
    public Label getLabel() {return new Label(this.getPosition().toString());}

    public void setHeight(int height) {this.height = height;}

    public void setWidth(int width) {this.width = width;}

    public void addNewChildren(Animal animal){
        this.children.add(animal);
    }

    public void fillTheGenes(){
        // only used for the "Adam and Eve" animals
        while(this.genotype.size() < 32){
            Random rand = new Random();
            int i = rand.nextInt(8);
            this.genotype.add(i);
        }
    }
}
