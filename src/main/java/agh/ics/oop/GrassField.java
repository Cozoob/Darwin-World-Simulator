package agh.ics.oop;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;


public class GrassField implements IWorldMap {
    private ArrayList<Grass> grassPositions;
    private final int upperBound;
    private ArrayList<Animal> animals;
    private Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vector2d upperRight = new Vector2d(0, 0);
    private Object Animal;

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
//        Random rand = new Random(103); - do testow
        Vector2d randomVector = new Vector2d(rand.nextInt(this.upperBound), rand.nextInt(this.upperBound));
        boolean isPut = false; // nie podoba mi sie to, ale nie wiem jak inaczej
        while(!isPut) {
            if (canPutGrass(randomVector)) {
                this.grassPositions.add(new Grass(randomVector));
                isPut = true;
            } else {
                randomVector = new Vector2d(rand.nextInt(this.upperBound), rand.nextInt(this.upperBound));
            }
        }
    }

    private boolean canPutGrass(Vector2d position){
        for(Grass grass : this.grassPositions){
            if(grass.position.equals(position)) {
                return false;
            }
        }
        return true;
    }

    private void findCorners(){
        // szukam wektorow lowerLeft oraz upperRight w grassPositions i animals
        for (Grass grass : this.grassPositions){
            this.lowerLeft = this.lowerLeft.lowerLeft(grass.position);
            this.upperRight = this.upperRight.upperRight(grass.position);
        }
        for(Animal animal : this.animals){
            this.lowerLeft = this.lowerLeft.lowerLeft(animal.getPosition());
            this.upperRight = this.upperRight.upperRight(animal.getPosition());
        }
    }

    private boolean isOnMap(Vector2d position){
        return position.follows(new Vector2d(0,0));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Object object = objectAt(position);
        if(object != null && object.getClass().equals(this.Animal)){
            return false;
        }
        return isOnMap(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            this.animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {return this.objectAt(position) != null;}

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : this.animals){
            if (animal.getPosition().equals(position)){
                return animal;
            }
        }
        for (Grass grass : this.grassPositions){
            if (grass.position.equals(position)){
                return grass;
            }
        }

        return null;
    }

    public String toString(){
        findCorners();
        return new MapVisualizer(this).draw(this.lowerLeft, this.upperRight);
    }
}
