package agh.ics.oop.Gui;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Engine.SimulationEngine;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.WorldElements.Vector2d;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Simulation implements IPositionChangeObserver{
    private final AbstractWorldMap map;
    private SimulationEngine engine;
    private Map<String, Integer> input;
    private Set<String> inputNames;
    private GridPane simulationPane;

    public Simulation(AbstractWorldMap map, SimulationEngine engine, Map<String, Integer> input, Set<String> inputNames) throws FileNotFoundException{
        this.map = map;
        this.engine = engine;
        this.input = input;
        this.inputNames = inputNames;
        init();
    }

    private void init() throws FileNotFoundException {
        for (Animal animal : map.getAliveAnimals()){
            animal.addObserver(this);
//            animal.setWidth(25); // optional - default options are width = 50, height = 70
//            animal.setHeight(35); // RATIO 5:7 -> WIDTH : HEIGHT
        }

        createSimulationPane();

        Thread thread = new Thread(engine);
        thread.start();
    }

    private void createSimulationPane() throws FileNotFoundException {
        int rowSize = 60;
        int columnSize = 60;

        Label label = new Label("y\\x");
        simulationPane.add(label, 0, 0, 1, 1);
        simulationPane.getRowConstraints().add(new RowConstraints(rowSize));
        simulationPane.getColumnConstraints().add(new ColumnConstraints(columnSize));
        GridPane.setHalignment(label, HPos.CENTER);
        simulationPane.setGridLinesVisible(true);

        // border
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        // index Y axis
        for (int i = 0; i <= upperRight.x - lowerLeft.x; i++){
            simulationPane.getColumnConstraints().add(new ColumnConstraints(columnSize));
            Label index = new Label(String.valueOf(i + lowerLeft.x));
            simulationPane.add(index, i + 1, 0);
            GridPane.setHalignment(index, HPos.CENTER);
        }

        // index X axis
        for (int i = 0; i <= upperRight.y - lowerLeft.y; i++){
            simulationPane.getRowConstraints().add(new RowConstraints(rowSize));
            Label index = new Label(String.valueOf(upperRight.y - i));
            simulationPane.add(index, 0, i + 1);
            GridPane.setHalignment(index, HPos.CENTER);
        }

        // add grass and animal icons
        ArrayList<Animal> animals = map.getAliveAnimals();
        LinkedHashMap<Vector2d, Grass> grasses = map.getGrassPositions();

        for(Grass grass : grasses.values()){
            GuiElementBox animalIcon = new GuiElementBox(grass);
            simulationPane.add(animalIcon.vBox, grass.getPosition().x + 1 - lowerLeft.x, upperRight.y - grass.getPosition().y + 1, 1, 1);
        }

        for(Animal animal : animals){
            GuiElementBox animalIcon = new GuiElementBox(animal);
            simulationPane.add(animalIcon.vBox, animal.getPosition().x + 1 - lowerLeft.x, upperRight.y - animal.getPosition().y + 1, 1, 1);
        }
    }

    public GridPane getSimulationPane() {
        return simulationPane;
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) throws FileNotFoundException {
        Platform.runLater( () ->{
            try {
                createSimulationPane();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } );
    }
}
