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

import static java.lang.System.out;

public class World {

    public static void main(String[] args) {
        int days = 2;
        int numberOfAnimals = 3;
        int amountOfGrass = 2;
        int width = 10;
        int height = 10;
        int jungleWidth = 5;
        int jungleHeight = 5;
        WrappedMap wrappedMap = new WrappedMap(numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);
        Animal animal1 = new Animal(wrappedMap, new Vector2d(0,0));
        wrappedMap.place(animal1);

        out.println("FORWARD");
        out.println(wrappedMap);
        animal1.setMoveDirection(MoveDirection.FORWARD);
        animal1.move();
        out.println(wrappedMap);

        animal1.setMoveDirection(MoveDirection.FORWARDLEFT);
        animal1.move();
        out.println(wrappedMap);

        out.println("FORWARD");
        animal1.setMoveDirection(MoveDirection.FORWARD);
        animal1.move();
        out.println(wrappedMap);
        out.println(animal1.getPosition());

        out.println("NEW ANIMAL!");
        Animal animal2 = new Animal(wrappedMap, new Vector2d(0,9));
        wrappedMap.place(animal2);
        out.println(wrappedMap);

        out.println("FORWARD");
        animal2.setMoveDirection(MoveDirection.FORWARD);
        animal2.move();
        out.println(wrappedMap);

        animal2.setMoveDirection(MoveDirection.FORWARDLEFT);
        animal2.move();
        out.println(wrappedMap);

        out.println("BACKWARD");
        animal2.setMoveDirection(MoveDirection.BACKWARD);
        animal2.move();
        out.println(wrappedMap);

        out.println("FORWARD");
        animal2.setMoveDirection(MoveDirection.FORWARD);
        animal2.move();
        out.println(wrappedMap);

        animal2.setMoveDirection(MoveDirection.BACKWARDLEFT);
        animal2.move();
        out.println(wrappedMap);

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