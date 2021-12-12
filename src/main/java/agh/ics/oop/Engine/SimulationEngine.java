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
    public int days;

    public SimulationEngine(AbstractWorldMap map, int days) {
        this.map = map;
        this.days = days - 1;
        // choose random positions of the animals
        int i = 0;
        while(i < map.numberOfAnimals && map.freePrairiePositions.size() > 0 && map.freeJunglePositions.size() > 0){
            Random rand = new Random();
//        Random rand = new Random(103); // - do testow
            Vector2d randomVector = new Vector2d(rand.nextInt(map.getUpperRight().x), rand.nextInt(map.getUpperRight().y));
            if(!map.isOccupied(randomVector)){
                i++;
                Animal animal = new Animal(map, randomVector);
                map.place(animal);
                this.animals.add(animal);
            }
        }
    }


    @Override
    public synchronized void run() {
        System.out.println(map);

        for (int day = 0; day <= this.days; day++){
            for(Animal animal : this.animals){
                // kazdy wykonuje losowany z genow jego ruch po mapie
                animal.move();
                System.out.println(animal.getMapDirection());
            }
            System.out.println(map);
        }
    }

    public void setMoveDelay(int moveDelay) {this.moveDelay = moveDelay;}

    public void setMoves(List<MoveDirection> moves) {this.moves = moves;}
}