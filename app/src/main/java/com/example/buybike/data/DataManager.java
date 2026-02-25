package com.example.buybike.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {
    private static DataManager instance;
    private List<Bike> bikes;
    private User user;

    private DataManager() {
        initializeData();
    }
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    private void initializeData() {
        bikes = new ArrayList<>();
        user = new User("Default User");

        bikes.add(new RoadBike("Pinarello", 1800));
        bikes.add(new RoadBike("Pina Bike", 1500));
        bikes.add(new RoadBike("Halo Bike", 1900));
        bikes.add(new MountainBike("Pina Mountain", 1700));
        bikes.add(new MountainBike("Hit Mountain", 1350));
    }
    public List<Bike> getBikesByType(String type) {
        return bikes.stream()
                .filter(bike -> bike.getType().contains(type))
                .collect(Collectors.toList());
    }
    public List<Bike> getBikes() {
        return new ArrayList<>(bikes);
    }
    public User getUser() {
        return user;
    }
}
