package agh.ics.oop;
import java.util.*;
import java.lang.Math;

public class GrassField extends AbstractWorldMap {
    public final LinkedHashMap<Vector2d, Grass> grassPositions;
    private final int upperBound;
    private Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private final MapBoundary boundary;

    public GrassField(int amountOfGrass){
        super();
        this.grassPositions = new LinkedHashMap<>();
        this.upperBound = (int)Math.sqrt(amountOfGrass*10) + 1;
        this.boundary = new MapBoundary(this);
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
        this.boundary.addPosition(randomVector);
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
        upperRight = this.boundary.getNewUpperRight();
        lowerLeft = this.boundary.getNewLowerLeft();
    }

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
    public boolean place(Animal animal){
        super.place(animal);
        this.boundary.addPosition(animal.getPosition());
        findCorners();
        return true;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        super.positionChanged(oldPosition, newPosition);
        findCorners();
        this.boundary.positionChanged(oldPosition, newPosition);
    }

    @Override
    public Vector2d getLowerLeft(){return this.lowerLeft;}

    @Override
    public Vector2d getUpperRight(){return this.upperRight;}

    @Override
    public LinkedHashMap<Vector2d, Grass> getGrass(){return this.grassPositions;}
}