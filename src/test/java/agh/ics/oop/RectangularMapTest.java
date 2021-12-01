package agh.ics.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RectangularMapTest {
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
        Assertions.assertTrue(map.isOccupied(new Vector2d(1,1)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(2, 1)));
        Assertions.assertTrue(map.isOccupied(new Vector2d(2, 2)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0, 1)));
    }

    @Test
    void place(){
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(0,0))));
        IllegalArgumentException thrown1 = Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(15, 5))));
        Assertions.assertEquals("\"(15,5)\" field is invalid", thrown1.getMessage());

        IllegalArgumentException thrown2 = Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(14, 6))));
        Assertions.assertEquals("\"(14,6)\" field is invalid", thrown2.getMessage());

        IllegalArgumentException thrown3 = Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(-1, 2))));
        Assertions.assertEquals("\"(-1,2)\" field is invalid", thrown3.getMessage());

        IllegalArgumentException thrown4 = Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(-1, -1))));
        Assertions.assertEquals("\"(-1,-1)\" field is invalid", thrown4.getMessage());

        IllegalArgumentException thrown5 = Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(1, -2))));
        Assertions.assertEquals("\"(1,-2)\" field is invalid", thrown5.getMessage());
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
        Assertions.assertFalse(map.canMoveTo(new Vector2d(16,2)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(2,6)));
    }


    @BeforeEach
    void setUp(){
        map = new RectangularMap(15, 6);
        animal1 = new Animal(map, new Vector2d(1, 1));
        animal2 = new Animal(map, new Vector2d(2, 2));
        map.place(animal1);
        map.place(animal2);
    }
}
