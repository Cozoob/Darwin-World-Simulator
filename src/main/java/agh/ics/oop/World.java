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

    public static void main(String[] args) {

        // WITH GUI

//        try {
//            Application.launch(App.class);
//        } catch (IllegalArgumentException ex){
//            out.println(ex.getMessage());
//            ex.printStackTrace();
//            System.exit(1);
//        }

        // WITHOUT GUI

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


        // set up the engine
        int days = 200;
        int animalStartingEnergy = 13;
        int amountStartingAnimals = 10;
        SimulationEngine engine = new SimulationEngine(wrappedMap, days, animalStartingEnergy, amountStartingAnimals);
        engine.setMoveDelay(2);
        engine.start();
    }
}