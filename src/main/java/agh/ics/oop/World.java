package agh.ics.oop;

import agh.ics.oop.Gui.App;
import javafx.application.Application;

import static java.lang.System.out;

public class World {

    public static void main(String[] args) {
        try {
            Application.launch(App.class, args);
        } catch (IllegalArgumentException ex){
            out.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
    }
}