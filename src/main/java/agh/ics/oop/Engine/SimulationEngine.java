package agh.ics.oop.Engine;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.EnumClasses.MoveDirection;
import agh.ics.oop.Interfaces.IEngine;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class SimulationEngine extends Thread implements IEngine, Runnable{
    private AbstractWorldMap map;
    private List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves;
    private int moveDelay = 1000;

    public SimulationEngine(List<MoveDirection> moves, AbstractWorldMap map) {
        this.moves = moves; // do usuniecia i zmiany - tutaj powinienem losowac moves z genotypu danego zwierzaka
        this.map = map;
        // choose random positions of the animals
        int i = 0;
        while(i < map.numberOfAnimals){
            Random rand = new Random();
//        Random rand = new Random(103); // - do testow
            Vector2d randomVector = new Vector2d(rand.nextInt(map.getUpperRight().x), rand.nextInt(map.getUpperRight().y));
            if(!map.isOccupied(randomVector)){
                // place the animal
                i++;
                Animal animal = new Animal(map, randomVector);
                map.place(animal);
                this.animals.add(animal);
            }
        }
    }


    @Override
    public synchronized void run() {
//        System.out.println(map);
        int i = 0;
        int numberOfAnimals = this.animals.size(); // lub: map.numberOfAnimals

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