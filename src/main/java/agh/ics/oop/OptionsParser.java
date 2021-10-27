package agh.ics.oop;

import java.util.ArrayList;

public class OptionsParser {

    public ArrayList<MoveDirection> parse(String[] moves){
        ArrayList<MoveDirection> result = new ArrayList<>();

        for(String move : moves){
            switch (move){
                case "f", "forward" -> result.add(MoveDirection.FORWARD);
                case "b", "backward" -> result.add(MoveDirection.BACKWARD);
                case "l", "left" -> result.add(MoveDirection.LEFT);
                case "r", "right" -> result.add(MoveDirection.RIGHT);
            }
        }

        return result;
    }
}
