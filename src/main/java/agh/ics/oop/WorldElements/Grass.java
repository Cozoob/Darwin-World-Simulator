package agh.ics.oop.WorldElements;

import agh.ics.oop.Interfaces.IMapElement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass implements IMapElement {
    private final Vector2d position;
    public int energy;  // public?
    private final Image image;  // to lepiej przenieść to GUI
    private double width = 50;  // jw.
    private double height = 50;

    public Grass(Vector2d position, int energy) throws FileNotFoundException {
        this.position = position;
        this.energy = energy;
        this.image = new Image(new FileInputStream("src/main/resources/grass-icon.jpg"));   // czy każda trawa musi mieć swój obrazek?
    }

    public Vector2d getPosition(){return this.position;}

    public String toString() {return "*";}

    @Override
    public ImageView getImageView() throws FileNotFoundException {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public Label getLabel() {return new Label("");}
}
