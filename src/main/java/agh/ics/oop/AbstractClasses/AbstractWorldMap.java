package agh.ics.oop.AbstractClasses;
import agh.ics.oop.Engine.MapBoundary;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.Engine.MapVisualizer;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Random;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    public LinkedHashMap<Vector2d, Grass> grassPositions; // protected
    public LinkedHashMap<Vector2d, Animal> animals; // protected
    protected Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRight;
    public Vector2d jungleLowerLeft; // protected
    public Vector2d jungleUpperRight; // protected
    protected final MapBoundary mapBoundary;
    public boolean isFreePositionOnJungle = true; // protected
    public boolean isFreePositionOnPrairie = true; // protected
    public int numberOfAnimals; // protected
    public ArrayList<Vector2d> freeJunglePositions = new ArrayList<>(); // protected
    public ArrayList<Vector2d> freePrairiePositions = new ArrayList<>(); // protected
    // roslina poza jungle rosna w wiekszym rozproszeniu po prostu...

    // do usuniecia
    public int firstHalfOfGrass;
    public int secondHalfOfGrass;

    // sprawdz jakie wartosci z inputu moga cos zepsuc... wyrzuc wtedy wyjatki
    public AbstractWorldMap(int numberOfAnimals,int amountOfGrass, int width, int height, int jungleWidth, int jungleHeight){
        if (jungleHeight > height || jungleWidth > width){
            System.out.println("ZLE");
            // wyrzuc wyjatek!
        }
        this.numberOfAnimals = numberOfAnimals;
        this.animals = new LinkedHashMap<>();
        this.grassPositions = new LinkedHashMap<>();
        this.mapBoundary = new MapBoundary(this);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.jungleLowerLeft = new Vector2d( (int) Math.ceil((double) width / 2 -  (double) jungleWidth / 2), (int) Math.ceil((double)height / 2 - (double) jungleHeight / 2));
        this.jungleUpperRight = new Vector2d( (int) Math.ceil((double) width / 2 +  (double) jungleWidth / 2) - 1, (int) Math.ceil((double)height / 2 + (double) jungleHeight / 2) - 1);
        int firstHalfOfGrass = (int) Math.ceil((double)amountOfGrass / 2);
        int secondHalfOfGrass = amountOfGrass - firstHalfOfGrass;
        // start do usuniecia{
        this.firstHalfOfGrass = firstHalfOfGrass;
        this.secondHalfOfGrass = secondHalfOfGrass;
        // }koniec do usuniecia
        findFreeJunglePositions();
        findFreePrairiePositions();

        // first half in jungle
        for (int i = 0; i < firstHalfOfGrass; i++){
            if(this.freeJunglePositions.size() > 0) {
                putGrassOnJungle();
            }
        }
        // second half in prairie (outside the jungle)
        for (int i = 0; i < secondHalfOfGrass; i++){
            if(this.freePrairiePositions.size() > 0) {
                putGrassOnPrairie();
            }
        }
    }

    private void findFreeJunglePositions(){
        for(int x = this.jungleLowerLeft.x; x <= this.jungleUpperRight.x; x++){
            for(int y = this.jungleLowerLeft.y; y <= this.jungleUpperRight.y; y++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freeJunglePositions.add(vector2d);
                }
            }
        }
    }

    private void findFreePrairiePositions(){
        // for x in [lowerLeft.x, jungleLowerLeft) union (jungleUpperRight.x, upperRight.x]
        // for y in [lowerLeft.y, upperRight.y]
        for(int y = this.jungleLowerLeft.y; y <= this.upperRight.y; y++){
            for(int x = this.lowerLeft.x; x < this.jungleLowerLeft.x; x++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }

            for(int x = this.jungleUpperRight.x + 1; x <= this.upperRight.x; x++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }
        }

        // for x in [jungleLowerLeft.x, jungleUpperRight.x]
        // for y in [lowerLeft.y, jungleLowerLeft.y) union (jungleUpperRight.y, upperRight.y]
        for(int x = this.jungleLowerLeft.x; x <= this.jungleUpperRight.x; x++){
            for(int y = this.lowerLeft.y; y < this.jungleLowerLeft.y; y++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }

            for(int y = this.jungleUpperRight.y + 1; y <= this.upperRight.y; y++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }
        }
    }

    private void putGrassOnJungle(){
        Collections.shuffle(this.freeJunglePositions);
        int index = this.freeJunglePositions.size() - 1;
        Vector2d randomVector = this.freeJunglePositions.get(index);
        this.freeJunglePositions.remove(index);
        this.grassPositions.put(randomVector, new Grass(randomVector));
        this.mapBoundary.addPosition(randomVector);
    }

    private void putGrassOnPrairie(){
        Collections.shuffle(this.freePrairiePositions);
        int index = this.freePrairiePositions.size() - 1;
        Vector2d randomVector = this.freePrairiePositions.get(index);
        this.freePrairiePositions.remove(index);
        this.grassPositions.put(randomVector, new Grass(randomVector));
        this.mapBoundary.addPosition(randomVector);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
             this.animals.put(animal.getPosition(), animal);
            this.mapBoundary.addPosition(animal.getPosition());
            return true;
        }
        throw new IllegalArgumentException("\"" + animal.getPosition() + "\" field is invalid");
    }

    @Override
    public boolean isOccupied(Vector2d position) {return this.objectAt(position) != null;}

    @Override
    public abstract boolean canMoveTo(Vector2d position);

    @Override
    public Object objectAt(Vector2d position) {
        if (this.animals.get(position) != null) {
            return this.animals.get(position);
        }
        if (this.grassPositions.get(position) != null){
            return this.grassPositions.get(position);

        }
        return null;
    }

    public String toString(){return new MapVisualizer(this).draw(getLowerLeft(), getUpperRight());}

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = this.animals.get(oldPosition);
        this.animals.remove(oldPosition);
        this.animals.put(newPosition, animal);
        this.mapBoundary.positionChanged(oldPosition, newPosition);
    }

    public LinkedHashMap<Vector2d, Animal> getAnimals(){return this.animals;}

    public Vector2d getLowerLeft(){return this.lowerLeft;}

    public Vector2d getUpperRight(){return this.upperRight;}

    public LinkedHashMap<Vector2d, Grass> getGrass(){return this.grassPositions;}
}
