package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import co.edu.uptc.presenter.Contract;
import co.edu.uptc.presenter.Presenter;

public class VehicleExitFrame extends JFrame implements Contract.ReceptionistView {
    private JTextField txtPlate;
    private JTextField txtAmountPaid;
    private Contract.Presenter presenter;

    public VehicleExitFrame() {
        setTitle("Registrar Salida de Vehículo");
        configureWindow();
        initComponents();
        setupMVP();
    }

    private void configureWindow() {
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Placa
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Placa:"), gbc);
        gbc.gridx = 1;
        txtPlate = new JTextField(15);
        mainPanel.add(txtPlate, gbc);

        // Monto pagado
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Monto Pagado:"), gbc);
        gbc.gridx = 1;
        txtAmountPaid = new JTextField(15);
        mainPanel.add(txtAmountPaid, gbc);

        // Botón procesar salida
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnProcess = new JButton("Procesar Salida");
        btnProcess.addActionListener(e -> processExit());
        mainPanel.add(btnProcess, gbc);

        add(mainPanel);
    }

    private void setupMVP() {
        presenter = Presenter.getInstance();
        presenter.setView(this);
    }

    private void processExit() {
        String plate = txtPlate.getText().trim().toUpperCase();
        if (plate.isEmpty()) {
            showMessage("Por favor ingrese la placa del vehículo");
            return;
        }

        if (!presenter.existsActiveTicket(plate)) {
            showMessage("La placa ingresada no se encuentra registrada");
            return;
        }

        try {
            double amountPaid = Double.parseDouble(txtAmountPaid.getText().trim());
            if (amountPaid < 3000) {
                showMessage("Monto no válido. El pago mínimo es $3,000");
                return;
            }
            presenter.processVehicleExit(plate, amountPaid);
            dispose();
        } catch (NumberFormatException e) {
            showMessage("Por favor ingrese un monto válido");
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void updateAvailableSpaces(int spaces, boolean warn) {
    }

    @Override
    public void showTicket(String plate, String type, String dateTime, String parkingName) {
        
    }

    @Override
    public void showPaymentReceipt(String plate, String type, String entryTime, 
                                 String exitTime, String duration, String fee, 
                                 String amountPaid, String change) {
        String receipt = String.format(
            "RECIBO DE PAGO\n\n" +
            "Placa: %s\n" +
            "Tipo: %s\n" +
            "Hora de entrada: %s\n" +
            "Hora de salida: %s\n" +
            "Tiempo total: %s\n" +
            "Tarifa: %s\n" +
            "Monto pagado: %s\n" +
            "Cambio: %s",
            plate, type, entryTime, exitTime, duration, fee, amountPaid, change
        );
        JOptionPane.showMessageDialog(this, receipt, "Recibo de Pago", JOptionPane.INFORMATION_MESSAGE);
    }
}
