package agh.ics.oop.Engine;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.EnumClasses.MoveDirection;
import agh.ics.oop.Interfaces.IEngine;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.*;

public class SimulationEngine extends Thread implements IEngine, Runnable{
    private AbstractWorldMap map;
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
        //        System.out.println(map);

        // place the started animals
        for(int i = 0; i < amountStartingAnimals; i++){
            // started animals cannot be placed in the same field with other animals or grass
            if(map.freePrairiePositions.size() == 0 && map.freeJunglePositions.size() == 0){
                break; // no free fields to place started animals!
            }

            Animal animal = new Animal(map, getRandomFreeJunglePosition(), animalStartingEnergy);
            map.initialPlace(animal);
//            map.place(animal);
//            animal.fillTheGenes();
        }

        // put initial grass
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
                // kazdy wykonuje losowany z genow jego ruch po mapie
                animal.move(); // dodatkowo jest usuwane zwierze z mapy jesli umarlo
//                System.out.println(animal.getMapDirection());
            }
            for(HashSet<Animal> animals : map.animals.values()){
                for(Animal animal : animals){
//                    System.out.println(System.identityHashCode(animal) );
//                    System.out.println(animal);
//                    System.out.println(animal.energy);
//                    System.out.println(animal.isAlive);
//                    System.out.println(animal.children);
                }
            }
            // teraz trzeba sprawdzic czy sa animalse ktore stoja na jakiejs trawie jesli tak to je ja
            // najsilniejszy (inne rozstrzyganie remisow pozniej dodaj)
            map.animalsEatGrass();
            // trzeba tez sprawdzic czy sa zwierzaki na dwoch tych samych pozycjach jesli tak to kupuluje
            // dwojka najsilniejszych
            map.animalsCopulate();
            // pod koniec kazdego dnia trzeba tez wlozyc nowa trawe na mape
            map.putGrassOnJungle();
            map.putGrassOnPrairie();
            System.out.println(map);
            System.out.println(map.getModeOfGenotypes());
//            System.out.println(map.getModeOfGenotypes());
            int counter = 0;
            for(Integer number : map.genotypes.values()){
                counter += number;
            }
            if(counter != map.aliveAnimals.size()){
                System.out.println(map.aliveAnimals);
                System.out.println(counter);
                System.exit(1);
            }
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(map.aliveAnimals);
//            System.out.println(map.animals);
        }
    }

    public void setMoveDelay(int moveDelay) {this.moveDelay = moveDelay;}

    private Vector2d getRandomVector2d(){
        Random rand = new Random();
        int maxX = map.jungleUpperRight.x;
        int maxY = map.jungleUpperRight.y;
        int randomX = rand.nextInt(maxX + 1);
        int randomY = rand.nextInt(maxY + 1);
        return new Vector2d(randomX, randomY);
    }

    private Vector2d getRandomFreeJunglePosition(){
        int randomX;
        int randomY;
        Random rand = new Random();
        randomX = rand.nextInt(map.jungleUpperRight.x - map.jungleLowerLeft.x + 1) + map.jungleLowerLeft.x;
        randomY = rand.nextInt(map.jungleUpperRight.y - map.jungleLowerLeft.y + 1) + map.jungleLowerLeft.y;

        return new Vector2d(randomX, randomY);
    }
}