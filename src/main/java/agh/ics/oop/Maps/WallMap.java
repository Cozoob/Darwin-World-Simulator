package agh.ics.oop.Maps;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.LinkedHashMap;

// mapa z "murem"
public class WallMap extends AbstractWorldMap {


    public WallMap(int maxAnimalEnergy, int grassEnergy, int numberOfAnimals, int amountOfGrass, int width, int height, int jungleWidth, int jungleHeight) {
        super(maxAnimalEnergy, grassEnergy, numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }
}