package model;

import java.util.Arrays;
import java.util.Optional;

public class Building {

    private final Floor[] floors;

    public Building(int numberOfFloors){
        floors = new Floor[numberOfFloors];
        for (int n = 1; n <= numberOfFloors; n++){
            floors[n-1] = new Floor(n);
        }
    }

    public int getNumberOfFloors() {
        return floors.length;
    }

    public Optional<Floor> getFloor(int number){
        return Arrays.stream(floors).filter(f -> f.getNumber() == number).findAny();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Building - ");
        builder.append("floors = ").append(getNumberOfFloors());

        for (Floor floor : floors) {
            builder.append('\n').append("#").append(floor.getNumber());
            if (floor.personList.isEmpty()) {
                builder.append('\n').append("-empty");
            } else {
                floor.personList.forEach(p -> builder.append("\n-").append(p));
            }
        }
        return builder.toString();
    }
}
