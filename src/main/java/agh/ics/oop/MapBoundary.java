package agh.ics.oop;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedSet<Vector2d> setSortedByX = new TreeSet<>(MapBoundary::compareOnXAxis);
    private final SortedSet<Vector2d> setSortedByY = new TreeSet<>(MapBoundary::compareOnYAxis);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        setSortedByX.remove(oldPosition);
        setSortedByY.remove(oldPosition);
        setSortedByX.add(newPosition);
        setSortedByY.add(newPosition);
    }

    private static int compareOnXAxis(Vector2d position1, Vector2d position2){
        return Integer.compare(position1.x, position2.x);
    }

    private static int compareOnYAxis(Vector2d position1, Vector2d position2){
        return Integer.compare(position1.y, position2.y);
    }

    public Vector2d getNewUpperRight(){
        int x = this.setSortedByX.last().x + 1; // dodaje 1 jednostke zeby pokazalo sie troszke wiecej mapy
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
