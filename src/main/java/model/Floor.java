package model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class Floor {

    @Getter
    private final int number;

    public Set<Person> personList = new HashSet<>();

    public Floor(int number){
        this.number = number;
    }

    public boolean buttonUpIsPressed(){
        return personList.stream().anyMatch(p -> p.getDesiredFloor() > number);
    }

    public boolean buttonDownIsPressed(){
        return personList.stream().anyMatch(p -> p.getDesiredFloor() < number);
    }
}
