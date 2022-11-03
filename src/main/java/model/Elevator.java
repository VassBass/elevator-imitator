package model;

import lombok.Getter;
import lombok.Setter;
import state.Direction;

import java.util.HashSet;
import java.util.Set;

public class Elevator {

    @Getter @Setter
    private int currentFloor = 1;

    @Getter @Setter
    private Direction direction = Direction.UP;

    private final int capacity;
    public final Set<Person> passengers;

    public Elevator(int capacity){
        this.capacity = capacity;
        this.passengers = new HashSet<>(capacity);
    }

    public int getRemainingSpace(){
        return capacity - passengers.size();
    }

    public boolean isFull(){
        return passengers.size() >= capacity;
    }

    public boolean isEmpty() {
        return passengers.isEmpty();
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "capacity=" + capacity +
                '}';
    }
}
