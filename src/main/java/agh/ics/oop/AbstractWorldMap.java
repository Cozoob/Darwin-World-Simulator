package agh.ics.oop;
import java.util.LinkedHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected LinkedHashMap<Vector2d, Animal> animals;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;

    public AbstractWorldMap(){animals = new LinkedHashMap<>();}

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
    public boolean canMoveTo(Vector2d position) {return !(objectAt(position) instanceof Animal);}

    @Override
    public Object objectAt(Vector2d position) {
        if (animals.get(position) != null) {
            return animals.get(position);
        }
        return null;
    }

    public String toString(){return new MapVisualizer(this).draw(getLowerLeft(), getUpperRight());}

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = this.animals.get(oldPosition);
        this.animals.remove(oldPosition);
        this.animals.put(newPosition, animal);
    }

    public LinkedHashMap<Vector2d, Animal> getAnimals(){return this.animals;}

    public abstract Vector2d getLowerLeft();

    public abstract Vector2d getUpperRight();

    public abstract LinkedHashMap<Vector2d, Grass> getGrass();
}
