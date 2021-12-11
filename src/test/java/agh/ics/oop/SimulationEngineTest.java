package agh.ics.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngineTest {
    private IWorldMap map1;
    private IWorldMap map2;
    private Vector2d[] positions;
    private IEngine engine1;
    private IEngine engine2;
    private List<MoveDirection> moves1;
    private List<MoveDirection> moves2;
    private List<Animal> animals1;
    private List<Animal> animals2;


    @Test
    public void testSimulationEngine(){
        //engine1.run();

        Assertions.assertEquals(animals1.get(0), map1.objectAt(new Vector2d(2, 4)));
        Assertions.assertEquals(animals1.get(1), map1.objectAt(new Vector2d(4, 3)));

        //engine2.run();

        Assertions.assertEquals(animals2.get(0), map2.objectAt(new Vector2d(2, 0)));
        Assertions.assertEquals(animals2.get(1), map2.objectAt(new Vector2d(3, 4)));
    }

    @BeforeEach
    void setUp(){
        positions = new Vector2d[] {new Vector2d(2, 2), new Vector2d(3,4)};
        moves1 = new OptionsParser().parse(new String[] {"f", "b", "r", "l", "r", "f", "b", "b", "l"});
        map1 = new RectangularMap(6, 5);
        engine1 = new SimulationEngine(moves1, map1, positions);
        animals1 = new ArrayList<Animal>();
        animals1.add((Animal) map1.objectAt(new Vector2d(2, 2)));
        animals1.add((Animal) map1.objectAt(new Vector2d(3,4)));

        moves2 = new OptionsParser().parse(new String[] {"f", "b", "r", "l","f","f","r","r","f","f","f","f","f","f","f","f"});
        map2 = new RectangularMap(10, 5);
        engine2 = new SimulationEngine(moves2, map2, positions);
        animals2 = new ArrayList<Animal>();
        animals2.add((Animal) map2.objectAt(new Vector2d(2,2)));
        animals2.add((Animal) map2.objectAt(new Vector2d(3,4)));
    }
}
