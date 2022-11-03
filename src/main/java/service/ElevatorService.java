package service;

import lombok.NonNull;
import model.Building;
import model.Elevator;
import model.Floor;
import model.Person;

import java.util.Comparator;
import java.util.Optional;

public class ElevatorService {

    /**
     * Go to the next floor based on the state of the elevator and the building.
     * @param building
     * @param elevator
     */
    public void goNextFloor(@NonNull Building building,@NonNull Elevator elevator){
        if (elevator.isFull()){
            Optional<Person> next;
            switch (elevator.getDirection()){
                case UP -> next = elevator.passengers.stream().min(Comparator.comparing(Person::getDesiredFloor));
                case DOWN -> next = elevator.passengers.stream().max(Comparator.comparing(Person::getDesiredFloor));
                default -> next = Optional.empty();
            }
            next.ifPresent(p -> elevator.setCurrentFloor(p.getDesiredFloor()));

        }else if (elevator.isEmpty()){
            elevator.setCurrentFloor(getNextFloorEmptyElevator(building));
        }else {
            switch (elevator.getDirection()) {
                case UP -> elevator.setCurrentFloor(getNextFloorUp(building, elevator));
                case DOWN -> elevator.setCurrentFloor(getNextFloorDown(building, elevator));
            }
        }
    }

    /**
     * @param building
     * @param elevator
     * @return the number of the nearest floor,
     * in the upward direction from the current one,
     * on which the UP button was pressed
     */
    private int getNextFloorUp(Building building, Elevator elevator){
        int nextFloor = elevator.getCurrentFloor() + 1;
        Optional<Integer> nearestFloor = elevator.passengers.stream()
                .min(Comparator.comparing(Person::getDesiredFloor))
                .map(Person::getDesiredFloor);

        if (nearestFloor.isPresent() && nearestFloor.get() == nextFloor){
            return nextFloor;
        }else {
            elevator.setCurrentFloor(nextFloor);
            Optional<Floor> f = building.getFloor(elevator.getCurrentFloor());
            if (f.isPresent()) {
                Floor floor = f.get();
                if (floor.buttonUpIsPressed()) {
                    return floor.getNumber();
                } else {
                    return getNextFloorUp(building, elevator);
                }
            } else {
                return building.getNumberOfFloors();
            }
        }
    }

    /**
     * @param building
     * @param elevator
     * @return the number of the nearest floor,
     * in the downward direction from the current one,
     * on which the DOWN button was pressed
     */
    private int getNextFloorDown(Building building, Elevator elevator){
        int nextFloor = elevator.getCurrentFloor() - 1;
        Optional<Integer> nearestFloor = elevator.passengers.stream()
                .max(Comparator.comparing(Person::getDesiredFloor))
                .map(Person::getDesiredFloor);

        if (nearestFloor.isPresent() && nearestFloor.get() == nextFloor){
            return nextFloor;
        }else {
            elevator.setCurrentFloor(nextFloor);
            Optional<Floor> f = building.getFloor(elevator.getCurrentFloor());
            if (f.isPresent()) {
                Floor floor = f.get();
                if (floor.buttonDownIsPressed()) {
                    return floor.getNumber();
                } else {
                    return getNextFloorDown(building, elevator);
                }
            } else {
                return building.getNumberOfFloors();
            }
        }
    }

    /**
     * Checks all floors, starting from the 1st, for the presence of any pressed button.
     * @param building
     * @return floor number with pressed button
     * or 1 if no buttons were pressed
     */
    private int getNextFloorEmptyElevator(Building building){
        for (int i = 1; i <= building.getNumberOfFloors(); i++){
            Optional<Floor> f = building.getFloor(i);
            if (f.isPresent()){
                Floor floor = f.get();
                if (floor.personList.stream().anyMatch(p -> p.getDesiredFloor() != floor.getNumber())){
                    return floor.getNumber();
                }
            }
        }

        return 1;
    }
}
