package agh.ics.oop;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.System.out;

public class World {

    public static void main(String[] args){
        Animal dog = new Animal();
        out.println(dog);
//        dog.move(MoveDirection.RIGHT);
//        dog.move(MoveDirection.FORWARD);
//        dog.move(MoveDirection.FORWARD);
//        dog.move(MoveDirection.FORWARD);
//        out.println(dog);
        String[] moves = {"r", "f", "f", "t", "forward", "c"};
        OptionsParser parser = new OptionsParser();
        ArrayList<MoveDirection> realMoves = parser.parse(moves);
        for(MoveDirection move : realMoves){
            dog.move(move);
            out.println(dog);
        }
    }



}