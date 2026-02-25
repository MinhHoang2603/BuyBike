package com.example.buybike.data;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final List<Bike> bikes;
    private final List<Bike> liked;

    public User(String name) {
        this.name = name;
        this.bikes = new ArrayList<>();
        this.liked = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public List<Bike> getBikes() {
        return bikes;
    }
    public List<Bike> getLiked() {
        return liked;
    }
    public void addBike(Bike bike) {
        bikes.add(bike);
    }
    public void addLikedBike(Bike bike) {
        if (!liked.contains(bike)) {
            liked.add(bike);
        }
    }
    public void removeLikedBike(Bike bike) {
        liked.remove(bike);
    }
    public boolean isLikedBike(Bike bike) {
        return liked.contains(bike);
    }
    public List<Bike> getLikedBikes() {
        return liked;
    }
}
