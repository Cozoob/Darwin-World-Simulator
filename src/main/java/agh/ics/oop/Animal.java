package agh.ics.oop;

import java.util.Map;

public class Animal {
    private Vector2d position = new Vector2d(2,2);
    private MapDirection direction = MapDirection.NORTH;

    public String toString(){
        return position.toString() + ", " + direction.toString();
    }

    public void move(MoveDirection direction){
        if (direction == MoveDirection.RIGHT){
            this.direction = this.direction.next();

        } else if (direction == MoveDirection.LEFT){
            this.direction = this.direction.previous();

        } else if (direction == MoveDirection.FORWARD){

            if (this.direction == MapDirection.NORTH && position.y < 4){
                // do gory
                position.add(new Vector2d(0, 1));
            } else if(this.direction == MapDirection.SOUTH && position.y > 0){
                // w dol
                position.add(new Vector2d(0, -1));
            } else if (this.direction == MapDirection.EAST && position.x < 4){
                // w prawo
                position.add(new Vector2d(1, 0));
            } else if (this.direction == MapDirection.WEST && position.x > 0){
                // w lewo
                position.add(new Vector2d(-1, 0));
            }
        } else if (direction == MoveDirection.BACKWARD){

            if(this.direction == MapDirection.NORTH && position.y > 0){
                // w dol
                position.add(new Vector2d(0, -1));
            } else if (this.direction == MapDirection.SOUTH && position.y < 4){
                // w gore
                position.add(new Vector2d(0, 1));
            } else if (this.direction == MapDirection.EAST && position.x > 0){
                // w lewo
                position.add(new Vector2d(-1, 0));
            } else if (this.direction == MapDirection.WEST && position.x < 4){
                // w prawo
                position.add(new Vector2d(1, 0));
            }
        }


    }



}
