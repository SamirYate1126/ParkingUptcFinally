package co.edu.uptc.model;

import java.util.Arrays;

public class Parking {
    private String name;
    private String address;
    private int totalSpaces;
    private Schedule[] schedules;

    

    public Parking() {
    }




    public Parking(String name, String address, int totalSpaces, Schedule[] schedules) {
        this.name = name;
        this.address = address;
        this.totalSpaces = totalSpaces;
        this.schedules = schedules;
    }

    
    

    public String getName() {
        return name;
    }




    public void setName(String name) {
        this.name = name;
    }




    public String getAddress() {
        return address;
    }




    public void setAddress(String address) {
        this.address = address;
    }




    public int getTotalSpaces() {
        return totalSpaces;
    }




    public void setTotalSpaces(int totalSpaces) {
        this.totalSpaces = totalSpaces;
    }




    public Schedule[] getSchedules() {
        return schedules;
    }




    public void setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
    }




    @Override
    public String toString() {
        return "Parking [name=" + name + ", address=" + address + ", totalSpaces=" + totalSpaces + ", schedules="
                + Arrays.toString(schedules) + "]";
    }

    
}
