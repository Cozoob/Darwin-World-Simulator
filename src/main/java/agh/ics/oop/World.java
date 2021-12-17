package agh.ics.oop;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Engine.SimulationEngine;
import agh.ics.oop.EnumClasses.MoveDirection;
import agh.ics.oop.Gui.App;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.Maps.WallMap;
import agh.ics.oop.Maps.WrappedMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Vector2d;
import javafx.application.Application;

import java.util.*;

import static java.lang.System.out;

public class World {
    // TODO ZAMIEN TREESET NA SET - wyciagajac najwiekszy element sortuj po prostu....
    public static void main(String[] args) {

//        try {
//            Application.launch(App.class, args);
//        } catch (IllegalArgumentException ex){
//            out.println(ex.getMessage());
//            ex.printStackTrace();
//            System.exit(1);
//        }


        // set up the map
        boolean isMagic = true;
        int minimumEnergyToCopulate = 2;
        int maxAnimalEnergy = 20;
        int grassEnergy = 5;
        int amountOfGrass = 2;
        int width = 10;
        int height = 10;
        int jungleWidth = 5;
        int jungleHeight = 5;
        WrappedMap wrappedMap = new WrappedMap(isMagic ,minimumEnergyToCopulate,maxAnimalEnergy, grassEnergy, amountOfGrass, width, height, jungleWidth, jungleHeight);

        // test animal genotype
//        Animal animal = new Animal(wrappedMap,new Vector2d(1,1), 20);
//        animal.fillTheGenes();
//        Collections.sort(animal.genotype);
//        out.println(animal.genotype);
//        wrappedMap.place(animal);
//        Collections.sort(animal.genotype);
//        out.println(animal.genotype);
//        wrappedMap.putGenotype(animal);
//        out.println(wrappedMap.genotypes);
//        Collections.sort(animal.genotype);
//        wrappedMap.putGenotype(animal);
//        out.println(wrappedMap.genotypes);
//        wrappedMap.removeGenotype(animal);
//        wrappedMap.removeGenotype(animal);
//        wrappedMap.removeGenotype(animal);

        // set up the engine
        int days = 200;
        int animalStartingEnergy = 13;
        int amountStartingAnimals = 10;
        SimulationEngine engine = new SimulationEngine(wrappedMap, days, animalStartingEnergy, amountStartingAnimals);
        engine.setMoveDelay(2);
        engine.start();
    }
}