package agh.ics.oop;
import java.util.LinkedHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected LinkedHashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    protected Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRight = new Vector2d(4, 4);

    public AbstractWorldMap(){
        animals = new LinkedHashMap<>();
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
             this.animals.put(animal.getPosition(), animal);
            return true;
        }
        throw new IllegalArgumentException("\"" + animal.getPosition() + "\" field is invalid");
    }

    @Override
    public boolean isOccupied(Vector2d position) {return this.objectAt(position) != null;}

    @Override
    public Object objectAt(Vector2d position) {
        if (animals.get(position) != null) {
            return animals.get(position);
        }
        return null;
    }

    public String toString(){return new MapVisualizer(this).draw(this.lowerLeft, this.upperRight);}

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = this.animals.get(oldPosition);
        this.animals.remove(oldPosition);
        this.animals.put(newPosition, animal);
    }

    public Vector2d getLowerLeft(){return this.lowerLeft;}

    public Vector2d getUpperRight(){return this.upperRight;}
    
    public LinkedHashMap<Vector2d, Animal> getAnimals(){return this.animals;}

    public abstract LinkedHashMap<Vector2d, Grass> getGrass();
}
