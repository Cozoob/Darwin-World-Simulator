package agh.ics.oop;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Animal implements IMapElement{
    private Vector2d position;
    private MapDirection mapDirection;
    private IWorldMap map;
    private final ArrayList<IPositionChangeObserver> positionObservers = new ArrayList<>();
    private int width = 50;
    private int height = 70; // RATIO 5:7 -> WIDTH : HEIGHT

    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2)); //- tak mozesz wywolac drugi konstruktor -> po to by nie powielac kodu
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.mapDirection = MapDirection.NORTH;
        if(map instanceof IPositionChangeObserver){
            addObserver((IPositionChangeObserver) map);
        }
    }

    // getter
    public Vector2d getPosition() {return position;}

    // getter
    public MapDirection getMapDirection() {return mapDirection;}

    // getter
    public IWorldMap getMap() {return map;}

    public String toString(){return mapDirection.toString();}

    public void move(MoveDirection direction) {
        Vector2d oldPosition = this.position;
       switch (direction){
           case FORWARD -> {
               boolean isMoveTo = map.canMoveTo(this.position.add(this.mapDirection.toUnitVector()));
               if(isMoveTo){
                    this.position = this.position.add(this.mapDirection.toUnitVector());
//                   positionChanged(oldPosition);
               }
           }
           case BACKWARD -> {
               boolean isMoveTo = map.canMoveTo(this.position.add(this.mapDirection.toUnitVector().opposite()));
               if(isMoveTo){
                   this.position = this.position.add(this.mapDirection.toUnitVector().opposite());
//                   positionChanged(oldPosition);
               }
           }
           case LEFT -> this.mapDirection = this.mapDirection.previous();
           case RIGHT -> this.mapDirection = this.mapDirection.next();
       }
        positionChanged(oldPosition);
    }

    public void addObserver(IPositionChangeObserver observer){this.positionObservers.add(observer);}

    public void removeObserver(IPositionChangeObserver observer){this.positionObservers.remove(observer);}

    private void positionChanged(Vector2d oldPosition){
        for (IPositionChangeObserver observer : positionObservers){
            try {
                observer.positionChanged(oldPosition, this.position);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public ImageView getImageView() throws FileNotFoundException {
        String url;

        switch(this.getMapDirection()){
            case NORTH -> url = "src/main/resources/animal/icon-north.png";
            case SOUTH -> url = "src/main/resources/animal/icon-south.png";
            case EAST -> url = "src/main/resources/animal/icon-east.png";
            case WEST -> url = "src/main/resources/animal/icon-west.png";
            default -> throw new IllegalStateException("Unexpected value: " + this.getMapDirection());
        }


        Image image = new Image(new FileInputStream(url));

        ImageView imageView = new ImageView(image);
        if (this.getMapDirection().equals(MapDirection.NORTH) || this.getMapDirection().equals(MapDirection.SOUTH)){
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
        } else {
            imageView.setFitHeight(width);
            imageView.setFitWidth(height);
        }

//        imageView.setFitHeight(height);
//        imageView.setFitWidth(width);
        return imageView;
    }

    @Override
    public Label getLabel() {
        return new Label(this.getPosition().toString());
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
