package agh.ics.oop.Interfaces;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public interface IMapElement {
    public ImageView getImageView() throws FileNotFoundException;
    public Label getLabel();
}
