package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class MapDirectionTest {

    @Test
    public void testNext(){
        Assertions.assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        Assertions.assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        Assertions.assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
        Assertions.assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
    }

    @Test
    public void testPrevious(){
        Assertions.assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
        Assertions.assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        Assertions.assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
        Assertions.assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
    }
}
