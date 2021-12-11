package agh.ics.oop;
import java.util.LinkedHashMap;


public class RectangularMap extends AbstractWorldMap{
    public final int width;
    public final int height;
    private final Vector2d upperRight;
    private final Vector2d lowerLeft = new Vector2d(0,0);

    public RectangularMap(int width, int height){
        if (0 < width && 0 < height) {
            this.width = width;
            this.height = height;
        }
        else {
            this.width = 4;
            this.height = 4;
        }
        this.animals = new LinkedHashMap<>();
        this.upperRight = new Vector2d(width - 1, height - 1);
    }

    private boolean isOnMap(Vector2d position){return position.follows(lowerLeft) && position.precedes(upperRight);}

    @Override
    public boolean canMoveTo(Vector2d position) {return isOnMap(position) && !isOccupied(position);}

    @Override
    public String toString(){return new MapVisualizer(this).draw(lowerLeft, upperRight);}

    @Override
    public Vector2d getLowerLeft(){return this.lowerLeft;}

    @Override
    public Vector2d getUpperRight(){return this.upperRight;}

    @Override
    public LinkedHashMap<Vector2d, Grass> getGrass() {
        return null;
    }
}
