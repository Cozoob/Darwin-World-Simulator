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
    private double width = 60;
    private double height = 60;
    public int energy;
    public ArrayList<Integer> genotype = new ArrayList<>();
    public boolean isAlive = true;
    public ArrayList<Animal> children = new ArrayList<>();
    public int daysAlive = 0;

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

    public Vector2d getPosition() {return position;}

    public MapDirection getMapDirection() {return mapDirection;}

    public IWorldMap getMap() {return map;}

    public String toString(){return mapDirection.toString();}

    public void addEnergy(int energy){this.energy = Math.min(this.energy + energy, this.map.getMaxAnimalEnergy());}

    public ArrayList<Integer> getGenotype() {
        return genotype;
    }

    public void removeEnergy(int energy) {
        this.energy = Math.max(this.energy - energy, 0);
        if(this.energy == 0){
            this.isAlive = false;
        }
    }

    public void move() {
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
                   Vector2d wrappedPosition = ((WrappedMap) map).wrapPosition(newPosition);
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
               canMoveTo = map.canMoveTo(this.position.add(newPosition));

               if(!canMoveTo && map instanceof WrappedMap){
                   // "wrap" the position if needed
                   Vector2d wrappedPosition = ((WrappedMap) map).wrapPosition(newPosition);
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
        if(this.map.getIsMagic() && this.map.getAliveAnimals().size() <= 5){
            map.magicHappen();
        }
        this.daysAlive++;
    }

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
            default -> throw new IllegalArgumentException("Value out of scope!");
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
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(width);
        imageView.setFitWidth(height);

        return imageView;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object other){
        return this == other;
    }

    @Override
    public Label getLabel() {return new Label(Integer.toString(this.energy));}

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

    public int getNumberOfChildren() {return this.children.size();}
}
