package agh.ics.oop.Gui;

import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;

public class GuiElementBox extends Node{
    public ImageView imageView;
    public Label label;
    public VBox vBox;

    public GuiElementBox(Object object) throws FileNotFoundException{
        if(object instanceof Animal){
            Animal animal = (Animal) object;
            this.imageView = animal.getImageView();
            this.label = animal.getLabel();

        } else if (object instanceof Grass){
            Grass grass = (Grass) object;
            this.imageView = grass.getImageView();
            this.label = grass.getLabel();

        } else { throw new FileNotFoundException("Image not found!");}

        this.vBox = new VBox();
        this.vBox.getChildren().add(0, imageView);
        this.vBox.getChildren().add(1, label);
        this.vBox.setAlignment(Pos.CENTER);

    }
}
