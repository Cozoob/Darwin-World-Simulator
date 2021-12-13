package agh.ics.oop.AbstractClasses;
import agh.ics.oop.Engine.MapBoundary;
import agh.ics.oop.WorldElements.Animal;
import agh.ics.oop.WorldElements.Grass;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Interfaces.IWorldMap;
import agh.ics.oop.Engine.MapVisualizer;
import agh.ics.oop.WorldElements.Vector2d;

import java.util.*;

import static java.lang.System.out;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    public Random rand = new Random(42); // do testow - pozniej usun!

    public LinkedHashMap<Vector2d, Grass> grassPositions; // protected
    public LinkedHashMap<Vector2d, TreeSet<Animal>> animals; // protected
    public ArrayList<Animal> deadAnimals = new ArrayList<>(); // protected
    public ArrayList<Animal> aliveAnimals = new ArrayList<>(); // protected
    protected Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRight;
    public Vector2d jungleLowerLeft; // protected
    public Vector2d jungleUpperRight; // protected
    protected MapBoundary mapBoundary; // final ?
    public boolean isFreePositionOnJungle = true; // protected
    public boolean isFreePositionOnPrairie = true; // protected
    public int numberOfAnimals; // protected
    public ArrayList<Vector2d> freeJunglePositions = new ArrayList<>(); // protected
    public ArrayList<Vector2d> freePrairiePositions = new ArrayList<>(); // protected
    // roslina poza jungle rosna w wiekszym rozproszeniu po prostu...
    public int grassEnergy; // protected
    public int maxAnimalEnergy; // protected

    // do usuniecia
    public int firstHalfOfGrass;
    public int secondHalfOfGrass;

    // sprawdz jakie wartosci z inputu moga cos zepsuc... wyrzuc wtedy wyjatki
    public AbstractWorldMap(int maxAnimalEnergy ,int grassEnergy, int numberOfAnimals, int amountOfGrass, int width, int height, int jungleWidth, int jungleHeight){
        if (jungleHeight > height || jungleWidth > width){
            System.out.println("ZLE");
            // wyrzuc wyjatek!
        }
        this.maxAnimalEnergy = maxAnimalEnergy;
        this.grassEnergy = grassEnergy;
        this.numberOfAnimals = numberOfAnimals;
        this.animals = new LinkedHashMap<Vector2d, TreeSet<Animal>>();
        this.aliveAnimals = new ArrayList<Animal>();
        this.grassPositions = new LinkedHashMap<>();
        this.mapBoundary = new MapBoundary(this);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.jungleLowerLeft = new Vector2d( (int) Math.ceil((double) width / 2 -  (double) jungleWidth / 2), (int) Math.ceil((double)height / 2 - (double) jungleHeight / 2));
        this.jungleUpperRight = new Vector2d( (int) Math.ceil((double) width / 2 +  (double) jungleWidth / 2) - 1, (int) Math.ceil((double)height / 2 + (double) jungleHeight / 2) - 1);
        int firstHalfOfGrass = (int) Math.ceil((double)amountOfGrass / 2);
        int secondHalfOfGrass = amountOfGrass - firstHalfOfGrass;
        // start do usuniecia{
        this.firstHalfOfGrass = firstHalfOfGrass;
        this.secondHalfOfGrass = secondHalfOfGrass;
        // }koniec do usuniecia
        findFreeJunglePositions();
        findFreePrairiePositions();

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
            Collections.shuffle(this.freeJunglePositions, this.rand); // do testow tylko
//        Collections.shuffle(this.freeJunglePositions);
            int index = this.freeJunglePositions.size() - 1;
            Vector2d randomVector = this.freeJunglePositions.get(index);
            this.freeJunglePositions.remove(index);
            this.grassPositions.put(randomVector, new Grass(randomVector, this.grassEnergy));
            this.mapBoundary.addPosition(randomVector);
        }
    }

    public void putGrassOnPrairie(){
        if(this.freePrairiePositions.size() > 0) {
            Collections.shuffle(this.freePrairiePositions, this.rand); // do testow tylko
//        Collections.shuffle(this.freePrairiePositions);
            int index = this.freePrairiePositions.size() - 1;
            Vector2d randomVector = this.freePrairiePositions.get(index);
            this.freePrairiePositions.remove(index);
            this.grassPositions.put(randomVector, new Grass(randomVector, this.grassEnergy));
            this.mapBoundary.addPosition(randomVector);
        }
    }

    public boolean initialPlace(Animal animal){
        Vector2d position = animal.getPosition();

        if(!isOccupied(position)){
            place(animal);
        }
        throw new IllegalArgumentException("\"" + animal.getPosition() + "\" field is invalid");
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();

        if (!this.animals.containsKey(position)){
            this.animals.put(position, new TreeSet<Animal>(AbstractWorldMap::compareOnEnergy));
        }

        this.animals.get(position).add(animal);
        this.aliveAnimals.add(animal);
        this.mapBoundary.addPosition(position);
        if(position.precedes(this.jungleUpperRight) && position.follows(this.jungleLowerLeft)){
            this.freeJunglePositions.remove(position); // moze byc wolne?
        } else {
            this.freePrairiePositions.remove(position);
        }
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
        for(TreeSet<Animal> treeSet : this.animals.values()){
            if(treeSet.size() > 1){
                Animal animal1 = treeSet.first();
                Animal animal2 = treeSet.higher(animal1);
                assert animal2 != null; // to zamien pozniej na throwsa po prostu
//                out.println(animal1.energy);
//                out.println(animal2.energy);
                int ratio1 = (int) Math.floor((double) animal1.energy * 100/ (animal1.energy + animal2.energy)); // ratio1 animal1.energy : (animal1.energy + animal2.energy)
                int ratio2 = (int) Math.floor((double) animal2.energy * 100/ (animal1.energy + animal2.energy)); // ratio1 animal1.energy : (animal1.energy + animal2.energy)
                out.println(ratio1);
                out.println(ratio2);

                Vector2d initialPosition = new Vector2d(animal1.getPosition().x, animal1.getPosition().y);
                int childEnergy = (int) Math.floor((double) animal1.energy / 4) + (int) Math.floor((double)animal2.energy / 4);
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
//                randomBoolean = true; // tylko do testow - usun pozniej
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
                this.place(child);
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
        this.animals.computeIfAbsent(newPosition, k -> new TreeSet<Animal>(AbstractWorldMap::compareOnEnergy));
        this.animals.get(newPosition).add(animal);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        // raczej do usuniecia
//        this.animals.remove(oldPosition);
//        this.animals.put(newPosition, animal);
        this.mapBoundary.positionChanged(oldPosition, newPosition, animal);
    }

//    public LinkedHashMap<Vector2d, Animal> getAnimals(){return this.animals;}

    public Vector2d getLowerLeft(){return this.lowerLeft;}

    public Vector2d getUpperRight(){return this.upperRight;}

    public LinkedHashMap<Vector2d, Grass> getGrass(){return this.grassPositions;}

    public void updateListOfAnimals(Animal animal, Vector2d oldPosition){
        removeMovedAnimal(animal, oldPosition);
        addMovedAnimal(animal, animal.getPosition());
        updateFreePositions(oldPosition, animal.getPosition());
    }

    public void animalsEatGrass(){
        ArrayList<Vector2d> grassToRemove = new ArrayList<>();
        for(Grass grass : this.grassPositions.values()){
            Vector2d grassPosition = grass.getPosition();
            if(this.animals.containsKey(grassPosition)) {
                TreeSet<Animal> treeAnimal = this.animals.get(grassPosition);
                if (treeAnimal.size() > 0) {
                    // choose the strongest animals that eat this grass
                    ArrayList<Animal> strongestAnimals = new ArrayList<>();
                    int biggestEnergy = treeAnimal.first().energy;
                    for (Animal animal : treeAnimal) {
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

    private static int compareOnEnergy(Animal animal1, Animal animal2){
        if(animal1.equals(animal2)){
            return 0;
        } else if(animal1.energy >= animal2.energy) {
            return -1;
        }
        return 1;
    }

    public ArrayList<Animal> getAliveAnimals() {
        return aliveAnimals;
    }
}
