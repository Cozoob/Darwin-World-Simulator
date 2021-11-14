package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap extends AbstractWorldMap{
    public final int width;
    public final int height;
    private Vector2d endCorner;

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
    public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), endCorner);}
}
