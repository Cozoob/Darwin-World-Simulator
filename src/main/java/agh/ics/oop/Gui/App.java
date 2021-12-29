package agh.ics.oop.Gui;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Engine.SimulationEngine;
import agh.ics.oop.Interfaces.IMapObserver;
import agh.ics.oop.Maps.WallMap;
import agh.ics.oop.Maps.WrappedMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.WorldElements.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.util.*;


public class App extends Application {
    private AbstractWorldMap map1;
    private AbstractWorldMap map2;
    private final GridPane gridPane1 = new GridPane();
    private final GridPane gridPane2 = new GridPane();
    private SimulationEngine engine1;
    private SimulationEngine engine2;
    private final Button startStopButton1 = new Button("PAUSE");
    private final Button startStopButton2 = new Button("PAUSE");
    private final Text day1 = new Text("DAY: 0");
    private final Text day2 = new Text("DAY: 0");
    private MapStatistics mapStatistics1;
    private MapStatistics mapStatistics2;
    private Scene menuScene;

    @Override
    public void start(Stage primaryStage) throws IllegalArgumentException{
        showMapsOptions(primaryStage);
    }

    private void drawGrid(AbstractWorldMap map, GridPane gridPane) throws FileNotFoundException {

        gridPane.setGridLinesVisible(false);
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();

        // one map is always 500x500
        int amountOfRows = map.getUpperRight().y - map.getLowerLeft().y;
        int amountOfColumns = map.getUpperRight().x - map.getLowerLeft().x;
        double rowSize = (double)500 / amountOfRows;
        double columnSize = (double)500 / amountOfColumns;

        Label label = new Label("y\\x");
        gridPane.add(label, 0, 0, 1, 1);
        gridPane.getRowConstraints().add(new RowConstraints(rowSize));
        gridPane.getColumnConstraints().add(new ColumnConstraints(columnSize));
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.setGridLinesVisible(true);

        // border
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        // index Y axis
        for (int i = 0; i <= upperRight.x - lowerLeft.x; i++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(columnSize));
            Label index = new Label(String.valueOf(i + lowerLeft.x));
            gridPane.add(index, i + 1, 0);
            GridPane.setHalignment(index, HPos.CENTER);
        }

        // index X axis
        for (int i = 0; i <= upperRight.y - lowerLeft.y; i++){
            gridPane.getRowConstraints().add(new RowConstraints(rowSize));
            Label index = new Label(String.valueOf(upperRight.y - i));
            gridPane.add(index, 0, i + 1);
            GridPane.setHalignment(index, HPos.CENTER);
        }

        for(int x = map.getLowerLeft().x; x <= map.getUpperRight().x; x++){
            for(int y = map.getLowerLeft().y; y <= map.getUpperRight().y; y++){
                GridPane pane = new GridPane();
                if(map.getJungleUpperRight().x >= x && x >= map.getJungleLowerLeft().x && map.getJungleUpperRight().y >= y && y >= map.getJungleLowerLeft().y){
                    pane.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    pane.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                gridPane.add(pane, y + 1, x + 1);
                GridPane.setHalignment(pane, HPos.CENTER);
            }
        }

        // add grass and animal icons
        // LinkedHashMap<Vector2d, Animal> animals = map.getAnimals();
        LinkedHashMap<Vector2d, Grass> grasses = map.getGrassPositions();

        ArrayList<Grass> grassesValues = new ArrayList<>(grasses.values());
        ArrayList<Animal> animalsValues = new ArrayList<>(map.getAliveAnimals());

        for(Grass grass : grassesValues){
            grass.setHeight(columnSize * 3 / 4);
            grass.setWidth(rowSize * 3 / 4);
            GuiElementBox animalIcon = new GuiElementBox(grass);
            gridPane.add(animalIcon.vBox, grass.getPosition().x + 1 - lowerLeft.x, upperRight.y - grass.getPosition().y + 1, 1, 1);
        }

        for(Animal animal : animalsValues){
            animal.setHeight(columnSize * 3 / 4);
            animal.setWidth(rowSize * 3 / 4);
            GuiElementBox animalIcon = new GuiElementBox(animal);
            gridPane.add(animalIcon.vBox, animal.getPosition().x + 1 - lowerLeft.x, upperRight.y - animal.getPosition().y + 1, 1, 1);
        }

    }

    private void showSimulation(Stage primaryStage, List<Map<String, Integer>> inputList) throws FileNotFoundException {
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
        map1 = wallMap;
        engine1 = new SimulationEngine(wallMap, 1, input1.get("AnimalsStartingEnergy"), input1.get("amountStartingAnimals"));
        engine1.setMoveDelay(input1.get("dayDelay"));

        // set up the WrappedMap
        Map<String, Integer> input2 = inputList.get(0);
        boolean isMagic2 = input2.get("worldType") == 1;
        WrappedMap wrappedMap = new WrappedMap(isMagic2, input2.get("minEnergyToCopulate"), input2.get("maxAnimalEnergy"), input2.get("grassEnergy"), input2.get("amountGrass"), input2.get("mapWidth"), input2.get("mapHeight"), input2.get("jungleWidth"),input2.get("jungleHeight"));
        map2 = wrappedMap;
        engine2 = new SimulationEngine(wrappedMap, 1, input2.get("AnimalsStartingEnergy"), input2.get("amountStartingAnimals"));
        engine2.setMoveDelay(input2.get("dayDelay"));



        engine1.addObserver(new IMapObserver() {
            @Override
            public void mapChanged() {
                updateMap();
            }
        });

        engine2.addObserver(new IMapObserver() {
            @Override
            public void mapChanged() {
                updateMap();
            }
        });

        drawGrid(this.map1, this.gridPane1);
        drawGrid(this.map2, this.gridPane2);

        updateDayText();
        setUpButton(startStopButton1, engine1);
        setUpButton(startStopButton2, engine2);

        Text mapName1 = new Text("WALL MAP");
        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(mapName1, day1, gridPane1, startStopButton1);

        Text mapName2 = new Text("WRAPPED MAP");
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(mapName2, day2, gridPane2, startStopButton2);

        HBox hBox0 = new HBox();

        // add statistics to maps
        mapStatistics1 = new MapStatistics(map1);
        mapStatistics2 = new MapStatistics(map2);
//        VBox statsBox1 = new VBox();
//        statsBox1.getChildren().addAll(mapStatistics1.lineChart, mapStatistics1.getModeOfGenotypes());
//        VBox statsBox2 = new VBox();
//        statsBox2.getChildren().addAll(mapStatistics2.lineChart, mapStatistics2.getModeOfGenotypes());


//        hBox0.getChildren().addAll(mapStatistics1.lineChart, vBox1, vBox2, mapStatistics2.lineChart);
//        gridPane1.setPadding(new Insets(10));
//        gridPane2.setPadding(new Insets(10));

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(mapStatistics1.lineChart, vBox1);
        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(vBox2, mapStatistics2.lineChart);

        gridPane1.setPadding(new Insets(10));
        gridPane2.setPadding(new Insets(10));

        VBox mapBox1 = new VBox();
        mapBox1.getChildren().addAll(hBox1, mapStatistics1.getModeOfGenotypes());
        VBox mapBox2 = new VBox();
        mapBox2.getChildren().addAll(hBox2, mapStatistics2.getModeOfGenotypes());


        hBox0.getChildren().addAll(mapBox1, mapBox2); // do zmiany

        HBox hBox = new HBox();
//        Text mapName1 = new Text("Wall Map");
//        Text mapName2 = new Text("Wrapped Map");
//        hBox.getChildren().addAll(mapName1, mapName2);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, hBox0);
        vBox.setSpacing(20);

        StackPane stackPane = new StackPane(vBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(10);
        scrollPane.setLayoutY(10);
        scrollPane.setContent(stackPane);

        GridPane grid = new GridPane();

        stackPane.minWidthProperty().bind(Bindings.createDoubleBinding( () ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
        grid.getChildren().add(stackPane);

        BorderPane border = new BorderPane();
        border.setCenter(scrollPane);

        primaryStage.setTitle("The simulation");
        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setFullScreen(true);
        primaryStage.show();



        Thread thread1 = new Thread(engine1);
        Thread thread2 = new Thread(engine2);
        thread1.start();
        thread2.start();
    }

    private void setUpButton(Button button, SimulationEngine engine){
        button.setOnAction( (ActionEvent event) ->{
            if(engine.isRunning){
                engine.pause();
                button.setText("RESUME");
                engine.isRunning = false;
            } else {
                engine.resumeRun();
                button.setText("PAUSE");
                engine.isRunning = true;
            }
        });
    }

    private void updateDayText(){
        day1.setText("DAY: " + engine1.getCurrentDay());
        day2.setText("DAY: " + engine2.getCurrentDay());
    }

//    @Override
//    public synchronized void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
//        Platform.runLater( () ->{
//            try {
//                drawGrid(this.map1, this.gridPane1);
//                drawGrid(this.map2, this.gridPane2);
//                updateDayText();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        } );
//    }

    public synchronized void updateMap(){
        Platform.runLater( () ->{
            try {
                drawGrid(this.map1, this.gridPane1);
                drawGrid(this.map2, this.gridPane2);
                updateDayText();
                mapStatistics1.updateData(engine1.getCurrentDay());
                mapStatistics2.updateData(engine2.getCurrentDay());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } );
    }

    private void showMapsOptions(Stage primaryStage){
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
            addMapsOptionTextField(rowIndex, offset, menu, 15, 1, "mapHeight", input.get(i));
            inputNames.add("mapHeight");
            rowIndex++;
            addMapOptionLabel(rowIndex, offset, menu, "Map width");
            addMapsOptionTextField(rowIndex, offset, menu, 15, 1, "mapWidth", input.get(i));
            inputNames.add("mapWidth");
            rowIndex++;
            addMapOptionLabel(rowIndex, offset, menu, "Jungle height");
            addMapsOptionTextField(rowIndex, offset, menu, 5, 1, "jungleHeight", input.get(i));
            inputNames.add("jungleHeight");
            rowIndex++;
            addMapOptionLabel(rowIndex, offset, menu, "Jungle width");
            addMapsOptionTextField(rowIndex, offset, menu, 5, 1, "jungleWidth", input.get(i));
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
            addMapsOptionTextField(rowIndex, offset, menu, 5, 0, "grassEnergy", input.get(i));
            inputNames.add("grassEnergy");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Minimum energy to copulate");
            addMapsOptionTextField(rowIndex, offset, menu, 5, 0, "minEnergyToCopulate", input.get(i));
            inputNames.add("minEnergyToCopulate");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Animals' maximum energy");
            addMapsOptionTextField(rowIndex, offset, menu, 50, 0,"maxAnimalEnergy", input.get(i));
            inputNames.add("maxAnimalEnergy");

            rowIndex += 1;
            addMapOptionLabel(rowIndex, offset, menu, "Animals' starting energy");
            addMapsOptionTextField(rowIndex, offset, menu, 30, 0, "AnimalsStartingEnergy", input.get(i));
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
            addMapsOptionTextField(rowIndex, offset, menu, 5, 0, "amountGrass", input.get(i));
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
                showSimulation(primaryStage, input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } );


        menu.add(submit, 0, 24, 4, 1);
        GridPane.setHalignment(submit, HPos.CENTER);

        primaryStage.setTitle("The simulation (settings)");
        primaryStage.setScene(menuScene);
        primaryStage.show();
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

}