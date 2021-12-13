package agh.ics.oop.Gui;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Engine.OptionsParser;
import agh.ics.oop.Engine.SimulationEngine;
import agh.ics.oop.EnumClasses.MoveDirection;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Maps.WallMap;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.WorldElements.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.util.*;


public class App extends Application implements IPositionChangeObserver {
    MoveDirection[] directions;
    String[] args;
    Vector2d[] positions;
    AbstractWorldMap map;
    SimulationEngine engine;
    GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) throws IllegalArgumentException, FileNotFoundException {
//        for (Animal animal : map.getAnimals().values()){
//            animal.addObserver(this);
//            animal.setWidth(25); // optional - default options are width = 50, height = 70
//            animal.setHeight(35); // RATIO 5:7 -> WIDTH : HEIGHT
//        }

        drawGrid();
        // set up the scene
        gridPane.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        TextField textField = new TextField();

        Button button = new Button("START");
        button.setOnAction( (ActionEvent event) -> clickOnButton(textField));
        button.scaleShapeProperty();
        hBox.getChildren().addAll(textField, button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, gridPane);
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


        Thread thread = new Thread(engine);
        thread.start();
    }

    @Override
    public void init() throws IllegalArgumentException {
        args = getParameters().getRaw().toArray( new String[0] );
        positions = new Vector2d[] {new Vector2d(0,0), new Vector2d(1,1)};
        new OptionsParser();
        directions = OptionsParser.parse(args).toArray(new MoveDirection[0]);
        //map = new GrassField(5);
        // map = new RectangularMap(5, 5);
        //engine = new SimulationEngine(List.of(directions), map, positions);
        //engine.setMoveDelay(500); // optional[ms] - 1000ms is the default option
    }

    public void drawGrid() throws FileNotFoundException {

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

//        for(Animal animal : animals.values()){
//            GuiElementBox animalIcon = new GuiElementBox(animal);
//            gridPane.add(animalIcon.vBox, animal.getPosition().x + 1 - lowerLeft.x, upperRight.y - animal.getPosition().y + 1, 1, 1);
//        }
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        Platform.runLater( () ->{
            try {
                drawGrid();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } );
    }

    private void clickOnButton(TextField textField) throws IllegalArgumentException{
//        out.println("New thread");
        String[] args = textField.getText().split("[\\s]");
        List<MoveDirection> moves = OptionsParser.parse(args);
        this.engine.setMoves(moves);
        Thread thread = new Thread(this.engine);
        thread.start();
    }

}