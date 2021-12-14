package agh.ics.oop.Gui;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Engine.OptionsParser;
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
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.util.*;


public class App extends Application implements IPositionChangeObserver {
    AbstractWorldMap map1;
    AbstractWorldMap map2;
    SimulationEngine engine1;
    SimulationEngine engine2;
    GridPane gridPane1 = new GridPane();
    GridPane gridPane2 = new GridPane();

    @Override
    public void start(Stage primaryStage) throws IllegalArgumentException, FileNotFoundException {
        for (Animal animal : map1.getAliveAnimals()){
            animal.addObserver(this);
            animal.setWidth(25); // optional - default options are width = 50, height = 70
            animal.setHeight(35); // RATIO 5:7 -> WIDTH : HEIGHT
        }

//        for (Animal animal : map2.getAliveAnimals()){
//            animal.addObserver(this);
//            animal.setWidth(25); // optional - default options are width = 50, height = 70
//            animal.setHeight(35); // RATIO 5:7 -> WIDTH : HEIGHT
//        }

        drawGrid(this.map1, this.gridPane1);
        drawGrid(this.map2, this.gridPane2);

        HBox hBox0 = new HBox();
        Separator separator = new Separator();

        hBox0.getChildren().addAll(gridPane1,gridPane2);
        gridPane1.setAlignment(Pos.TOP_LEFT);
        gridPane2.setAlignment(Pos.TOP_RIGHT);
        gridPane1.setPadding(new Insets(10));
        gridPane2.setPadding(new Insets(10));
        // set up the scene
//        gridPane.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
//        TextField textField = new TextField();

        Button button = new Button("START");
        button.setOnAction( (ActionEvent event) -> clickOnButton());
        button.scaleShapeProperty();
        hBox.getChildren().addAll(button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);

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
        primaryStage.show();


        Thread thread1 = new Thread(engine1);
        Thread thread2 = new Thread(engine2);
        thread1.start();
        thread2.start();
    }

    @Override
    public void init() throws IllegalArgumentException {
        // set up the first map
        boolean isMagic1 = true;
        int minimumEnergyToCopulate1 = 5;
        int maxAnimalEnergy1 = 20;
        int grassEnergy1 = 5;
        int amountOfGrass1 = 2;
        int width1 = 10;
        int height1 = 10;
        int jungleWidth1 = 5;
        int jungleHeight1 = 5;
        WrappedMap wrappedMap = new WrappedMap(isMagic1,minimumEnergyToCopulate1,maxAnimalEnergy1, grassEnergy1, amountOfGrass1, width1, height1, jungleWidth1, jungleHeight1);
        this.map1 = wrappedMap;

        // set up the second map
        boolean isMagic2 = true;
        int minimumEnergyToCopulate2= 5;
        int maxAnimalEnergy2 = 20;
        int grassEnergy2 = 5;
        int amountOfGrass2 = 2;
        int width2 = 10;
        int height2 = 10;
        int jungleWidth2 = 5;
        int jungleHeight2 = 5;
        WallMap wallMap = new WallMap(isMagic2,minimumEnergyToCopulate2,maxAnimalEnergy2, grassEnergy2, amountOfGrass2, width2, height2, jungleWidth2, jungleHeight2);
        this.map2 = wallMap;

        // set up the engine1
        int days1 = 20;
        int animalStartingEnergy1 = 20;
        int amountStartingAnimals1 = 10;
        SimulationEngine engine1 = new SimulationEngine(wrappedMap, days1, animalStartingEnergy1, amountStartingAnimals1);
        this.engine1 = engine1;
        engine1.setMoveDelay(2000);

        // set up the engine2
        int days2 = 20;
        int animalStartingEnergy2 = 20;
        int amountStartingAnimals2 = 10;
        SimulationEngine engine2 = new SimulationEngine(wallMap, days2, animalStartingEnergy2, amountStartingAnimals2);
        this.engine2 = engine2;
        engine2.setMoveDelay(2000);
    }

    public void drawGrid(AbstractWorldMap map, GridPane gridPane) throws FileNotFoundException {

        gridPane.setGridLinesVisible(false);
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        int rowSize = 60;
        int columnSize = 60;

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

        // add grass and animal icons
        //LinkedHashMap<Vector2d, Animal> animals = map.getAnimals();
        LinkedHashMap<Vector2d, Grass> grasses = map.getGrass();

        for(Grass grass : grasses.values()){
            GuiElementBox animalIcon = new GuiElementBox(grass);
            gridPane.add(animalIcon.vBox, grass.getPosition().x + 1 - lowerLeft.x, upperRight.y - grass.getPosition().y + 1, 1, 1);
        }

        for(Animal animal : map.aliveAnimals){
            GuiElementBox animalIcon = new GuiElementBox(animal);
            gridPane.add(animalIcon.vBox, animal.getPosition().x + 1 - lowerLeft.x, upperRight.y - animal.getPosition().y + 1, 1, 1);
        }
    }


    @Override
    public synchronized void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        Platform.runLater( () ->{
            try {
                drawGrid(this.map1, this.gridPane1);
                drawGrid(this.map2, this.gridPane2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } );
    }

    private void clickOnButton() throws IllegalArgumentException{
        System.out.println("New thread");
        Thread thread = new Thread(this.engine1);
        thread.start();
    }

}
