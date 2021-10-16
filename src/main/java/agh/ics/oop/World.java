package agh.ics.oop;
import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        out.print("system wystartował\n");
        run();
        out.print("system zakończył działanie");
    }

    public static void run(){
        out.print("zwierze idzie do przodu\n");
    }
}