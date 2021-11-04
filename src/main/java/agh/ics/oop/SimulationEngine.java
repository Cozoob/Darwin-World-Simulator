package agh.ics.oop;

import java.util.List;
import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    private IWorldMap map;
    private Vector2d[] initialPositions;
    private List<MoveDirection> moves;

    public SimulationEngine(List<MoveDirection> moves, IWorldMap map, Vector2d[] initialPositions) {
        this.moves = moves;
        this.map = map;
        this.initialPositions = initialPositions;
        for(Vector2d position : initialPositions){
            map.place(new Animal(map, position));
        }
    }

    @Override
    public void run() {
        System.out.println(map);

        List<Animal> animals = new ArrayList<Animal>();
        int i = 0;

        for(Vector2d position : initialPositions){
            if (map.objectAt(position) != null) {
                animals.add((Animal) map.objectAt(position));
            }
        }

        int numberOfAnimals = animals.size();
        for (MoveDirection move : moves){
            animals.get(i % numberOfAnimals).move(move);
            System.out.println(move);
            System.out.println(map);
            i++;
        }
    }
}