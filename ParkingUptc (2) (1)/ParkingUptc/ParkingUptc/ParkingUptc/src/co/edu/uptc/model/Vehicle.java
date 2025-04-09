package co.edu.uptc.model;

public class Vehicle {
    private String plate;
    private String type;

    

    public Vehicle() {
    }

    public Vehicle(String plate, String type) {
        this.plate = plate.toUpperCase();
        this.type = type;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vehicle [plate=" + plate + ", type=" + type + "]";
    }

    
}