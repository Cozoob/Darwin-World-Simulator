package agh.ics.oop;

public class RectangularMap implements IWorldMap{
    public final int width;
    public final int height;

    public RectangularMap(int width, int height){
        if (0 < width && 0 < height) {
            this.width = width;
            this.height = height;
        }
        else {
            this.width = 4;
            this.height = 4;
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(new Vector2d(0,0)) && position.precedes(new Vector2d(width, height)));
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }
}
