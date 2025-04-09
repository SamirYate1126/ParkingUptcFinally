package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import co.edu.uptc.presenter.Contract;
import co.edu.uptc.presenter.Presenter;

public class VehicleEntryFrame extends JFrame implements Contract.ReceptionistView {
    private JTextField txtPlate;
    private JComboBox<String> cmbVehicleType;
    private Contract.Presenter presenter;

    public VehicleEntryFrame() {
        setTitle("Registrar Entrada de Vehículo");
        configureWindow();
        initComponents();
        setupMVP();
    }

    private void configureWindow() {
        setSize(400, 250);
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

        // Tipo de vehículo
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        cmbVehicleType = new JComboBox<>(new String[]{"Carro", "Moto"});
        mainPanel.add(cmbVehicleType, gbc);

        // Botón registrar
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnRegister = new JButton("Registrar Entrada");
        btnRegister.addActionListener(e -> registerEntry());
        mainPanel.add(btnRegister, gbc);

        add(mainPanel);
    }

    private void setupMVP() {
        presenter = Presenter.getInstance();
        presenter.setView(this);
    }

    private void registerEntry() {
        String plate = txtPlate.getText().trim().toUpperCase();
        if (plate.isEmpty()) {
            showMessage("Por favor ingrese la placa del vehículo");
            return;
        }

        String type = (String) cmbVehicleType.getSelectedItem();
        presenter.registerVehicleEntry(plate, type);
        dispose();
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
        String ticket = String.format(
            "TICKET DE ENTRADA\n\n" +
            "Placa: %s\n" +
            "Tipo: %s\n" +
            "Fecha/Hora: %s\n" +
            "Parqueadero: %s",
            plate, type, dateTime, parkingName
        );
        JOptionPane.showMessageDialog(this, ticket, "Ticket Generado", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showPaymentReceipt(String plate, String type, String entryTime, 
                                 String exitTime, String duration, String fee, 
                                 String amountPaid, String change) {
       
    }
}