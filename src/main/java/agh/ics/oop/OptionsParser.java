package agh.ics.oop;

import java.util.ArrayList;

public class OptionsParser {

//    final private String[] moveOptions = new String[] {"f", "forward", "b", "backward", "l", "left", };

    public ArrayList<MoveDirection> parse(String[] moves){
        ArrayList<MoveDirection> result = new ArrayList<>();

        for(String move : moves){
            switch (move) {
                case "f", "forward" -> result.add(MoveDirection.FORWARD);
                case "b", "backward" -> result.add(MoveDirection.BACKWARD);
                case "l", "left" -> result.add(MoveDirection.LEFT);
                case "r", "right" -> result.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException("\""+ move + "\" argument is not legal move specification.");
            }

        }

        return result;
    }
}
