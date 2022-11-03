package demo;

import event.ChainOfEvents;
import model.Building;
import model.Elevator;

public class YoursDemo {

    public static void main(String[] args) {
        Building building = createBuilding();
        Elevator elevator = createElevator();
        ChainOfEvents.of(building, elevator).start();
    }

    public static Elevator createElevator(){
        //todo create your elevator
        return null;
    }

    public static Building createBuilding(){
        //todo create your building (with people on floors)

//        @Example
//        Building building = new Building(10);
//        building.getFloor(1).ifPresent(f -> f.personList.addAll(List.of(
//                new Person(1, 5),
//                new Person(2, 5),
//                new Person(3, 8)
//        )));
//
//        building.getFloor(3).ifPresent(f -> f.personList.addAll(List.of(
//                new Person(4, 5),
//                new Person(5, 7),
//                new Person(6, 2),
//                new Person(7, 1)
//        )));
//
//        building.getFloor(5).ifPresent(f -> f.personList.addAll(List.of(
//                new Person(8, 8),
//                new Person(9, 8)
//        )));
//
//        building.getFloor(8).ifPresent(f -> f.personList.addAll(List.of(
//                new Person(10, 10),
//                new Person(11, 8),
//                new Person(12, 2),
//                new Person(13, 5),
//                new Person(14, 2)
//        )));
//
//        return building;

        return null;
    }
}
