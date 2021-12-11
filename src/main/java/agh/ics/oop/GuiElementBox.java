package agh.ics.oop;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox extends Node{
    public ImageView imageView;
    public Label label;
    public VBox vBox;
    public int width;
    public int height;

    public GuiElementBox(Object object) throws FileNotFoundException{
        Image image;
        if(object instanceof Animal){
            Animal animal = (Animal) object;
            this.imageView = animal.getImageView();
            this.label = animal.getLabel();

        } else if (object instanceof Grass){
            Grass grass = (Grass) object;
            this.imageView = grass.getImageView();
            this.label = grass.getLabel();

        } else { // do zmiany
            image = new Image("unknown");
        }

        this.label.setFont(new Font(15));
        this.vBox = new VBox();
        this.vBox.getChildren().add(0, imageView);
        this.vBox.getChildren().add(1, label);
        this.vBox.setAlignment(Pos.CENTER);

    }
}
