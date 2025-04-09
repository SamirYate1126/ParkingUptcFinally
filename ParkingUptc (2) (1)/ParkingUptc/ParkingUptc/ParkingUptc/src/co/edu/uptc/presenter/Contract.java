package co.edu.uptc.presenter;

import co.edu.uptc.model.*;

public interface Contract {
    interface BaseView {
        void showMessage(String message);
    }

    interface LoginView extends BaseView {
        void openRoleWindow(String role, String firstName);
    }

    interface ReceptionistView extends BaseView {
        void updateAvailableSpaces(int spaces, boolean warn);
        void showTicket(String plate, String type, String dateTime, String parkingName);
        void showPaymentReceipt(String plate, String type, String entryTime, 
                              String exitTime, String duration, String fee, 
                              String amountPaid, String change);
    }

    interface AdminView extends BaseView {
        void showParkingDetails(Parking parking);
        void showReceptionistDetails(User receptionist);
    }

    interface Model {
        User validateUser(String username, String password);
        Ticket registerVehicleEntry(String plate, String vehicleType);
        double calculateParkingFee(String plate);
        Ticket processVehicleExit(String plate, double amountPaid);
        int getAvailableSpaces();
        void addParking(Parking parking);
        void addReceptionist(User receptionist);
        boolean existsActiveTicket(String plate);
        boolean changePassword(String username, String newPassword);

    }

    interface Presenter {
        void setView(BaseView view);
        void validateUser(String username, String password);
        void registerVehicleEntry(String plate, String vehicleType);
        void processVehicleExit(String plate, double amountPaid);
        void updateAvailableSpaces();
        void addParking(Parking parking);
        void addReceptionist(User receptionist);
        void openChangeCredentialsWindow();
        boolean existsActiveTicket(String plate);
    }
}