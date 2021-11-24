package agh.ics.oop;

import java.util.ArrayList;

public class Animal{
    private Vector2d position;
    private MapDirection mapDirection;
    private IWorldMap map;
    private final static Vector2d downLeftCorner = new Vector2d(0,0);
    private final static Vector2d upRightCorner = new Vector2d(4,4);
    private final ArrayList<IPositionChangeObserver> positionObservers = new ArrayList<>();

    public Animal(){
        mapDirection = MapDirection.NORTH;
        position = new Vector2d(2,2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.mapDirection = MapDirection.NORTH;
        if(map instanceof IPositionChangeObserver){
            addObserver((IPositionChangeObserver) map);
        }
    }

    // getter
    public Vector2d getPosition() {return position;}

    // getter
    public MapDirection getMapDirection() {return mapDirection;}

    // getter
    public IWorldMap getMap() {return map;}

    public String toString(){return mapDirection.toString();}

    public void move(MoveDirection direction){
        Vector2d oldPosition = new Vector2d(this.position.x, this.position.y);
       switch (direction){
           case FORWARD -> {
               boolean isMoveTo = map.canMoveTo(this.position.add(this.mapDirection.toUnitVector()));
               if(isMoveTo){
                    this.position = this.position.add(this.mapDirection.toUnitVector());
               }
           }
           case BACKWARD -> {
               boolean isMoveTo = map.canMoveTo(this.position.add(this.mapDirection.toUnitVector().opposite()));
               if(isMoveTo){
                   this.position = this.position.add(this.mapDirection.toUnitVector().opposite());
               }
           }
           case LEFT -> this.mapDirection = this.mapDirection.previous();
           case RIGHT -> this.mapDirection = this.mapDirection.next();
       }
       positionChanged(oldPosition);
    }
    void addObserver(IPositionChangeObserver observer){this.positionObservers.add(observer);}

    void removeObserver(IPositionChangeObserver observer){this.positionObservers.remove(observer);}

    protected void positionChanged(Vector2d oldPosition){
        if (!this.position.equals(oldPosition)){
            for (IPositionChangeObserver observer : positionObservers){
                observer.positionChanged(oldPosition, this.position);
            }
        }
    }
}
