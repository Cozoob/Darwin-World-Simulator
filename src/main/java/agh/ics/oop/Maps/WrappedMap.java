package agh.ics.oop.Maps;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.LinkedHashMap;

// "zawinięta" mapa
public class WrappedMap extends AbstractWorldMap {
    public WrappedMap(int numberOfAnimals, int amountOfGrass, int width, int height, int jungleWidth, int jungleHeight) {
        super(numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        // firstly "wrap" the position if needed
        if (position.x < 0) {
            position.x = this.upperRight.x;
        }
        if (position.x > this.upperRight.x) {
            position.x = this.lowerLeft.x;
        }
        if (position.y < 0) {
            position.y = this.upperRight.y;
        }
        if (position.y > this.upperRight.y) {
            position.y = this.lowerLeft.y;
        }
        return true;
    }
}
