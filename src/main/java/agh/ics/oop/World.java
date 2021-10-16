package agh.ics.oop;
import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        String[] moves = {"f", "f", "r", "l", "o", "c"};
        out.print("Start\n");
        run(moves);
        out.print("Stop");
    }

    public static void run(String[] strArray) {

        for(String str : strArray) {
            String message = switch(str) {
                case "f" -> "Zwierzak idzie do przodu\n";
                case "l" -> "Zwierzak skręca w lewo\n";
                case "b" -> "Zwierzak idzie do tyłu\n";
                case "r" -> "Zwierzak skręca w prawo\n";
                default -> "Nieznana komenda";
                };
            if(!message.equals("Nieznana komenda")) {
                out.print(message);

            }
        }
    }
}