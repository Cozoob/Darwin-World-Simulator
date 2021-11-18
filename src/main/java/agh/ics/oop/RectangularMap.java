package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap extends AbstractWorldMap{
    public final int width;
    public final int height;
    private Vector2d endCorner;
    private final Vector2d startCorner = new Vector2d(0,0);

    public RectangularMap(int width, int height){
        if (0 < width && 0 < height) {
            this.width = width;
            this.height = height;
        }
        else {
            this.width = 4;
            this.height = 4;
        }
        this.animals = new ArrayList<Animal>();
        this.endCorner = new Vector2d(width - 1, height - 1);
    }

    private boolean isOnMap(Vector2d position){
        return position.follows(startCorner) && position.precedes(endCorner);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {return isOnMap(position) && !isOccupied(position);}

    @Override
    public String toString(){return new MapVisualizer(this).draw(startCorner, endCorner);}
}
