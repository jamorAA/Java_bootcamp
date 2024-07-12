package org.example.classes;

import java.util.StringJoiner;

public class Car {
    private final String model;
    private int weight;
    private final int year;
    private final int maxspeed;

    public Car() {
        this.model = "Default";
        this.weight = 0;
        this.year = 0;
        this.maxspeed = 0;
    }
    public Car(String model, int weight, int year, int maxspeed) {
        this.model = model;
        this.weight = weight;
        this.year = year;
        this.maxspeed = maxspeed;
    }
    public double growWeight(int value) {
        this.weight += value;
        return weight;
    }
    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("weight='" + weight + "'")
                .add("year=" + year)
                .add("max speed=" + maxspeed)
                .toString();
    }
}
