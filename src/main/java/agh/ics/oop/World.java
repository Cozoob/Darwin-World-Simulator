package agh.ics.oop;
import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        Direction moves[];
        moves = new Direction[args.length];
        int idx = 0;
        for(String arg : args){
            Direction move = switch (arg){
                case "f" -> Direction.FORWARD;
                case "l" -> Direction.LEFT;
                case "r" -> Direction.RIGHT;
                case "b" -> Direction.BACKWARD;
                default -> Direction.NONE;
            };
            moves[idx] = move;
            idx++;
        }


        // Direction[] moves = {Direction.FORWARD, Direction.FORWARD, Direction.RIGHT, Direction.LEFT};
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
                case NONE -> "NIGDZIE\n";
            };
            out.print(message);
        }
    }
}