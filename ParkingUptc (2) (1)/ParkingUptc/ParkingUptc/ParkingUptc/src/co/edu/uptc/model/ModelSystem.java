package co.edu.uptc.model;

import co.edu.uptc.presenter.Contract;
import java.time.*;
import java.util.*;

public class ModelSystem implements Contract.Model {
    private List<User> users;
    private List<Ticket> activeTickets;
    private List<Parking> parkings;
    private User currentUser;
    private static final double HOURLY_RATE = 3000.0;

    public ModelSystem() {
        users = new ArrayList<>();
        activeTickets = new ArrayList<>();
        parkings = new ArrayList<>();
        initializeTestData();
    }

    private void initializeTestData() {
        users.add(new User("1", "Admin", "Sistema", "admin", "admin123", "admin",
                "0000000", "UPTC", "admin@parkinguptc.com", "123456789"));
        users.add(new User("2", "Recepcionista", "Principal", "recep", "recep123",
                "receptionist", "1111111", "UPTC", "recep@parkinguptc.com",
                "987654321"));

        Schedule[] schedules = { new Schedule("Lunes-Viernes", "07:00", "19:00") };
        parkings.add(new Parking("ParkingUPTC Central", "UPTC Tunja", 10, schedules));
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword);
                return true;
            }
        }
        return false;
    }

    @Override
    public User validateUser(String username, String password) {
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username) &&
                        u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (user != null) {
            currentUser = user;
        }
        return user;
    }

    @Override
    public Ticket registerVehicleEntry(String plate, String vehicleType) {
        if (getAvailableSpaces() <= 0)
            return null;

        Vehicle vehicle = new Vehicle(plate, vehicleType);
        Ticket ticket = new Ticket(vehicle, LocalDateTime.now(), parkings.get(0), currentUser);
        activeTickets.add(ticket);
        return ticket;
    }

    @Override
    public double calculateParkingFee(String plate) {
        Optional<Ticket> ticket = activeTickets.stream()
                .filter(t -> t.getVehicle().getPlate().equalsIgnoreCase(plate.trim()))
                .findFirst();

        if (ticket.isPresent()) {
            Duration duration = Duration.between(
                    ticket.get().getArrivalDateTime(),
                    LocalDateTime.now());
            long hours = duration.toHours();
            if (duration.toMinutesPart() > 0)
                hours++;
            return Math.max(HOURLY_RATE, hours * HOURLY_RATE);
        }
        return 0;
    }

    @Override
    public Ticket processVehicleExit(String plate, double amountPaid) {
        Optional<Ticket> ticketOpt = activeTickets.stream()
                .filter(t -> t.getVehicle().getPlate().equalsIgnoreCase(plate.trim()))
                .findFirst();

        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            double fee = calculateParkingFee(plate);

            if (amountPaid < fee)
                return null;

            ticket.setDepartureDateTime(LocalDateTime.now());
            ticket.setFee(fee);
            ticket.setAmountPaid(amountPaid);

            activeTickets.remove(ticket);
            return ticket;
        }
        return null;
    }

    @Override
    public int getAvailableSpaces() {
        return parkings.get(0).getTotalSpaces() - activeTickets.size();
    }

    @Override
    public void addParking(Parking parking) {
        parkings.add(parking);
    }

    @Override
    public void addReceptionist(User receptionist) {
        String username = receptionist.getEmail().split("@")[0];
        String password = "recep" + String.format("%04d", users.size());
        receptionist.setUsername(username);
        receptionist.setPassword(password);
        users.add(receptionist);
    }

    @Override
    public boolean existsActiveTicket(String plate) {
        return activeTickets.stream()
                .anyMatch(t -> t.getVehicle().getPlate().equalsIgnoreCase(plate.trim()));
    }
}
