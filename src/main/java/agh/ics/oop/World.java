package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class World {

    public static void main(String[] args) {
        String[] moves = new String[] {"f", "b", "r", "l", "r", "f", "b", "b", "l"};

        ArrayList<MoveDirection> directions = new OptionsParser().parse(moves);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2,3), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();


//        ArrayList<MoveDirection> directions = new OptionsParser().parse(args);
//        IWorldMap map = new RectangularMap(10, 5);
//        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
//        IEngine engine = new SimulationEngine(directions, map, positions);
//        engine.run();

//        ArrayList<Grass> grassPositions = new ArrayList<Grass>();
//        grassPositions.add(new Grass(new Vector2d(0,0)));
//        grassPositions.add(new Grass(new Vector2d(3,0)));
//        grassPositions.add(new Grass(new Vector2d(3,3)));
//        grassPositions.add(new Grass(new Vector2d(-1,0)));
//        grassPositions.add(new Grass(new Vector2d(3,-4)));
//
//        Vector2d lowerLeft = grassPositions.get(0).position;
//        Vector2d upperRight = grassPositions.get(0).position;
//        for (Grass grass : grassPositions){
//            lowerLeft = lowerLeft.lowerLeft(grass.position);
//            upperRight = upperRight.upperRight(grass.position);
//        }
//        out.println(lowerLeft);
//        out.println(upperRight);
    }
}