package agh.ics.oop.AbstractClasses;

import agh.ics.oop.Engine.MapBoundary;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.Engine.MapVisualizer;
import agh.ics.oop.WorldElements.Vector2d;

import java.awt.*;
import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    public LinkedHashMap<Vector2d, Grass> grassPositions; // protected
    public LinkedHashMap<Vector2d, HashSet<Animal>> animals; // protected
    public ArrayList<Animal> deadAnimals = new ArrayList<>(); // protected
    public ArrayList<Animal> aliveAnimals; // protected
    protected Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRight;
    public Vector2d jungleLowerLeft; // protected
    public Vector2d jungleUpperRight; // protected
    protected MapBoundary mapBoundary; // final ?
    public ArrayList<Vector2d> freeJunglePositions = new ArrayList<>(); // protected
    public ArrayList<Vector2d> freePrairiePositions = new ArrayList<>(); // protected
    public int grassEnergy; // protected
    public int amountOfGrass;
    public int maxAnimalEnergy; // protected
    public Random rand = new Random(); // protected
    public int minimumEnergyToCopulate;
    public boolean isMagic;
    protected int counterOfMagic = 0;
    public LinkedHashMap<String, Integer> genotypes = new LinkedHashMap<>();// protected

    // sprawdz jakie wartosci z inputu moga cos zepsuc... wyrzuc wtedy wyjatki
    public AbstractWorldMap(boolean isMagic,int minimumEnergyToCopulate,int maxAnimalEnergy ,int grassEnergy, int amountOfGrass, int width, int height, int jungleWidth, int jungleHeight){
        if (jungleHeight > height || jungleWidth > width){
            System.out.println("ZLE");
            // wyrzuc wyjatek!
        }
        this.isMagic = isMagic;
        this.minimumEnergyToCopulate = minimumEnergyToCopulate;
        this.maxAnimalEnergy = maxAnimalEnergy;
        this.grassEnergy = grassEnergy;
        this.amountOfGrass = amountOfGrass;
        this.animals = new LinkedHashMap<>();
        this.aliveAnimals = new ArrayList<>();
        this.grassPositions = new LinkedHashMap<>();
        this.mapBoundary = new MapBoundary(this);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.jungleLowerLeft = new Vector2d( (int) Math.ceil((double) width / 2 -  (double) jungleWidth / 2), (int) Math.ceil((double)height / 2 - (double) jungleHeight / 2));
        this.jungleUpperRight = new Vector2d( (int) Math.ceil((double) width / 2 +  (double) jungleWidth / 2) - 1, (int) Math.ceil((double)height / 2 + (double) jungleHeight / 2) - 1);

        findFreeJunglePositions();
        findFreePrairiePositions();
    }

    public void putInitialGrass(){
        int firstHalfOfGrass = (int) Math.ceil((double)this.amountOfGrass / 2);
        int secondHalfOfGrass = amountOfGrass - firstHalfOfGrass;
        this.amountOfGrass = 0;
        // first half in jungle
        for (int i = 0; i < firstHalfOfGrass; i++){
            if(this.freeJunglePositions.size() > 0) {
                putGrassOnJungle();
            }
        }
        // second half in prairie (outside the jungle)
        for (int i = 0; i < secondHalfOfGrass; i++){
            if(this.freePrairiePositions.size() > 0) {
                putGrassOnPrairie();
            }
        }
    }


    // mozna by bylo zrobic jedna metoda putGrassOn(i na czym....)
    public void putGrassOnJungle(){
        if(this.freeJunglePositions.size() > 0) {
        Collections.shuffle(this.freeJunglePositions);
            int index = this.freeJunglePositions.size() - 1;
            Vector2d randomVector = this.freeJunglePositions.get(index);
            this.freeJunglePositions.remove(index);
            this.grassPositions.put(randomVector, new Grass(randomVector, this.grassEnergy));
            this.mapBoundary.addPosition(randomVector);
            this.amountOfGrass++;
        }
    }

    public void putGrassOnPrairie(){
        if(this.freePrairiePositions.size() > 0) {
        Collections.shuffle(this.freePrairiePositions);
            int index = this.freePrairiePositions.size() - 1;
            Vector2d randomVector = this.freePrairiePositions.get(index);
            this.freePrairiePositions.remove(index);
            this.grassPositions.put(randomVector, new Grass(randomVector, this.grassEnergy));
            this.mapBoundary.addPosition(randomVector);
            this.amountOfGrass++;
        }
    }

    public void initialPlace(Animal animal){
        animal.fillTheGenes();
        putGenotype(animal);

        Vector2d position = animal.getPosition();

        if (!this.animals.containsKey(position)){
            this.animals.put(position, new HashSet<>());
        }

        this.animals.get(position).add(animal);
        this.aliveAnimals.add(animal);
        this.mapBoundary.addPosition(position);
        this.freeJunglePositions.remove(position);
    }

    @Override
    public boolean place(Animal animal) {
        putGenotype(animal);
        Vector2d position = animal.getPosition();

        if (!this.animals.containsKey(position)){
            this.animals.put(position, new HashSet<>());
        }

        this.animals.get(position).add(animal);
        this.aliveAnimals.add(animal);
        this.mapBoundary.addPosition(position);
        if(position.precedes(this.jungleUpperRight) && position.follows(this.jungleLowerLeft)){
            this.freeJunglePositions.remove(position);
        } else {
            this.freePrairiePositions.remove(position);
        }
//        System.out.println();
//        System.out.println("FIRST CHIILD!");
//        System.out.println(this.genotypes);
//        System.out.println(this.genotypes);
//        System.out.println();
//        System.exit(1);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {return this.objectAt(position) != null;}

    @Override
    public abstract boolean canMoveTo(Vector2d position);

    @Override
    public Object objectAt(Vector2d position) {
        if (this.animals.get(position) != null) {
            return this.animals.get(position);
        }
        if (this.grassPositions.get(position) != null){
            return this.grassPositions.get(position);

        }
        return null;
    }

    public String toString(){return new MapVisualizer(this).draw(getLowerLeft(), getUpperRight());}

    public void animalsCopulate(){
        for(HashSet<Animal> hashSet : this.animals.values()){
            if(hashSet.size() > 1){
                // TODO ZMIANA! - moze juz jest ok?
                ArrayList<Animal> twoStrongestAnimals = getTwoStrongestAnimals(hashSet);
                Animal animal1 = twoStrongestAnimals.get(0);
                Animal animal2 = twoStrongestAnimals.get(1);
                if(animal2 == null){
                    continue;
                }
                if(animal1.energy < this.minimumEnergyToCopulate || animal2.energy < this.minimumEnergyToCopulate){
                    break;
                }
                // to zamien pozniej na throwsa po prostu?? TODO
                // problem z ta metoda NullPointerException...! -- czy rozwiazane
                int ratio1 = (int) Math.floor((double) animal1.energy * 100/ (animal1.energy + animal2.energy)); // ratio1 animal1.energy : (animal1.energy + animal2.energy)
                int ratio2 = (int) Math.floor((double) animal2.energy * 100/ (animal1.energy + animal2.energy)); // ratio1 animal1.energy : (animal1.energy + animal2.energy)

                Vector2d initialPosition = new Vector2d(animal1.getPosition().x, animal1.getPosition().y);
                int childEnergy = (int) Math.ceil((double) animal1.energy / 4) + (int) Math.floor((double)animal2.energy / 4);
                animal1.energy -= (int) Math.floor((double) animal1.energy / 4);
                animal2.energy -= (int) Math.floor((double) animal2.energy / 4);

                Animal child = new Animal(this, initialPosition, childEnergy);

                Collections.shuffle(animal1.genotype);
                Collections.shuffle(animal2.genotype);
                ArrayList<Integer> genotypeAnimal1 = animal1.genotype;
                ArrayList<Integer> genotypeAnimal2 = animal2.genotype;
                ArrayList<Integer> childGenotype = child.genotype;
                Random random = new Random();
                boolean randomBoolean = random.nextBoolean();
                // need to fill the rest 32-8=24 genes of the child
                if(randomBoolean){
                    // choose left genotype of the animal1 and right genotype of the animal2
                    // considering ratios
                    int numberOfGenesFromAnimal1 = (int) Math.ceil((double) ratio1 / 100 * 24);
                    int numberOfGenesFromAnimal2 = 24 - numberOfGenesFromAnimal1;
                    for(int i = 0; i < numberOfGenesFromAnimal1; i++){
                        childGenotype.add(genotypeAnimal1.get(i));
                    }
                    int idx = genotypeAnimal2.size() - 1;
                    for(int i = 0; i < numberOfGenesFromAnimal2; i++){
                        childGenotype.add(genotypeAnimal2.get(idx));
                        idx--;
                    }


                } else {
                    // choose left genotype of the animal2 and right genotype of the animal1
                    // considering ratios
                    int numberOfGenesFromAnimal2 = (int) Math.ceil((double) ratio2 / 100 * 24);
                    int numberOfGenesFromAnimal1 = 24 - numberOfGenesFromAnimal2;
                    int idx = genotypeAnimal1.size() - 1;
                    for(int i = 0; i < numberOfGenesFromAnimal1; i++){
                        childGenotype.add(genotypeAnimal1.get(idx));
                        idx--;
                    }
                    for(int i = 0; i < numberOfGenesFromAnimal2; i++){
                        childGenotype.add(genotypeAnimal2.get(i));
                    }
                }

                animal1.addNewChildren(child);
                animal2.addNewChildren(child);
                place(child);
                if(animal1.energy <= 0){
                    removeDeadAnimal(animal1);
                }
                if(animal2.energy <= 0){
                    removeDeadAnimal(animal1);
                }
            }
        }
    }

    public void removeMovedAnimal(Animal animal,Vector2d oldPosition){
        this.animals.get(oldPosition).remove(animal);
        if(this.animals.get(oldPosition).size() == 0){
            this.animals.remove(oldPosition);
        }
    }

    public void addMovedAnimal(Animal animal,Vector2d newPosition){
        this.animals.computeIfAbsent(newPosition, k -> new HashSet<>());
        this.animals.get(newPosition).add(animal);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        this.mapBoundary.positionChanged(oldPosition, newPosition, animal);
    }

    public LinkedHashMap<Vector2d, HashSet<Animal>> getAnimals(){return this.animals;}

    public Vector2d getLowerLeft(){return this.lowerLeft;}

    public Vector2d getUpperRight(){return this.upperRight;}

    public LinkedHashMap<Vector2d, Grass> getGrass(){return this.grassPositions;}

    public void updateListOfAnimals(Animal animal, Vector2d oldPosition){
        removeMovedAnimal(animal, oldPosition);
        addMovedAnimal(animal, animal.getPosition());
        updateFreePositions(oldPosition, animal.getPosition());
    }

    private ArrayList<Animal> getTwoStrongestAnimals(HashSet<Animal> hashSet){
        ArrayList<Animal> arrayList = new ArrayList<>(hashSet);
        ArrayList<Animal> twoStrongestAnimals = new ArrayList<>();
        arrayList.sort(AbstractWorldMap::compareOnEnergy);
        Animal animal1 = arrayList.get(0);
        twoStrongestAnimals.add(animal1);
        if(hashSet.size() > 1) {
            Animal animal2 = arrayList.get(1);
            twoStrongestAnimals.add(animal2);
        }
//        System.out.print("TWO STRONGEST ANIMALS: ");
//        System.out.println(twoStrongestAnimals);


        return twoStrongestAnimals;
    }

    private static int compareOnEnergy(Animal animal1, Animal animal2){
        if(animal1.energy == animal2.energy) {
            return 0;
        } else if(animal1.energy <= animal2.energy) {
            return -1;
        }
        return 1;
    }

    public void animalsEatGrass(){
        ArrayList<Vector2d> grassToRemove = new ArrayList<>();
        for(Grass grass : this.grassPositions.values()){
            Vector2d grassPosition = grass.getPosition();
            if(this.animals.containsKey(grassPosition)) {
                HashSet<Animal> hashSetAnimals = this.animals.get(grassPosition);
                if (hashSetAnimals.size() > 0) {
                    // choose the strongest animals that eat this grass
                    ArrayList<Animal> strongestAnimals = new ArrayList<>();
                    int biggestEnergy = getTwoStrongestAnimals(hashSetAnimals).get(0).energy;
                    for (Animal animal : hashSetAnimals) {
                        if (animal.energy == biggestEnergy) {
                            strongestAnimals.add(animal);
                        } else {
                            break;
                        }
                    }

                    // divide grass energy for each animal from the strongest ones
                    int dividedEnergy = (int) Math.floor((double) this.grassEnergy / strongestAnimals.size());
                    for (Animal animal : strongestAnimals) {
                        animal.addEnergy(dividedEnergy);
                    }
                }
                grassToRemove.add(grassPosition);
                this.amountOfGrass--;
            }
        }
        for(Vector2d position : grassToRemove){
            this.grassPositions.remove(position);
        }
    }

    public void removeDeadAnimal(Animal animal){
        this.animals.get(animal.getPosition()).remove(animal);
        this.aliveAnimals.remove(animal);
        if(this.animals.get(animal.getPosition()).isEmpty()){
            this.animals.remove(animal.getPosition());
        }
        removeGenotype(animal);
        this.deadAnimals.add(animal);
        addPosition(animal.getPosition());
    }

    public void updateFreePositions(Vector2d oldPosition, Vector2d newPosition){
        addPosition(oldPosition);

        if (newPosition.follows(this.jungleLowerLeft) && newPosition.precedes(this.jungleUpperRight)) {
            this.freeJunglePositions.remove(newPosition);
        } else {
            this.freePrairiePositions.remove(newPosition);
        }
    }

    public void addPosition(Vector2d oldPosition){
        if(!isOccupied(oldPosition)) {
            if (oldPosition.follows(this.jungleLowerLeft) && oldPosition.precedes(this.jungleUpperRight)) {
                this.freeJunglePositions.add(oldPosition);
            } else {
                this.freePrairiePositions.add(oldPosition);
            }
        }
    }

    private void findFreeJunglePositions(){
        for(int x = this.jungleLowerLeft.x; x <= this.jungleUpperRight.x; x++){
            for(int y = this.jungleLowerLeft.y; y <= this.jungleUpperRight.y; y++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freeJunglePositions.add(vector2d);
                }
            }
        }
    }

    private void findFreePrairiePositions(){
        // for x in [lowerLeft.x, jungleLowerLeft) union (jungleUpperRight.x, upperRight.x]
        // for y in [lowerLeft.y, upperRight.y]
        for(int y = this.lowerLeft.y; y <= this.upperRight.y; y++){
            for(int x = this.lowerLeft.x; x < this.jungleLowerLeft.x; x++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }

            for(int x = this.jungleUpperRight.x + 1; x <= this.upperRight.x; x++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }
        }

        // for x in [jungleLowerLeft.x, jungleUpperRight.x]
        // for y in [lowerLeft.y, jungleLowerLeft.y) union (jungleUpperRight.y, upperRight.y]
        for(int x = this.jungleLowerLeft.x; x <= this.jungleUpperRight.x; x++){
            for(int y = this.lowerLeft.y; y < this.jungleLowerLeft.y; y++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }

            for(int y = this.jungleUpperRight.y + 1; y <= this.upperRight.y; y++){
                Vector2d vector2d = new Vector2d(x, y);
                if(!isOccupied(vector2d)){
                    this.freePrairiePositions.add(vector2d);
                }
            }
        }
    }

    public ArrayList<Animal> getAliveAnimals() {
        return aliveAnimals;
    }

    public void magicHappen(){
        if(this.counterOfMagic < 3) {
            this.counterOfMagic++;
            ArrayList<Animal> copyOfAnimals = new ArrayList<>();
            for (Animal animal : this.aliveAnimals) {
                Vector2d position = animal.getPosition();
                int energy = this.maxAnimalEnergy;
                Animal copiedAnimal = new Animal(this, position, energy);
                copiedAnimal.genotype = new ArrayList<>(animal.genotype);
                copyOfAnimals.add(copiedAnimal);
            }
            for (Animal animal : copyOfAnimals) {
                place(animal);
            }
            // TODO komunikat w interfejsie o magicznosci
            System.out.println("MAGIC HAPPENED!");
        }
    }

    public int getNumberOfAliveAnimals() {return this.aliveAnimals.size();}

    public int getNumberOfGrass() {return this.amountOfGrass;}

    public float getAverageEnergyOfAliveAnimals() {
        int numberOfAliveAnimals = getNumberOfAliveAnimals();
        if(numberOfAliveAnimals == 0){
            return 0;
        }

        int sum = 0;
        for(Animal animal : this.aliveAnimals){
            sum+= animal.energy;
        }
        return (float) sum / numberOfAliveAnimals;
    }

    public float getAverageLifeDaysOfDeadAnimals() {
        int numberOfDeadAnimals = this.deadAnimals.size();
        if(numberOfDeadAnimals == 0){
            return 0;
        }

        int sum = 0;
        for(Animal animal : this.deadAnimals){
            sum+= animal.daysAlive;
        }
        return (float) sum / numberOfDeadAnimals;
    }

    public float getAverageNumberOfChildren(){
        int numberOfAnimals = getNumberOfAliveAnimals() + this.deadAnimals.size();
        if(numberOfAnimals == 0){
            return 0;
        }

        int sum = 0;
        for(Animal animal : this.aliveAnimals){
            sum+= animal.getNumberOfChildren();
        }
        for(Animal animal : this.deadAnimals){
            sum+= animal.getNumberOfChildren();
        }
        return (float) sum/numberOfAnimals;
    }

    //TODO przetestuj getery z wyzej i dodaj szukanie dominujacego genotypu
    public ArrayList<Integer> getModeOfGenotypes(){
        String dominantKey = null;
        int countDominantGenotype = 0;
        for(String key : this.genotypes.keySet()){
            int currentValue = this.genotypes.get(key);
            if(countDominantGenotype < currentValue){
                countDominantGenotype = currentValue;
                dominantKey = key;
            }
        }
        return decodeTheGenotype(dominantKey);
    }

    // TODO stworz klase gene zeby tam trzymac takie rzeczy?

    // metoda wywolywana tylko przy place oraz initialplace animals
    public void putGenotype(Animal animal){ // TODO PRIVATE
        ArrayList<Integer> genotype = new ArrayList<>(animal.genotype);
        String key = codeTheGenotype(genotype);
        Integer value = this.genotypes.getOrDefault(key, 0);
        this.genotypes.put(key, value + 1);
    }

    public void removeGenotype(Animal animal){
        ArrayList<Integer> genotype = new ArrayList<>(animal.genotype);
        String key = codeTheGenotype(genotype);
        this.genotypes.put(key, this.genotypes.get(key) - 1);
        if(this.genotypes.get(key) == 0){
            this.genotypes.remove(key);
        }
    }

    private String codeTheGenotype(ArrayList<Integer> genotype){
        //        0-1-2-3-4-5-6-7
        // klucz opisuje n-ty gen w String na miejscu n*2 oraz n*2 + 1
        StringBuilder key = new StringBuilder();
        ArrayList<Integer> intsToConvert = new ArrayList<>();
        // count each gene
        int counter0 = 0;
        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;
        int counter4 = 0;
        int counter5 = 0;
        int counter6 = 0;
        int counter7 = 0;

        for(int gene : genotype){
            switch (gene) {
                case 0 -> counter0++;
                case 1 -> counter1++;
                case 2 -> counter2++;
                case 3 -> counter3++;
                case 4 -> counter4++;
                case 5 -> counter5++;
                case 6 -> counter6++;
                case 7 -> counter7++;
            }
        }

        intsToConvert.add(counter0 + 10);
        intsToConvert.add(counter1 + 10);
        intsToConvert.add(counter2 + 10);
        intsToConvert.add(counter3 + 10);
        intsToConvert.add(counter4 + 10);
        intsToConvert.add(counter5 + 10);
        intsToConvert.add(counter6 + 10);
        intsToConvert.add(counter7 + 10);

//        System.out.println(intsToConvert);
        // -10 zeby dostac liczbe wystepowan danego genu
        for(int elem : intsToConvert){
            String convertedInt = String.valueOf(elem);
            key.append(convertedInt);
        }
//        System.out.println(key);
        return key.toString();
    }

    private ArrayList<Integer> decodeTheGenotype(String key){
        if(key == null) return null; // should never happen TODO THROW!
        ArrayList<Integer> genotype = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            String substr = key.substring(i*2, i*2 + 2); // it takes substring from index [i*2,i*2+1]
            int numberOfGene = Integer.parseInt(substr) - 10;
            while (numberOfGene > 0){
                genotype.add(i);
                numberOfGene--;
            }
        }

        return genotype;
    }

}
