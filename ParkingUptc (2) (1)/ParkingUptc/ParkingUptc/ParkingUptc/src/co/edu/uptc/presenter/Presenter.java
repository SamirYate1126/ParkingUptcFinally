package co.edu.uptc.presenter;

import co.edu.uptc.model.*;
import java.time.format.DateTimeFormatter;

import co.edu.uptc.view.AdminWindow;
import co.edu.uptc.view.FrameChangeCredentials;


public class Presenter implements Contract.Presenter {
    private static Presenter instance;
    private PasswordValidate passwordModel = new PasswordValidate();
    private Contract.Model model;
    private Contract.BaseView view;

    private Presenter() {
        model = new ModelSystem();
    }

    public static Presenter getInstance() {
        if (instance == null) {
            instance = new Presenter();
        }
        return instance;
    }

    public void handleChangePassword(FrameChangeCredentials frame) {
        String username = frame.getDocument();

        String newPassword = frame.getPassword();
        String repeatPassword = frame.getRepeatPassword();
    
        if (!passwordModel.arePasswordsEqual(newPassword, repeatPassword)) {
            frame.showMessage("Las contraseñas no coinciden.");
            return;
        }
    
        if (!passwordModel.isValidPassword(newPassword)) {
            frame.showMessage("La contraseña debe tener exactamente 8 caracteres y no puede tener símbolos especiales.");
            return;
        }
    
        boolean changed = model.changePassword(username, newPassword);
    
        if (changed) {
            frame.showMessage("Contraseña actualizada con éxito.");
            frame.dispose();
            new AdminWindow().setVisible(true);
        } else {
            frame.showMessage("Error: usuario no encontrado.");
        }
    }
    

    @Override
    public void setView(Contract.BaseView view) {
        this.view = view;
    }

    @Override
    public void validateUser(String username, String password) {
        User user = model.validateUser(username, password);
        if (user != null) {
            if (view instanceof Contract.LoginView) {
                ((Contract.LoginView) view).openRoleWindow(user.getRole(), user.getFirstName());
            }
        } else {
            view.showMessage("Credenciales inválidas");
        }
    }

    @Override
    public void registerVehicleEntry(String plate, String vehicleType) {
        Ticket ticket = model.registerVehicleEntry(plate, vehicleType);
        if (ticket != null && view instanceof Contract.ReceptionistView) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            ((Contract.ReceptionistView) view).showTicket(
                    ticket.getVehicle().getPlate(),
                    ticket.getVehicle().getType(),
                    ticket.getArrivalDateTime().format(formatter),
                    ticket.getParking().getName());
            updateAvailableSpaces();
        } else {
            view.showMessage("No hay espacios disponibles");
        }
    }

    @Override
    public void processVehicleExit(String plate, double amountPaid) {
        Ticket ticket = model.processVehicleExit(plate, amountPaid);
        if (ticket != null && view instanceof Contract.ReceptionistView) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            long hours = java.time.temporal.ChronoUnit.HOURS.between(
                    ticket.getArrivalDateTime(),
                    ticket.getDepartureDateTime());
            long minutes = java.time.temporal.ChronoUnit.MINUTES.between(
                    ticket.getArrivalDateTime(),
                    ticket.getDepartureDateTime()) % 60;
            String duration = String.format("%d horas %d minutos", hours, minutes);

            ((Contract.ReceptionistView) view).showPaymentReceipt(
                    ticket.getVehicle().getPlate(),
                    ticket.getVehicle().getType(),
                    ticket.getArrivalDateTime().format(formatter),
                    ticket.getDepartureDateTime().format(formatter),
                    duration,
                    String.format("$%,.0f", ticket.getFee()),
                    String.format("$%,.0f", ticket.getAmountPaid()),
                    String.format("$%,.0f", ticket.getAmountPaid() - ticket.getFee()));
            updateAvailableSpaces();
        } else {
            view.showMessage("Error al procesar la salida. Verifique la placa y el monto pagado.");
        }
    }

    @Override
    public void updateAvailableSpaces() {
        if (view instanceof Contract.ReceptionistView) {
            ((Contract.ReceptionistView) view).updateAvailableSpaces(
                    model.getAvailableSpaces(),
                    false);
        }
    }

    @Override
    public void addParking(Parking parking) {
        model.addParking(parking);
        if (view instanceof Contract.AdminView) {
            ((Contract.AdminView) view).showParkingDetails(parking);
        }
    }

    @Override
    public void addReceptionist(User receptionist) {
        model.addReceptionist(receptionist);
        if (view instanceof Contract.AdminView) {
            ((Contract.AdminView) view).showReceptionistDetails(receptionist);
        }
    }

    @Override
    public boolean existsActiveTicket(String plate) {
        return model.existsActiveTicket(plate);
    }

    @Override
    public void openChangeCredentialsWindow() {
        FrameChangeCredentials frame = new FrameChangeCredentials();
        frame.setSaveButtonListener(e -> handleChangePassword(frame));
        frame.setBackButtonListener(e -> {
            frame.dispose();
            new AdminWindow().setVisible(true);
        });

    }

}
