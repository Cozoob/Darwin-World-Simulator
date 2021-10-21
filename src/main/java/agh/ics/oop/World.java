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
        return "(" + Integer.toString(this.x) + "," + Integer.toString(this.y) + ")";
    }

}
public class World {
    public static void main(String[] args){
        Vector2d V = new Vector2d(1, 2);
        out.print(V.toString());
    }
}