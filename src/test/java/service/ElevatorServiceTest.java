package service;

import model.Building;
import model.Elevator;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import state.Direction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElevatorServiceTest {

    private static final ElevatorService service = new ElevatorService();

    private static Building building;

    @BeforeEach
    void setUp() {
        building = new Building(10);
        building.getFloor(1).ifPresent(f -> f.personList.addAll(List.of(
                new Person(1, 5),
                new Person(2, 5),
                new Person(3, 8)
        )));

        building.getFloor(3).ifPresent(f -> f.personList.addAll(List.of(
                new Person(4, 5),
                new Person(5, 7),
                new Person(6, 2),
                new Person(7, 1)
        )));

        building.getFloor(5).ifPresent(f -> f.personList.addAll(List.of(
                new Person(8, 8),
                new Person(9, 8)
        )));

        building.getFloor(8).ifPresent(f -> f.personList.addAll(List.of(
                new Person(10, 10),
                new Person(11, 8),
                new Person(12, 2),
                new Person(13, 5),
                new Person(14, 2)
        )));
    }

    @AfterEach
    void tearDown() {
        building.getFloor(1).ifPresent(f -> f.personList.clear());
        building.getFloor(5).ifPresent(f -> f.personList.clear());
        building.getFloor(3).ifPresent(f -> f.personList.clear());
        building.getFloor(8).ifPresent(f -> f.personList.clear());
    }

    @Test
    void testGoNextFloorFullElevatorUp() {
        Elevator elevator = new Elevator(2);
        elevator.setDirection(Direction.UP);
        elevator.passengers.addAll(List.of(
                new Person(15, 8),
                new Person(16, 10)
        ));

        assertEquals(1, elevator.getCurrentFloor());
        service.goNextFloor(building, elevator);
        assertEquals(8, elevator.getCurrentFloor());
    }

    @Test
    void testGoNextFloorFullElevatorDown() {
        Elevator elevator = new Elevator(2);
        elevator.setDirection(Direction.DOWN);
        elevator.setCurrentFloor(10);
        elevator.passengers.addAll(List.of(
                new Person(15, 2),
                new Person(16, 5)
        ));

        assertEquals(10, elevator.getCurrentFloor());
        service.goNextFloor(building, elevator);
        assertEquals(5, elevator.getCurrentFloor());
    }

    @Test
    void testGoNextFloorNotFullElevatorUp() {
        Elevator elevator = new Elevator(5);
        elevator.setDirection(Direction.UP);
        elevator.passengers.addAll(List.of(
                new Person(15, 8),
                new Person(16, 10)
        ));

        assertEquals(1, elevator.getCurrentFloor());
        service.goNextFloor(building, elevator);
        assertEquals(3, elevator.getCurrentFloor());
    }

    @Test
    void testGoNextFloorNotFullElevatorDown() {
        Elevator elevator = new Elevator(5);
        elevator.setDirection(Direction.DOWN);
        elevator.setCurrentFloor(10);
        elevator.passengers.addAll(List.of(
                new Person(15, 2),
                new Person(16, 5)
        ));

        assertEquals(10, elevator.getCurrentFloor());
        service.goNextFloor(building, elevator);
        assertEquals(8, elevator.getCurrentFloor());
    }

    @Test
    void testGoNextFloorEmptyElevator() {
        Elevator elevator = new Elevator(5);
        elevator.setCurrentFloor(7);
        building.getFloor(1).ifPresent(f -> f.personList.clear());

        assertEquals(7, elevator.getCurrentFloor());
        service.goNextFloor(building, elevator);
        assertEquals(3, elevator.getCurrentFloor());
    }
}