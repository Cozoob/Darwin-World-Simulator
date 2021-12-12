package agh.ics.oop.EnumClasses;

import agh.ics.oop.WorldElements.Vector2d;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString(){
        return switch(this){
            case EAST -> "E";
            case NORTHEAST -> "NE";
            case SOUTH -> "S";
            case SOUTHEAST -> "SE";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
            case NORTH -> "N";
        };
    }

    public MapDirection next(){
        return switch(this){
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public MapDirection previous(){
        return switch (this){
            case EAST -> NORTHEAST;
            case NORTHEAST -> NORTH;
            case NORTH -> NORTHWEST;
            case NORTHWEST -> WEST;
            case WEST -> SOUTHWEST;
            case SOUTHWEST -> SOUTH;
            case SOUTH -> SOUTHEAST;
            case SOUTHEAST -> EAST;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1,1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

}

/*
                 0
       315       ↑       45
         \       |       /
          \    NORTH    /
   NORTHWEST     |   NORTHEAST
            \    |    /
             \   |   /
270 <--WEST--   MAP  --EAST--> 90
             /   |   \
            /    |    \
      SOUTHWEST  |   SOUTHEAST
          /    SOUTH    \
         /       |       \
       225       ↓       135
                180
 */