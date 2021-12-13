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


        int days = 2;
        int maxAnimalEnergy = 10;
        int grassEnergy = 5;
        int numberOfAnimals = 3;
        int amountOfGrass = 2;
        int width = 10;
        int height = 10;
        int jungleWidth = 5;
        int jungleHeight = 5;
        WrappedMap wrappedMap = new WrappedMap(maxAnimalEnergy, grassEnergy, numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);

        out.println(wrappedMap);

//        wrappedMap.putGrassOnJungle();
//        wrappedMap.putGrassOnPrairie();
//        out.println(wrappedMap);
//
//        wrappedMap.putGrassOnJungle();
//        wrappedMap.putGrassOnPrairie();
//        out.println(wrappedMap);
//
//        wrappedMap.putGrassOnJungle();
//        wrappedMap.putGrassOnPrairie();
//        out.println(wrappedMap);

        // ONLY ON JUNGLE
//        wrappedMap.putGrassOnJungle();
//        out.println(wrappedMap);
//        wrappedMap.putGrassOnJungle();
//        out.println(wrappedMap);
//        wrappedMap.putGrassOnJungle();
//        out.println(wrappedMap);
//        while(wrappedMap.freeJunglePositions.size() > 0){
//            wrappedMap.putGrassOnJungle();
//        }
//        wrappedMap.putGrassOnJungle();
//        wrappedMap.putGrassOnJungle();
//        wrappedMap.putGrassOnJungle();
//        out.println(wrappedMap);

        out.println("PRAIRIE");
        // TEST ONLY ON PRAIRIE
        wrappedMap.putGrassOnPrairie();
        out.println(wrappedMap);
        wrappedMap.putGrassOnPrairie();
        out.println(wrappedMap);
        wrappedMap.putGrassOnPrairie();
        out.println(wrappedMap);
        while(wrappedMap.freePrairiePositions.size() > 0){
            wrappedMap.putGrassOnPrairie();
        }
        out.println(wrappedMap);

        Animal animal = new Animal(wrappedMap,new Vector2d(3, 2), 3);
        wrappedMap.place(animal);
        out.println(wrappedMap.freePrairiePositions);
        wrappedMap.animalsEatGrass();
        out.println(wrappedMap);
        animal.setMoveDirection(MoveDirection.FORWARD);
        animal.move();
        out.println(wrappedMap);
        out.println(wrappedMap.freePrairiePositions);
        wrappedMap.putGrassOnPrairie();
        out.println(wrappedMap);


        out.println("JUNGLE");
        WrappedMap wrappedMap1 = new WrappedMap(maxAnimalEnergy, grassEnergy, numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);

        out.println(wrappedMap1);
        // ONLY ON JUNGLE
        wrappedMap1.putGrassOnJungle();
        out.println(wrappedMap1);
        wrappedMap1.putGrassOnJungle();
        out.println(wrappedMap1);
        wrappedMap1.putGrassOnJungle();
        out.println(wrappedMap1);
        while(wrappedMap1.freeJunglePositions.size() > 0){
            wrappedMap1.putGrassOnJungle();
        }
        out.println(wrappedMap1);

        Animal animal1 = new Animal(wrappedMap1,new Vector2d(3, 3), 3);
        wrappedMap1.place(animal1);
        out.println(wrappedMap1.freeJunglePositions);
        wrappedMap1.animalsEatGrass();
        out.println(wrappedMap1);
        animal1.setMoveDirection(MoveDirection.BACKWARD);
        animal1.move();
        out.println(wrappedMap1);
        out.println(wrappedMap1.freeJunglePositions);
        wrappedMap1.putGrassOnJungle();
        out.println(wrappedMap1);
    }
}