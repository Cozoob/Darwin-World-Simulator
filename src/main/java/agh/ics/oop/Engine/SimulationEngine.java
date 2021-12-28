package agh.ics.oop.Engine;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;

import agh.ics.oop.Interfaces.IEngine;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Vector2d;

import java.io.FileNotFoundException;
import java.util.*;

public class SimulationEngine extends Thread implements IEngine, Runnable{
    private final AbstractWorldMap map;
    private int moveDelay = 1000;
    public int days;
    public int animalStartingEnergy;
    public int amountStartingAnimals;

    public SimulationEngine(AbstractWorldMap map, int days, int animalStartingEnergy, int amountStartingAnimals) {
        this.map = map;
        this.days = days;
        this.animalStartingEnergy = animalStartingEnergy;
        this.amountStartingAnimals = amountStartingAnimals;

        engineSetUp();
    }

    public void engineSetUp(){
        // place the started animals
        for(int i = 0; i < amountStartingAnimals; i++){
            // started animals cannot be placed in the same field with other animals or grass
            if(map.getFreePrairiePositions().size() == 0 && map.getFreeJunglePositions().size() == 0){
                if(i == 0){
                    // place at least one animal...
                    Animal animal = new Animal(map, new Vector2d(0,0), animalStartingEnergy);
                    map.initialPlace(animal);
                }
                break; // no free fields to place started animals! => Ignore the rest of the animals.
            }

            Animal animal = new Animal(map, getRandomFreeJunglePosition(), animalStartingEnergy);
            map.initialPlace(animal);
        }

        map.putInitialGrass();
    }

    @Override
    public synchronized void run() {

        for (int day = 0; day <= days; day++){
            System.out.print("DAY: ");
            System.out.println(day);
            if(map.getAliveAnimals().isEmpty()){
                break;
            }
            ArrayList<Animal> currentAliveAnimals = new ArrayList<>(map.getAliveAnimals());
            for(Animal animal : currentAliveAnimals){
                animal.move();
            }
            map.animalsEatGrass();
            map.animalsCopulate();
            map.putGrassOnJungle();
            map.putGrassOnPrairie();
            // TODO do usuniecia printy!
            System.out.println(map);
            System.out.println(map.getModeOfGenotypes());
            int counter = 0;
            for(Integer number : map.getGenotypes().values()){
                counter += number;
            }
            if(counter != map.getAliveAnimals().size()){
                System.out.println(map.getAliveAnimals());
                System.out.println(counter);
                System.exit(1);
            }
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!(map.getAliveAnimals().isEmpty())){
                days++;
            }
        }
    }

    public void setMoveDelay(int moveDelay) {this.moveDelay = moveDelay;}

    private Vector2d getRandomFreeJunglePosition(){
        int randomX;
        int randomY;
        Random rand = new Random();
        randomX = rand.nextInt(map.getJungleUpperRight().x - map.getJungleLowerLeft().x + 1) + map.getJungleLowerLeft().x;
        randomY = rand.nextInt(map.getJungleUpperRight().y - map.getJungleLowerLeft().y + 1) + map.getJungleLowerLeft().y;

        return new Vector2d(randomX, randomY);
    }
}