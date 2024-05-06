package com.model;

import java.io.Serializable;

public class Item implements Serializable {

    private Integer itemID;
    private String name;
    private String color;
    private String brand;
    private String cond;
    private Double price;
    private Integer typeID;
    private String shirtSize;
    private Integer pantSize;
    private Integer shoeSize;

    public Item() {
    }

    public Item(Integer itemID, Double price, String name, String color, String cond, String brand, Integer typeID) {
        this.itemID = itemID;
        this.name = name;
        this.color = color;
        this.brand = brand;
        this.cond = cond;
        this.price = price;
        this.typeID = typeID;
    }

    public void setItemID(Integer i) {
        this.itemID = i;
    }

    public int getItemID() {
        return itemID;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public void setColor(String c) {
        this.color = c;
    }

    public String getColor() {
        return color;
    }

    public void setBrand(String b) {
        this.brand = b;
    }

    public String getBrand() {
        return brand;
    }

    public void setCond(String c) {
        this.cond = c;
    }

    public String getCond() {
        return cond;
    }

    public void setPrice(Double p) {
        this.price = p;
    }

    public Double getPrice() {
        return price;
    }

    public void setShirtSize(String s) {
        this.shirtSize = s;
    }

    public String getShirtSize() {
        return shirtSize;
    }

    public void setPantSize(Integer s) {
        this.pantSize = s;
    }

    public Integer getPantSize() {
        return pantSize;
    }

    public void setShoeSize(Integer s) {
        this.shoeSize = s;
    }

    public Integer getShoeSize() {
        return shoeSize;
    }

    public void setTypeID(Integer i) {
        this.typeID = i;
    }

    public Integer getTypeID() {
        return typeID;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Item) ? itemID == ((Item) other).itemID : (other == this);
    }

    @Override
    public String toString() {
        return String.format("Item[itemID=%d,name=%s,color=%s,brand=%s]", itemID, name, color, brand);
    }
}