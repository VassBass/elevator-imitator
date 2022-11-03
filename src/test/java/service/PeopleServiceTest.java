package service;

import model.Elevator;
import model.Floor;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import state.Direction;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PeopleServiceTest {

    private static final Floor testFloor = new Floor(5);
    private static final Elevator testElevator = new Elevator(5);
    private static final PeopleService service = new PeopleService();

    private void fillTestFloor(){
        testFloor.personList.addAll(List.of(
                new Person(1, 5),
                new Person(2, 5),
                new Person(3, 7),
                new Person(4, 8),
                new Person(5, 3),
                new Person(6, 2),
                new Person(7, 2),
                new Person(8, 6),
                new Person(9, 5),
                new Person(10, 1)
        ));
    }

    private void fillTestElevator(){
        testElevator.passengers.add(new Person(11, 5));
        testElevator.passengers.add(new Person(12, 1));
    }

    @BeforeEach
    void setUp() {
        fillTestFloor();
        fillTestElevator();
    }

    @AfterEach
    void tearDown() {
        testFloor.personList.clear();
        testElevator.passengers.clear();
    }

    @Test
    void testGoFromFloorToElevatorDirectionUp() {
        testElevator.setDirection(Direction.UP);

        List<Person> expected = List.of(
                new Person(3,7),
                new Person(4,8),
                new Person(8,6),
                new Person(11,5),
                new Person(12,1)
        );

        service.goFromFloorToElevator(testFloor, testElevator);

        List<Person> actual = testElevator.passengers.stream()
                .sorted(Comparator.comparingInt(Person::getId))
                .collect(Collectors.toList());
        assertIterableEquals(expected, actual);
    }

    @Test
    void testGoFromFloorToElevatorDirectionDown() {
        testElevator.setDirection(Direction.DOWN);

        List<Person> expected = List.of(
                new Person(5,3),
                new Person(6,2),
                new Person(7,2),
                new Person(11,5),
                new Person(12,1)
        );

        service.goFromFloorToElevator(testFloor, testElevator);

        List<Person> actual = testElevator.passengers.stream()
                .sorted(Comparator.comparingInt(Person::getId))
                .collect(Collectors.toList());
        assertIterableEquals(expected, actual);
    }

    @Test
    void goFromElevatorToFloor() {
        Set<Person> expected = Set.of(new Person(12,1));

        service.goFromElevatorToFloor(testElevator, testFloor);
        assertIterableEquals(expected, testElevator.passengers);
    }
}