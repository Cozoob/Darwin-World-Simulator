package agh.ics.oop.Interfaces;

import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Vector2d;

import java.io.FileNotFoundException;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) throws FileNotFoundException;
}
