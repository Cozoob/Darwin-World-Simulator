package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class AnimalTest {

    @Test
    public void testAnimal(){
        Animal animal = new Animal(new RectangularMap(5,5));
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(MapDirection.EAST, animal.getMapDirection());
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(MapDirection.SOUTH, animal.getMapDirection());
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(MapDirection.SOUTH, animal.getMapDirection());
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(MapDirection.WEST, animal.getMapDirection());
        animal.move(MoveDirection.LEFT);
        Assertions.assertNotEquals(MapDirection.NORTH, animal.getMapDirection());
        animal.move(MoveDirection.BACKWARD);
        Assertions.assertEquals(MapDirection.SOUTH, animal.getMapDirection());

        Animal animal1 = new Animal(new RectangularMap(5,5));
        animal1.move(MoveDirection.RIGHT);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(2, 2));
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(3, 2));
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 2));
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertNotEquals(animal1.getPosition(), new Vector2d(5, 2));
        animal1.move(MoveDirection.LEFT);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 2));
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 3));
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 4));
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertNotEquals(animal1.getPosition(), new Vector2d(4, 5));
        animal1.move(MoveDirection.LEFT);
        animal1.move(MoveDirection.BACKWARD);
        Assertions.assertNotEquals(animal1.getPosition(), new Vector2d(5,4));

        Animal animal2 = new Animal(new RectangularMap(5,5));
        animal2.move(MoveDirection.LEFT); // 2,2
        animal2.move(MoveDirection.FORWARD); // 1,2
        animal2.move(MoveDirection.FORWARD); // 0, 2
        animal2.move(MoveDirection.LEFT); // 0, 2
        animal2.move(MoveDirection.FORWARD); // 0, 1
        animal2.move(MoveDirection.FORWARD); // 0, 0
        animal2.move(MoveDirection.FORWARD); // 0, 0
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(0,0));
        animal2.move(MoveDirection.RIGHT); // 0, 0
        animal2.move(MoveDirection.FORWARD); // 0, 0
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(0,0));
        animal2.move(MoveDirection.RIGHT); // 0, 0
        animal2.move(MoveDirection.BACKWARD); // 0, 0
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(0,0));
        animal2.move(MoveDirection.FORWARD); // 0, 1
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(0,1));

        String[] actions = {"forward","o" ,"f", "backward", "left","5", "%" ,"r", "l", "f"};
        OptionsParser parser = new OptionsParser();
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(actions));
        Assertions.assertEquals("\"o\" argument is not legal move specification.", thrown.getMessage());

    }


}
