package co.edu.uptc.model;

import java.time.LocalDateTime;

public class Ticket {
    private Vehicle vehicle;
    private LocalDateTime arrivalDateTime;
    private LocalDateTime departureDateTime;
    private Parking parking;
    private double fee;
    private double amountPaid;
    private User receptionist;

    public Ticket(Vehicle vehicle, LocalDateTime arrivalDateTime, Parking parking, User receptionist) {
        this.vehicle = vehicle;
        this.arrivalDateTime = arrivalDateTime;
        this.parking = parking;
        this.receptionist = receptionist;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public User getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(User receptionist) {
        this.receptionist = receptionist;
    }

}
