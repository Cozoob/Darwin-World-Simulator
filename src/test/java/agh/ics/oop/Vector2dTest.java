package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Vector2dTest {

    @Test
    public void testEquals(){
        Assertions.assertEquals(new Vector2d(1,2), new Vector2d(1,2));
        Assertions.assertNotEquals(new Vector2d(2,2), new Vector2d(1,1));
    }

    @Test
    public void testToString(){
        Assertions.assertEquals("(1,2)", new Vector2d(1,2).toString());
        Assertions.assertEquals("(5,2)", new Vector2d(5,2).toString());
        Assertions.assertNotEquals("(1,2)", new Vector2d(4,2).toString());
    }

    @Test
    public void testPrecedes(){
        Assertions.assertTrue(new Vector2d(1,2).precedes(new Vector2d(1,2)));
        Assertions.assertTrue(new Vector2d(1,2).precedes(new Vector2d(1,2)));
        Assertions.assertTrue(new Vector2d(1,2).precedes(new Vector2d(3,5)));
        Assertions.assertFalse(new Vector2d(1,2).precedes(new Vector2d(1,1)));
    }

    @Test
    public void testFollows(){
        Assertions.assertTrue(new Vector2d(1,2).follows(new Vector2d(1,2)));
        Assertions.assertTrue(new Vector2d(1,2).follows(new Vector2d(1,1)));
        Assertions.assertTrue(new Vector2d(1,2).follows(new Vector2d(-1,-2)));
        Assertions.assertTrue(new Vector2d(1,2).follows(new Vector2d(-11,2)));
        Assertions.assertFalse(new Vector2d(1,2).follows(new Vector2d(5,2)));
    }

    @Test
    public void testUpperRight(){
        Assertions.assertEquals(new Vector2d(5, 5), new Vector2d(5, 2).upperRight(new Vector2d(3, 5)));
        Assertions.assertEquals(new Vector2d(30, 20), new Vector2d(5, 20).upperRight(new Vector2d(30, 5)));
        Assertions.assertNotEquals(new Vector2d(5, 2), new Vector2d(5, 2).upperRight(new Vector2d(6, 5)));
    }

    @Test
    public void testLowerLeft(){
        Assertions.assertEquals(new Vector2d(3, 2), new Vector2d(5, 2).lowerLeft(new Vector2d(3, 5)));
        Assertions.assertEquals(new Vector2d(5, 5), new Vector2d(5, 20).lowerLeft(new Vector2d(30, 5)));
        Assertions.assertNotEquals(new Vector2d(6, 5), new Vector2d(5, 2).lowerLeft(new Vector2d(6, 5)));
    }

    @Test
    public void testAdd(){
        Assertions.assertEquals(new Vector2d(6, 6), new Vector2d(3,3).add(new Vector2d(3, 3)));
        Assertions.assertEquals(new Vector2d(60, -10), new Vector2d(3,3).add(new Vector2d(57, -13)));
        Assertions.assertNotEquals(new Vector2d(0, 0), new Vector2d(3,3).add(new Vector2d(3, 3)));
    }

    @Test
    public void testSubtract(){
        Assertions.assertEquals(new Vector2d(0, 0), new Vector2d(3,3).subtract(new Vector2d(3,3)));
        Assertions.assertEquals(new Vector2d(27, 24), new Vector2d(30,27).subtract(new Vector2d(3,3)));
        Assertions.assertNotEquals(new Vector2d(6, 6), new Vector2d(3,3).subtract(new Vector2d(3,3)));
    }

    @Test
    public void testOpposite(){
        Assertions.assertEquals(new Vector2d(2,3), new Vector2d(3,2).opposite());
        Assertions.assertEquals(new Vector2d(-5,-8), new Vector2d(-8,-5).opposite());
        Assertions.assertNotEquals(new Vector2d(3,2), new Vector2d(3,2).opposite());
    }
}
