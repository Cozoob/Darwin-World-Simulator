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

        // set up the map
        int maxAnimalEnergy = 20;
        int grassEnergy = 5;
        int amountOfGrass = 2;
        int width = 10;
        int height = 10;
        int jungleWidth = 5;
        int jungleHeight = 5;
        WrappedMap wrappedMap = new WrappedMap(maxAnimalEnergy, grassEnergy, amountOfGrass, width, height, jungleWidth, jungleHeight);

        // set up the engine
        int days = 20;
        int animalStartingEnergy = 20;
        int amountStartingAnimals = 10;
        SimulationEngine engine = new SimulationEngine(wrappedMap, days, animalStartingEnergy, amountStartingAnimals);
        engine.setMoveDelay(2000);
        engine.start();
    }
}