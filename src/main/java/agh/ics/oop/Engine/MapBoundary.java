package agh.ics.oop.Engine;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedSet<Vector2d> setSortedByX = new TreeSet<>(MapBoundary::compareOnXAxis);
    private final SortedSet<Vector2d> setSortedByY = new TreeSet<>(MapBoundary::compareOnYAxis);
    private final IWorldMap map;

    public MapBoundary(IWorldMap map){this.map = map;}

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        if(!(map.objectAt(oldPosition) instanceof Grass)) {
            setSortedByX.remove(oldPosition);
            setSortedByY.remove(oldPosition);
        }
        setSortedByX.add(newPosition);
        setSortedByY.add(newPosition);
    }

    private static int compareOnXAxis(Vector2d position1, Vector2d position2){
        if(position1.x>position2.x){return 1;}
        else if(position1.x < position2.x) {return -1;}
        return position1.y-position2.y;
    }

    private static int compareOnYAxis(Vector2d position1, Vector2d position2){
        if(position1.y>position2.y){return 1;}
        else if(position1.y < position2.y) {return -1;}
        return position1.x-position2.x;
    }

    public Vector2d getNewUpperRight(){
        int x = this.setSortedByX.last().x + 1;
        int y = this.setSortedByY.last().y + 1;
        return new Vector2d(x, y);
    }

    public Vector2d getNewLowerLeft(){
        int x = this.setSortedByX.first().x - 1;
        int y = this.setSortedByY.first().y - 1;
        return new Vector2d(x, y);
    }

    public void addPosition(Vector2d position){
        this.setSortedByX.add(position);
        this.setSortedByY.add(position);
    }
}
