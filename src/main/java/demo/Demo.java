package demo;

import event.ChainOfEvents;
import model.Building;
import model.Elevator;
import model.Person;

import java.util.Random;

/**
 * Demo.
 * Creates an elevator with a capacity of 5 people.
 * Creates a building with a random number of floors (range 5...20)
 * and a random number of people on each floor (range 0...10).
 * Each person needs to go to a random floor of the building.
 * All random numbers will be generated on every new launch.
 */
public class Demo {

    public static void main(String[] args) {
        Elevator elevator = new Elevator(5);
        Building building = createRandomBuilding();

        ChainOfEvents.of(building, elevator).start();
    }

    private static Building createRandomBuilding(){
        Random numberGenerator = new Random();
        Building building = new Building(numberGenerator.nextInt(5, 21));

        int personId = 0;
        for (int f = 1; f <= building.getNumberOfFloors(); f++){
            int numberOfPeople = numberGenerator.nextInt(0, 11);
            for (int n = 0; n < numberOfPeople; n++){
                int id = ++personId;
                int desiredFloor = numberGenerator.nextInt(1, building.getNumberOfFloors());
                building.getFloor(f).ifPresent(fl -> fl.personList.add(new Person(id, desiredFloor)));
            }
        }

        return building;
    }
}
