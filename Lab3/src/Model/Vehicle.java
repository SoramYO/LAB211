/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Son
 */
public class Vehicle {

    private int ID_Vehicle;
    private String Name_Vehicle;
    private String Color_Vehicle;
    private double Price_Vehicle;
    private String Brand_Vehicle;
    private String Type;
    private int ProductYear;

    public Vehicle(int id, String name, String color, double price, String brand, String type, int year) {
        this.ID_Vehicle = id;
        this.Name_Vehicle = name;
        this.Color_Vehicle = color;
        this.Price_Vehicle = price;
        this.Brand_Vehicle = brand;
        this.Type = type;
        this.ProductYear = year;
    }

    public String getColor_Vehicle() {
        return Color_Vehicle;
    }

    public void setColor_Vehicle(String Color_Vehicle) {
        this.Color_Vehicle = Color_Vehicle;
    }

    public double getPrice_Vehicle() {
        return Price_Vehicle;
    }

    public void setPrice_Vehicle(double Price_Vehicle) {
        this.Price_Vehicle = Price_Vehicle;
    }

    public String getBrand_Vehicle() {
        return Brand_Vehicle;
    }

    public void setBrand_Vehicle(String Brand_Vehicle) {
        this.Brand_Vehicle = Brand_Vehicle;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getProductYear() {
        return ProductYear;
    }

    public void setProductYear(int ProductYear) {
        this.ProductYear = ProductYear;
    }

    public int getID_Vehicle() {
        return ID_Vehicle;
    }

    public String getName_Vehicle() {
        return Name_Vehicle;
    }

    @Override
    public String toString() {
        return ID_Vehicle + "," + Name_Vehicle + "," + Color_Vehicle + "," + Price_Vehicle + "," + Brand_Vehicle + "," + Type + "," + ProductYear;
    }

}
