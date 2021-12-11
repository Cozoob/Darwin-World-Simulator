package agh.ics.oop;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass implements IMapElement{
    private final Vector2d position;

    public Grass(Vector2d position){this.position = position;}

    public Vector2d getPosition(){return this.position;}

    public String toString() {return "*";}

    @Override
    public ImageView getImageView() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/main/resources/grass-icon.jpg"));
        int width = 50;
        int height = 50;

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }

    @Override
    public Label getLabel() {return new Label(this.getPosition().toString());}
}
