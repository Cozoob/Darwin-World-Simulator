package agh.ics.oop.Gui;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.Engine.SimulationEngine;
import agh.ics.oop.Maps.WallMap;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSVWriter {
    private final AbstractWorldMap map;
    private final StringBuilder title = new StringBuilder();
    private final StringBuilder days = new StringBuilder();
    public final StringBuilder document = new StringBuilder();

    public CSVWriter(AbstractWorldMap map){
        this.map = map;
        createTitle();
    }

    public void write(){
        String filename;
        if(map instanceof WallMap){
            filename = "Wall_Map_Statistics";
        } else {
            filename = "Wrapped_Map_Statistics";
        }
        try (PrintWriter writer = new PrintWriter(filename)) {
            document.append(title);
            document.append(days);
            writer.write(document.toString());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void createTitle(){
        title.append("DAY");
        title.append(", ");
        title.append("AnimalCounter");
        title.append(", ");
        title.append("GrassCounter");
        title.append(", ");
        title.append("AverageEnergy");
        title.append(", ");
        title.append("AverageLifeSpan");
        title.append(", ");
        title.append("AverageNumberOfChildren");
        title.append("\n");
    }

    public void generateData(AbstractWorldMap map, SimulationEngine engine){
        days.append(addData(map, engine));
    }

    private StringBuilder addData(AbstractWorldMap map, SimulationEngine engine){
        StringBuilder dayData = new StringBuilder();
        dayData.append(engine.getCurrentDay());
        dayData.append(", ");
        dayData.append(map.getNumberOfAliveAnimals());
        dayData.append(", ");
        dayData.append(map.getNumberOfGrass());
        dayData.append(", ");
        dayData.append(map.getAverageEnergyOfAliveAnimals());
        dayData.append(", ");
        dayData.append(map.getAverageLifeDaysOfDeadAnimals());
        dayData.append(", ");
        dayData.append(map.getAverageNumberOfChildren());
        dayData.append("\n");
        return dayData;
    }
}
