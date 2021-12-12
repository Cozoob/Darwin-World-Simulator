package agh.ics.oop;

import agh.ics.oop.Maps.WallMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WallMapTest {
    WallMap map;
    Animal animal1;
    Animal animal2;

    @Test
    void objectAt(){
        Assertions.assertEquals(animal1, map.objectAt(new Vector2d(1, 1)));
        Assertions.assertEquals(animal2, map.objectAt(new Vector2d(2, 2)));
    }

    @Test
    void isOccupied(){
        Assertions.assertTrue(map.isOccupied(new Vector2d(1,1)) && map.objectAt(new Vector2d(1, 1)) instanceof Animal);
        Assertions.assertFalse(map.isOccupied(new Vector2d(2, 1)) && map.objectAt(new Vector2d(2, 1)) instanceof Animal);
        Assertions.assertTrue(map.isOccupied(new Vector2d(2, 2)) && map.objectAt(new Vector2d(2, 2)) instanceof Animal);
        Assertions.assertFalse(map.isOccupied(new Vector2d(0, 1)) &&  map.objectAt(new Vector2d(0, 1)) instanceof Animal);
    }

    @Test
    void place(){
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(0,0))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(14, 14))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(14, 6))));
//        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(-1, 2))));
//        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(-1, -1))));
//        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(1, -2))));
    }


    @BeforeEach
    void setUp(){
        int days = 10;
        int numberOfAnimals = 3;
        int amountOfGrass = 2;
        int width = 15;
        int height = 15;
        int jungleWidth = 5;
        int jungleHeight = 5;
        map = new WallMap(numberOfAnimals, amountOfGrass, width, height, jungleWidth, jungleHeight);
        animal1 = new Animal(map, new Vector2d(1, 1));
        animal2 = new Animal(map, new Vector2d(2, 2));
        map.place(animal1);
        map.place(animal2);
    }
}
