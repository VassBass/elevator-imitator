package event;

import console.ColoredPrinter;
import lombok.NonNull;
import model.Building;
import model.Elevator;
import model.Floor;
import model.Person;
import service.ElevatorService;
import service.PeopleService;
import state.Direction;

import java.util.Collection;
import java.util.Optional;

/**
 * Event algorithm
 */
public class ChainOfEvents {
    private final Building building;
    private final Elevator elevator;

    private final PeopleService peopleService = new PeopleService();
    private final ElevatorService elevatorService = new ElevatorService();

    private ChainOfEvents(@NonNull Building building,@NonNull Elevator elevator){
        this.building = building;
        this.elevator = elevator;
    }

    public static ChainOfEvents of(@NonNull Building building, @NonNull Elevator elevator){
        return new ChainOfEvents(building, elevator);
    }

    /**
     * Starts a chain of events for {@link #building} with {@link #elevator}
     */
    public void start(){
        ColoredPrinter.printlnGreen("Simulation started for ->");
        ColoredPrinter.println(building.toString());
        ColoredPrinter.printSeparator();
        ColoredPrinter.println(elevator.toString());
        ColoredPrinter.printSeparator();

        event();
    }

    private Direction getDirection(@NonNull Floor floor){
        long up = floor.personList.stream().filter(p -> p.getDesiredFloor() > floor.getNumber()).count();
        long down = floor.personList.stream().filter(p -> p.getDesiredFloor() < floor.getNumber()).count();

        return up >= down ? Direction.UP : Direction.DOWN;
    }

    /**
     * The event contains three actions:
     * 1st - people exit the elevator if they arrived at the desired floor.
     * 2nd - people enter the elevator if they need to go in the same direction as the elevator is moving,
     * or if the elevator is empty.
     * 3rd - the elevator goes to the next floor.
     */
    private void event(){
        Optional<Floor> f = building.getFloor(elevator.getCurrentFloor());
        if (f.isPresent()) {
            ColoredPrinter.printlnGreen("The elevator stopped at the " + elevator.getCurrentFloor() + " floor");
            Floor floor = f.get();

            /*  1st action  */
            Collection<Person> peopleOut = peopleService.goFromElevatorToFloor(elevator, floor);
            peopleOut.forEach(p -> ColoredPrinter.printlnPurple(p + " stepped out of the elevator."));
            ColoredPrinter.printSeparator();
            if (elevator.isEmpty()) {
                elevator.setDirection(getDirection(floor));
            }

            /*  2nd action  */
            Collection<Person> peopleIn = peopleService.goFromFloorToElevator(floor, elevator);
            peopleIn.forEach(p -> ColoredPrinter.printlnPurple(p + " entered the elevator"));
            ColoredPrinter.printSeparator();

            /*  3rd action  */
            int currentFloor = elevator.getCurrentFloor();
            elevatorService.goNextFloor(building, elevator);

            /*  checks if any button is pressed. If not, the event chain will end.    */
            if (currentFloor == elevator.getCurrentFloor()) {
                ColoredPrinter.printlnGreen("All the people on their floors");
                ColoredPrinter.println(building.toString());
                ColoredPrinter.printlnGreen("The simulation is over.");
            }else {
                event();
            }
        }else {
            ColoredPrinter.printlnRed("Elevator is confused, floor with number "
                    + elevator.getCurrentFloor()
                    + " not exists...");
        }
    }
}
