package agh.ics.oop;

import java.util.Map;

public class Animal{
    private Vector2d position;
    private MapDirection mapDirection;
    private IWorldMap map;
    private final static Vector2d downLeftCorner = new Vector2d(0,0);
    private final static Vector2d upRightCorner = new Vector2d(4,4);

    public Animal(){
        mapDirection = MapDirection.NORTH;
        position = new Vector2d(2,2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
    }

    // getter
    public Vector2d getPosition() {return position;}

    // getter
    public MapDirection getMapDirection() {return mapDirection;}

    public String toString(){return mapDirection.toString();}

    public void move(MoveDirection direction){
        Vector2d newPosition = this.position.add(this.mapDirection.toUnitVector());
       switch (direction){
           case FORWARD -> {
               boolean isMoveTo = map.canMoveTo(newPosition);
               if(isMoveTo){
                    this.position = newPosition;
               }
           }
           case BACKWARD -> {
               boolean isMoveTo = map.canMoveTo(newPosition.opposite());
               if(isMoveTo){
                   this.position = newPosition.opposite();
               }
           }
           case LEFT -> this.mapDirection = this.mapDirection.previous();
           case RIGHT -> this.mapDirection = this.mapDirection.next();
       }
    }

}
