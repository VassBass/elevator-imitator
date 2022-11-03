package service;

import model.Elevator;
import model.Floor;
import model.Person;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class PeopleService {

    public Collection<Person> goFromFloorToElevator(Floor floor, Elevator elevator){
        Person[] toIn;
        switch (elevator.getDirection()){
            case UP -> toIn = floor.personList.stream().filter(p -> p.getDesiredFloor() > floor.getNumber()).toArray(Person[]::new);
            case DOWN -> toIn = floor.personList.stream().filter(p -> p.getDesiredFloor() < floor.getNumber()).toArray(Person[]::new);
            default -> toIn = new Person[0];
        }

        if (toIn.length > elevator.getRemainingSpace()) {
            toIn = Arrays.copyOf(toIn, elevator.getRemainingSpace());
        }
        elevator.passengers.addAll(Arrays.asList(toIn));
        Arrays.asList(toIn).forEach(floor.personList::remove);
        return Arrays.asList(toIn);
    }

    public Collection<Person> goFromElevatorToFloor(Elevator elevator, Floor floor){
        Set<Person> toOut = elevator.passengers.stream().filter(p -> p.getDesiredFloor() == floor.getNumber()).collect(Collectors.toSet());
        floor.personList.addAll(toOut);
        elevator.passengers.removeAll(toOut);
        return toOut;
    }
}
