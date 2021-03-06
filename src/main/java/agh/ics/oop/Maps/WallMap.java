package agh.ics.oop.Maps;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.WorldElements.Vector2d;




public class WallMap extends AbstractWorldMap {


    public WallMap(boolean isMagic, int minimumEnergyToCopulate, int maxAnimalEnergy, int grassEnergy, int amountOfGrass, int width, int height, int jungleWidth, int jungleHeight) {
        super(isMagic, minimumEnergyToCopulate, maxAnimalEnergy, grassEnergy, amountOfGrass, width, height, jungleWidth, jungleHeight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }
}