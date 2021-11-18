package agh.ics.oop;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;


public class GrassField extends AbstractWorldMap {
    private final ArrayList<Grass> grassPositions;
    private final int upperBound;
    //private ArrayList<Animal> animals;
    private Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

    public GrassField(int amountOfGrass){
        this.grassPositions = new ArrayList<Grass>();
        this.upperBound = (int)Math.sqrt(amountOfGrass*10) + 1;
        this.animals = new ArrayList<Animal>();
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

        this.grassPositions.add(new Grass(randomVector));
    }

    private boolean canPutGrass(Vector2d position){
        for(Grass grass : this.grassPositions){
            if(grass.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private void findCorners(){
        // szukam wektorow lowerLeft oraz upperRight w grassPositions i animals
        for (Grass grass : this.grassPositions){
            this.lowerLeft = this.lowerLeft.lowerLeft(grass.getPosition());
            this.upperRight = this.upperRight.upperRight(grass.getPosition());
        }
        for(Animal animal : this.animals){
            this.lowerLeft = this.lowerLeft.lowerLeft(animal.getPosition());
            this.upperRight = this.upperRight.upperRight(animal.getPosition());
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
        for (Grass grass : this.grassPositions){
            if (grass.getPosition().equals(position)){
                return grass;
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