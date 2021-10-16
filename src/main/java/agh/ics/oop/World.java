package agh.ics.oop;
import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        String[] moves = {"forward", "left"};
        out.print("system wystartował\n");
        run(moves);
        out.print("system zakończył działanie");
    }

    public static void run(String[] strArray) {
        out.print("zwierze idzie do przodu\n");
        for(int i = 0; i < strArray.length - 1; i++) {
            out.print(strArray[i]);
            out.print(", ");
        }
        out.print(strArray[strArray.length - 1]);
        out.print("\n");
    }
}