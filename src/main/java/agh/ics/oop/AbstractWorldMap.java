package agh.ics.oop;

import java.util.ArrayList;

abstract class AbstractWorldMap implements IWorldMap{
    protected ArrayList<Animal> animals;

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
        return null;
    }

    public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), new Vector2d(4,4));}
}
