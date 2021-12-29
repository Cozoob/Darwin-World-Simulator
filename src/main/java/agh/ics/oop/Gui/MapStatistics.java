package agh.ics.oop.Gui;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MapStatistics {
    private final AbstractWorldMap map;
    private final XYChart.Series<Number, Number> numberOfAliveAnimals = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> numberOfGrass= new XYChart.Series<>();
    private final XYChart.Series<Number, Number>  averageEnergy= new XYChart.Series<>();
    private final XYChart.Series<Number, Number> averageLifeSpan= new XYChart.Series<>();
    private final XYChart.Series<Number, Number> averageAmountOfChildren= new XYChart.Series<>();
    private ArrayList<Integer> modeOfGenotypes;
    public LineChart<Number, Number> lineChart;

    public MapStatistics(AbstractWorldMap map){
        this.map = map;

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);

        numberOfAliveAnimals.setName("Number of animals");
        numberOfGrass.setName("Number of grass");
        averageEnergy.setName("Average energy");
        averageLifeSpan.setName("Average lifespan");
        averageAmountOfChildren.setName("Average amount of children");
        updateData(1);
        lineChart.getData().addAll(numberOfAliveAnimals, numberOfGrass, averageEnergy, averageLifeSpan, averageAmountOfChildren);
        lineChart.setCreateSymbols(false);
    }

    public void updateData(int day){
        numberOfAliveAnimals.getData().add(new XYChart.Data<>(day, map.getNumberOfAliveAnimals()));
        numberOfGrass.getData().add(new XYChart.Data<>(day, map.getNumberOfGrass()));
        averageEnergy.getData().add(new XYChart.Data<>(day, map.getAverageEnergyOfAliveAnimals()));
        averageLifeSpan.getData().add(new XYChart.Data<>(day, map.getAverageLifeDaysOfDeadAnimals()));
        averageAmountOfChildren.getData().add(new XYChart.Data<>(day, map.getAverageNumberOfChildren()));
        if(map.isAnyAliveAnimal()) {
            modeOfGenotypes = map.getModeOfGenotypes();
        }
    }

    public Text getModeOfGenotypes() {
        return new Text(modeOfGenotypes.toString());
    }
}
