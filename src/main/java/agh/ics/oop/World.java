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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import static java.lang.System.out;

public class World {

    public static void main(String[] args) {

//        ArrayList<String> arr = new ArrayList<>();
//        arr.add("aha");
//        arr.add("ehe");
//        out.println(arr);
//        arr.remove("aha");
//        out.println(arr);
//        arr.remove("aha");
//        out.println(arr);



        int days = 2;
        int maxAnimalEnergy = 4;
        int grassEnergy = 5;
        int numberOfAnimals = 3;
        int amountOfGrass = 2;
        int width = 10;
        int height = 10;
        int jungleWidth = 5;
        int jungleHeight = 5;
        WrappedMap wrappedMap = new WrappedMap(maxAnimalEnergy, grassEnergy, numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);
        Animal animal1 = new Animal(wrappedMap, new Vector2d(0,0), maxAnimalEnergy);
        wrappedMap.place(animal1);


        out.println(wrappedMap);
        out.println("BEFORE ANY MOVES");

        animal1.setMoveDirection(MoveDirection.FORWARD);
        animal1.move();
        out.println(wrappedMap);
        out.println("FORWARD");
        out.println(animal1.getPosition());
        out.println(animal1.energy);

        Animal animal2 = new Animal(wrappedMap, new Vector2d(0,1), maxAnimalEnergy);
        wrappedMap.place(animal2);
        out.println(wrappedMap);
        out.println("NEW ANIMAL!");

        animal1.setMoveDirection(MoveDirection.BACKWARD);
        animal1.move();
        animal2.setMoveDirection(MoveDirection.FORWARD);
        animal2.move();
        out.println(wrappedMap);
        out.println("ANIMAL1 BACKWARD ANIMAL2 FORWARD");


//        WallMap wallMap = new WallMap(numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);
//        Animal animal1 = new Animal(wallMap, new Vector2d(0,0));
//        wallMap.place(animal1);
//
//        out.println(wallMap);
//        animal1.setMoveDirection(MoveDirection.FORWARD);
//        animal1.move();
//        out.println(wallMap);
//
//        animal1.setMoveDirection(MoveDirection.LEFT);
//        animal1.move();
//        out.println(wallMap);
//
//        animal1.setMoveDirection(MoveDirection.FORWARD);
//        animal1.move();
//        out.println(wallMap);

//        SimulationEngine engine = new SimulationEngine(wallMap, days);
//        engine.run();
//        out.println("WALLMAP");
//        out.println(wallMap);
//        out.println(wallMap.freeJunglePositions.size());
//        out.println(wallMap.freePrairiePositions.size());
//        out.println(wallMap.firstHalfOfGrass);
//        out.println(wallMap.secondHalfOfGrass);
//        out.println(wallMap.jungleLowerLeft);
//        out.println(wallMap.jungleUpperRight);

//        out.println("WRAPPEDMAP");
//        WrappedMap wrappedMap = new WrappedMap(numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);

    }
}