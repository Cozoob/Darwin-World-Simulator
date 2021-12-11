package agh.ics.oop;

import java.util.List;
import java.util.ArrayList;

public class SimulationEngine extends Thread implements IEngine, Runnable{
    private final IWorldMap map;
    private final List<Animal> animals = new ArrayList<Animal>();
    private List<MoveDirection> moves;
    private int moveDelay = 1000;

    public SimulationEngine(List<MoveDirection> moves, IWorldMap map, Vector2d[] startedPositions) {
        this.moves = moves;
        this.map = map;
        for (Vector2d position : startedPositions) {
            map.place(new Animal(map, position));
            this.animals.add((Animal) map.objectAt(position));
        }
    }

    public SimulationEngine(IWorldMap map, Vector2d[] startedPositions) {
        this.moves = new ArrayList<>();
        this.map = map;
        for (Vector2d position : startedPositions) {
            map.place(new Animal(map, position));
            this.animals.add((Animal) map.objectAt(position));
        }
    }

    @Override
    public synchronized void run() {
//        System.out.println(map);
        int i = 0;
        int numberOfAnimals = this.animals.size();

        for (MoveDirection move : moves){
            this.animals.get(i % numberOfAnimals).move(move);
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(move);
//            System.out.println(map);
            i++;
        }
    }

    public void setMoveDelay(int moveDelay) {this.moveDelay = moveDelay;}

    public void setMoves(List<MoveDirection> moves) {this.moves = moves;}
}