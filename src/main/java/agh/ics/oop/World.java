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

        Animal animal1 = new Animal(wrappedMap, new Vector2d(2, 2), 10);
//        Animal animal2 = new Animal(wrappedMap, new Vector2d(2, 3), 5);
        Animal animal3 = new Animal(wrappedMap, new Vector2d(2, 2), 1);

        wrappedMap.place(animal1);
//        wrappedMap.place(animal2);
        out.println(wrappedMap);
//        animal2.setMoveDirection(MoveDirection.BACKWARD);
//        animal2.move();
        wrappedMap.place(animal3);
        out.println(wrappedMap);
//        Animal an = wrappedMap.animals.get(new Vector2d(2, 2)).first();
//        out.println(an.equals(animal2));
//        out.println(wrappedMap.animals.get(new Vector2d(2,2)).higher(an).equals(animal1));
        animal1.fillTheGenes();
        animal3.fillTheGenes2();

        out.println(animal3.energy);
        out.println(animal3.genotype);
        out.println(animal1.energy);
        out.println(animal1.genotype);
        wrappedMap.animalsCopulate();
//        out.println(wrappedMap);
//        out.println(animal3.energy);
//        out.println(animal1.energy);
        out.println(animal3.children.get(0).genotype);
    }
}