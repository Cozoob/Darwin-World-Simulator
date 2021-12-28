package agh.ics.oop.Maps;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.WorldElements.Vector2d;



public class WrappedMap extends AbstractWorldMap {


    public WrappedMap(boolean isMagic, int minimumEnergyToCopulate, int maxAnimalEnergy, int grassEnergy, int amountOfGrass, int width, int height, int jungleWidth, int jungleHeight) {
        super(isMagic, minimumEnergyToCopulate, maxAnimalEnergy, grassEnergy, amountOfGrass, width, height, jungleWidth, jungleHeight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }

    public Vector2d wrapPosition(Vector2d newPosition){
        Vector2d upperRight = this.getUpperRight();
        Vector2d lowerLeft = this.getLowerLeft();
        if (newPosition.x < lowerLeft.x) {
            newPosition.x = upperRight.x;
        }
        if (newPosition.x > upperRight.x) {
            newPosition.x = lowerLeft.x;
        }
        if (newPosition.y < lowerLeft.y) {
            newPosition.y = upperRight.y;
        }
        if (newPosition.y > upperRight.y) {
            newPosition.y = lowerLeft.y;
        }
        return newPosition;
    }
}
