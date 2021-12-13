package agh.ics.oop.Engine;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.EnumClasses.MoveDirection;
import agh.ics.oop.Interfaces.IEngine;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

            Animal animal = new Animal(map, getRandomFreePosition(), animalStartingEnergy);
//            map.initialPlace(animal);
            map.place(animal);
            animal.fillTheGenes();
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
                continue;
            }
            ArrayList<Animal> currentAliveAnimals = new ArrayList<>(map.getAliveAnimals());
            for(Animal animal : currentAliveAnimals){
                // kazdy wykonuje losowany z genow jego ruch po mapie
                animal.move(); // dodatkowo jest usuwane zwierze z mapy jesli umarlo
//                System.out.println(animal.getMapDirection());
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
//            System.out.println(map);
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMoveDelay(int moveDelay) {this.moveDelay = moveDelay;}

    private Vector2d getRandomVector2d(){
        Random rand = new Random();
        int maxX = map.getUpperRight().x;
        int maxY = map.getUpperRight().y;
        int randomX = rand.nextInt(maxX + 1);
        int randomY = rand.nextInt(maxY + 1);
        return new Vector2d(randomX, randomY);
    }

    private Vector2d getRandomFreePosition(){
        Collections.shuffle(map.freePrairiePositions);
        Collections.shuffle(map.freeJunglePositions);
        int randomX;
        int randomY;
        Random rand = new Random();
        boolean randomBoolean = rand.nextBoolean();
        if(map.freePrairiePositions.size() == 0){
            randomBoolean = false;
        } else if (map.freeJunglePositions.size() == 0){
            randomBoolean = true;
        }

        Vector2d vector2d;
        if(randomBoolean){
            // z prairie
            vector2d = map.freePrairiePositions.get(0);
        } else {
            // z jungle
            vector2d = map.freeJunglePositions.get(0);
        }
        randomX = vector2d.x;
        randomY = vector2d.y;

        return new Vector2d(randomX, randomY);
    }
}