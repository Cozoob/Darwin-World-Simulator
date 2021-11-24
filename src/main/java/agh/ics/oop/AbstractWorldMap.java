package agh.ics.oop;
import java.util.LinkedHashMap;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected LinkedHashMap<Vector2d, Animal> animals = new LinkedHashMap<>();

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
             this.animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
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

    public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), new Vector2d(4,4));}

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = this.animals.get(oldPosition);
        this.animals.remove(oldPosition);
        this.animals.put(newPosition, animal);
    }
}
