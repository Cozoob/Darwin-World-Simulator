package agh.ics.oop;
import static java.lang.System.out;
/* napisz hashcode */

class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y= y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other){
        return this.x == other.x && this.y == other.y;

    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Integer.max(this.x, other.x),Integer.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Integer.min(this.x, other.x),Integer.min(this.y, other.y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    // comparing objects
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    // comparing objects with hashcode()
    public boolean equals2(Object other){
        boolean isHashCodeEquals = this.hashCode() == other.hashCode();
        if (isHashCodeEquals)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite(){
        return new Vector2d(this.y, this.x);
    }

}
public class World {
    public static void main(String[] args){
        Vector2d V1 = new Vector2d(1, 2);
        // out.print(V1);
        Vector2d V2 = new Vector2d(1, 2);
        // out.print(V1.precedes(V2));
        // out.print(V1.equals(V2));
//        out.print(V1.equals2(V2) + "\n");
//        out.print(V1.hashCode() + "\n");
//        out.print(V2.hashCode());
        out.print(V1.opposite());

    }



}