package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap{
    public final int width;
    public final int height;
    private Vector2d endCorner;
    private ArrayList<Animal> animals;

    public RectangularMap(int width, int height){
        if (0 < width && 0 < height) {
            this.width = width;
            this.height = height;
            this.endCorner = new Vector2d(width - 1, height - 1);
        }
        else {
            this.width = 4;
            this.height = 4;
        }
        this.animals = new ArrayList<Animal>();
    }

    private boolean isOnMap(Vector2d position){
        return position.follows(new Vector2d(0,0)) && position.precedes(endCorner);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {return isOnMap(position) && !isOccupied(position);}

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.add(animal);
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
        return null;
    }

    public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), endCorner);}
}
