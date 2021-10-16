package agh.ics.oop;
import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        // String[] moves = {"f", "f", "r", "l", "o", "c"};
        Direction[] moves = {Direction.FORWARD, Direction.FORWARD, Direction.RIGHT, Direction.LEFT};
        out.print("Start\n");
        run(moves);
        out.print("Stop");
    }

    public static void run(Direction[] moves) {
        for(Direction move : moves){
            String message = switch(move){
                case BACKWARD -> "Zwierzak idzie do tyłu\n";
                case LEFT -> "Zwierzak skręca w lewo\n";
                case RIGHT -> "Zwierzak skręca w prawo\n";
                case FORWARD -> "Zwierzak idzie do przodu\n";
            };
            out.print(message);
        }
    }
}