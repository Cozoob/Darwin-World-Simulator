package agh.ics.oop;

import java.util.Map;

public class Animal {
    private Vector2d position;
    private MapDirection mapDirection;
    private final static Vector2d downLeftCorner = new Vector2d(0,0);
    private final static Vector2d upRightCorner = new Vector2d(4,4);

    public Animal(){
        mapDirection = MapDirection.NORTH;
        position = new Vector2d(2,2);
    }

    // getter
    public Vector2d getPosition() {return position;}

    // getter
    public MapDirection getMapDirection() {return mapDirection;}

    public String toString(){return position.toString() + ", " + mapDirection.toString();}

    public void move(MoveDirection direction){
       switch (direction){
           case FORWARD -> {
               Vector2d newPosition = this.position.add(this.mapDirection.toUnitVector());
               if(newPosition.follows(downLeftCorner) && newPosition.precedes(upRightCorner)){
                    this.position = newPosition;
               }
           }
           case BACKWARD -> {
               Vector2d newPosition = this.position.add(this.mapDirection.toUnitVector().opposite());
               if(newPosition.follows(downLeftCorner) && newPosition.precedes(upRightCorner)){
                   this.position = newPosition;
               }
           }
           case LEFT -> this.mapDirection = this.mapDirection.previous();
           case RIGHT -> this.mapDirection = this.mapDirection.next();
       }
    }



}
