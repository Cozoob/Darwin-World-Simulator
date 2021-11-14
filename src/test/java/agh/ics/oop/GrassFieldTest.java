package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrassFieldTest {
    Animal animal1;
    Animal animal2;
    IWorldMap map;

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
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(15, 5))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(14, 6))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(-1, 2))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(-1, -1))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(1, -2))));
    }

    @Test
    void canMoveTo(){
        Assertions.assertTrue(map.canMoveTo(new Vector2d(2,3)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(14,1)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(2,2)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(1,1)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(-2,-2)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(-2,2)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(2,-2)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(2,2)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(16,2)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(2,6)));
    }


    @BeforeEach
    void setUp(){
        map = new GrassField(20);
        animal1 = new Animal(map, new Vector2d(1, 1));
        animal2 = new Animal(map, new Vector2d(2, 2));
        map.place(animal1);
        map.place(animal2);
    }
}
