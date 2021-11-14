package agh.ics.oop;

import java.util.List;
import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    private IWorldMap map;
    private final List<Animal> animals = new ArrayList<Animal>();
    private final List<MoveDirection> moves;

    public SimulationEngine(List<MoveDirection> moves, IWorldMap map, Vector2d[] startedPositions) {
        this.moves = moves;
        this.map = map;
        for(Vector2d position : startedPositions){
            map.place(new Animal(map, position));
            this.animals.add((Animal) map.objectAt(position));
        }
    }

    @Override
    public void run() {
        System.out.println(map);
        int i = 0;
        int numberOfAnimals = this.animals.size();
        for (MoveDirection move : moves){
            this.animals.get(i % numberOfAnimals).move(move);
            System.out.println(move);
            System.out.println(map);
            i++;
        }
    }
}