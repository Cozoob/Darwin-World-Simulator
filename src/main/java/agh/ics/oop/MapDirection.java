package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString(){
        return switch(this){
            case EAST -> "Wschod";
            case SOUTH -> "Poludnie";
            case WEST -> "Zachod";
            case NORTH -> "Polnoc";
        };
    }

    public MapDirection next(){
        return switch(this){
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case NORTH -> EAST;
        };
    }

    public MapDirection previous(){
        return switch (this){
            case EAST -> NORTH;
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH, SOUTH -> new Vector2d(0, 1);
            case EAST, WEST -> new Vector2d(1, 0);
        };
    }

}

