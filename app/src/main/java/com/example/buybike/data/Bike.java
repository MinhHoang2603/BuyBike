package com.example.buybike.data;

public abstract class Bike{
    protected String name;
    protected int price;
    protected String type;

    public Bike(String name, int price, String type) {
        this.name = name;
        setPrice(price);
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public String getType() {
        return type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        if (price < 0) {
            this.price = 0;
        }else {
            this.price = price;
        }
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + " - " + price + " - " + type;
    }
}
