package agh.ics.oop;
import java.util.*;
import java.lang.Math;

public class GrassField extends AbstractWorldMap {
    private final LinkedHashMap<Vector2d, Grass> grassPositions;
    private final int upperBound;
    private Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

    public GrassField(int amountOfGrass){
        this.grassPositions = new LinkedHashMap<>();
        this.upperBound = (int)Math.sqrt(amountOfGrass*10) + 1;
        this.animals = new LinkedHashMap<>();
        for (int i = 0; i < amountOfGrass; i++){
            putGrass();
        }
    }

    private void putGrass(){
        Random rand = new Random();
//        Random rand = new Random(103); // - do testow
        Vector2d randomVector;

        do {
           randomVector = new Vector2d(rand.nextInt(this.upperBound), rand.nextInt(this.upperBound));
        } while (!canPutGrass(randomVector));

        this.grassPositions.put(randomVector, new Grass(randomVector));
    }

    private boolean canPutGrass(Vector2d position){
        for(Vector2d vector : this.grassPositions.keySet()){
            if(vector.equals(position)) {
                return false;
            }
        }
        return true;
    }

    private void findCorners(){
        // szukam wektorow lowerLeft oraz upperRight w grassPositions i animals
        for (Vector2d vector : this.grassPositions.keySet()){
            this.lowerLeft = this.lowerLeft.lowerLeft(vector);
            this.upperRight = this.upperRight.upperRight(vector);
        }
        for(Vector2d vector : this.animals.keySet()){
            this.lowerLeft = this.lowerLeft.lowerLeft(vector);
            this.upperRight = this.upperRight.upperRight(vector);
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) { return !( objectAt(position) instanceof Animal);}

    @Override
    public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        if (object instanceof Animal){
            return object;
        }
        for (Vector2d vector : this.grassPositions.keySet()){
            if (vector.equals(position)){
                return grassPositions.get(vector);
            }
        }
        return null;
    }

    @Override
    public String toString(){
        findCorners();
        return new MapVisualizer(this).draw(this.lowerLeft, this.upperRight);
    }
}