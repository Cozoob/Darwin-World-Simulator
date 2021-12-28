package agh.ics.oop.Gui;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Engine.SimulationEngine;
import agh.ics.oop.EnumClasses.MoveDirection;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Maps.WallMap;
import agh.ics.oop.Maps.WrappedMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.WorldElements.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.util.*;


public class App extends Application {

    private Stage window;
    private Scene simulation;
    private Scene menuScene;

    @Override
    public void start(Stage primaryStage) throws IllegalArgumentException, FileNotFoundException {
        window = primaryStage;
        primaryStage.setTitle("The simulation");
        window.show();
        showMapsOptions();

    }


    private void showMapsOptions(){
        //int menuWidth = 1300;
        //int menuHeight = 800;
        GridPane menu = new GridPane();
        menu.setAlignment(Pos.CENTER);

        menuScene = new Scene(menu, 1300, 800);
        for(int i = 0; i < 25;i++){
            menu.getRowConstraints().add(new RowConstraints(32));
        }
        for(int i = 0; i < 4; i++){
            menu.getColumnConstraints().add(new ColumnConstraints(325));
        }

        List<Map<String, Integer>> input = new ArrayList<>();
        Set<String> inputNames = new HashSet<>();
        for(int i = 0; i < 2; i++){
            int offset = i * 2;
            int rowIndex = 0;
            input.add(new HashMap<>());

            if(i == 0){
                addMapOptionHeader(rowIndex, offset, menu, "WRAPPED MAP DETAILS");
                rowIndex++;
            } else {
                addMapOptionHeader(rowIndex, offset, menu, "WALL MAP DETAILS");
                rowIndex++;
            }
            addMapOptionHeader(rowIndex,offset,menu, "MAP PROPERTIES");
            rowIndex++;
            addMapOptionHeader(rowIndex,offset,menu, "(values must be natural numbers (integers that are greater than 0))");
            rowIndex++;
            addMapOptionHeader(rowIndex,offset,menu, "(map width/height must be greater or equal to jungle width/height)");

            rowIndex++;
            addMapOptionLabel(rowIndex, offset, menu, "Map height");
            addMapsOptionTextField(rowIndex, offset, menu, 30, 1, "mapHeight", input.get(i));
            inputNames.add("mapHeight");
            rowIndex++;
            addMapOptionLabel(rowIndex, offset, menu, "Map width");
            addMapsOptionTextField(rowIndex, offset, menu, 30, 1, "mapWidth", input.get(i));
            inputNames.add("mapWidth");
            rowIndex++;
            addMapOptionLabel(rowIndex, offset, menu, "Jungle height");
            addMapsOptionTextField(rowIndex, offset, menu, 15, 1, "jungleHeight", input.get(i));
            inputNames.add("jungleHeight");
            rowIndex++;
            addMapOptionLabel(rowIndex, offset, menu, "Jungle width");
            addMapsOptionTextField(rowIndex, offset, menu, 15, 1, "jungleWidth", input.get(i));
            inputNames.add("jungleWidth");

            rowIndex += 1;
            addTwoRadioButtonsWithOptions(rowIndex, offset, menu, "Normal World", "Magic World", 0, 1, "worldType", input.get(i));
            inputNames.add("worldType");

            rowIndex += 1;
            addMapOptionHeader(rowIndex, offset, menu, "Energy properties");

            rowIndex += 1;
            addMapOptionHeader(rowIndex, offset, menu, "(values must be integers greater or equal to 0)");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Grass energy");
            addMapsOptionTextField(rowIndex, offset, menu, 20, 0, "grassEnergy", input.get(i));
            inputNames.add("grassEnergy");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Minimum energy to copulate");
            addMapsOptionTextField(rowIndex, offset, menu, 5, 0, "minEnergyToCopulate", input.get(i));
            inputNames.add("minEnergyToCopulate");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Animals' maximum energy");
            addMapsOptionTextField(rowIndex, offset, menu, 180, 0,"maxAnimalEnergy", input.get(i));
            inputNames.add("maxAnimalEnergy");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Animals' starting energy");
            addMapsOptionTextField(rowIndex, offset, menu, 100, 0, "AnimalsStartingEnergy", input.get(i));
            inputNames.add("AnimalsStartingEnergy");

            rowIndex += 1;
            addMapOptionHeader(rowIndex, offset, menu, "Spawning Properties");

            rowIndex += 1;
            addMapOptionHeader(rowIndex, offset, menu, "(values must be integers greater or equal to 0)");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Amount of starting animals");
            addMapsOptionTextField(rowIndex, offset, menu, 20, 0, "amountStartingAnimals", input.get(i));
            inputNames.add("amountStartingAnimals");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Amount of starting grass");
            addMapsOptionTextField(rowIndex, offset, menu, 10, 0, "amountGrass", input.get(i));
            inputNames.add("amountGrass");

            rowIndex += 1;
            addMapOptionHeader(rowIndex, offset, menu, "Others");

            rowIndex += 1;
            addMapOptionHeader(rowIndex, offset, menu, "(values must be integers greater than 0)");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Time between days [ms]");
            addMapsOptionTextField(rowIndex, offset, menu, 1000, 1, "dayDelay", input.get(i));
            inputNames.add("dayDelay");
        }

        Button submit = new Button("Submit");
        submit.setOnAction( (ActionEvent event) ->{
            if (!inputNames.contains("mapWidth") ||
                    !inputNames.contains("mapHeight") ||
                    !inputNames.contains("jungleWidth") ||
                    !inputNames.contains("jungleHeight"))
                throw new IllegalArgumentException("Expected inputs were not passed");

            for (Map<String, Integer> mapInputs : input){
                for (Integer value : mapInputs.values()){
                    if (value == null)
                        return;
                }
                if (mapInputs.get("mapWidth") - mapInputs.get("jungleWidth") < 2 ||
                        mapInputs.get("mapHeight") - mapInputs.get("jungleHeight") < 2)
                    return;
            }
            try {
                showSimulation(input, inputNames);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } );


        menu.add(submit, 0, 24, 4, 1);
        GridPane.setHalignment(submit, HPos.CENTER);

        window.setTitle("The simulation (settings)");
        window.setScene(menuScene);
    }

    private void addMapOptionHeader(int rowIndex,int offset,  GridPane menu, String text){
        Label header = new Label(text);
        menu.add(header, offset, rowIndex, 2, 1);
        GridPane.setHalignment(header, HPos.CENTER);
    }

    private void addMapOptionLabel(int rowIndex, int offset, GridPane menu, String text){
        Label label = new Label(text);
        menu.add(label, offset, rowIndex);
        GridPane.setHalignment(label, HPos.RIGHT);
        GridPane.setMargin(label, new Insets(5));
    }

    private void addMapsOptionTextField(int rowIndex, int offset, GridPane menu, int value, int minVal, String fieldName, Map<String, Integer> input){
        TextField field = new TextField(String.valueOf(value));
        menu.add(field, offset+1, rowIndex);
        GridPane.setHalignment(field, HPos.LEFT);
        GridPane.setMargin(field, new Insets(5));
        input.put(fieldName, value);

        field.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                int fieldValue = Integer.parseInt(newValue);
                if (fieldValue >= minVal)
                    input.put(fieldName, fieldValue);
                else
                    input.put(fieldName, null);
            } catch (NumberFormatException e){
                input.put(fieldName, null);
            }
        }));
    }

    private void addTwoRadioButtonsWithOptions(int rowIndex, int offset, GridPane menu, String name1, String name2, int value1, int value2, String fieldName, Map<String, Integer> input){
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton(name1);
        RadioButton radioButton2 = new RadioButton(name2);
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(radioButton1, radioButton2);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(30);
        GridPane.setHalignment(hBox, HPos.CENTER);

        radioButton1.setSelected(true);
        input.put(fieldName, value1);

        toggleGroup.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            String selectedName = (((RadioButton) toggleGroup.getSelectedToggle()).getText());

            if (selectedName.equals(name1))
                input.put(fieldName, value1);
            else
                input.put(fieldName, value2);

        }));

        menu.add(hBox, offset, rowIndex, 2, 1);
    }

    private void showSimulation(List<Map<String, Integer>> inputList, Set<String> inputNames) throws FileNotFoundException {
        GridPane gridPane = new GridPane();

        for(int i = 0; i < 2; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(50);
            gridPane.getColumnConstraints().addAll(columnConstraints);
        }

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getRowConstraints().addAll(rowConstraints);

        gridPane.setGridLinesVisible(true);

        // set up the WallMap
        Map<String, Integer> input1 = inputList.get(1);
        boolean isMagic1 = input1.get("worldType") == 1;
        WallMap wallMap = new WallMap(isMagic1, input1.get("minEnergyToCopulate"), input1.get("maxAnimalEnergy"), input1.get("grassEnergy"), input1.get("amountGrass"), input1.get("mapWidth"), input1.get("mapHeight"), input1.get("jungleWidth"),input1.get("jungleHeight"));
        SimulationEngine engine1 = new SimulationEngine(wallMap, 1, input1.get("AnimalsStartingEnergy"), input1.get("amountStartingAnimals"));
        engine1.setMoveDelay(input1.get("dayDelay"));

        // to od Rafała gdzie jest SimulationPane
        Simulation simulation1 = new Simulation(wallMap, engine1, input1, inputNames);

        gridPane.add(simulation1.getSimulationPane(), 0, 0);

        // set up the WrappedMap
        Map<String, Integer> input2 = inputList.get(0);
        boolean isMagic2 = input2.get("worldType") == 1;
        WrappedMap wrappedMap = new WrappedMap(isMagic2, input2.get("minEnergyToCopulate"), input2.get("maxAnimalEnergy"), input2.get("grassEnergy"), input2.get("amountGrass"), input2.get("mapWidth"), input2.get("mapHeight"), input2.get("jungleWidth"),input2.get("jungleHeight"));

        SimulationEngine engine2 = new SimulationEngine(wrappedMap, 1, input2.get("AnimalsStartingEnergy"), input2.get("amountStartingAnimals"));
        engine2.setMoveDelay(input2.get("dayDelay"));

        // to od Rafała gdzie jest SimulationPane
        Simulation simulation2 = new Simulation(wallMap, engine2, input2, inputNames);

        gridPane.add(simulation2.getSimulationPane(), 0, 0);

        simulation = new Scene(gridPane, 1800, 900);
        window.setTitle("The simulation");
        window.setScene(simulation);
        window.show();
    }


}