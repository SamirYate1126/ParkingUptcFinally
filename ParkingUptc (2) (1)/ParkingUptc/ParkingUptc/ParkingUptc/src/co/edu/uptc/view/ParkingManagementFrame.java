package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import co.edu.uptc.presenter.Contract;
import co.edu.uptc.presenter.Presenter;
import co.edu.uptc.model.*;

public class ParkingManagementFrame extends JFrame implements Contract.AdminView {
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtSpaces;
    private Contract.Presenter presenter;

    public ParkingManagementFrame() {
        setTitle("Registrar Parqueadero");
        configureWindow();
        initComponents();
        setupMVP();
    }

    private void configureWindow() {
        setSize(400, 300);
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

        
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        mainPanel.add(txtName, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        txtAddress = new JTextField(20);
        mainPanel.add(txtAddress, gbc);

        
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Espacios:"), gbc);
        gbc.gridx = 1;
        txtSpaces = new JTextField(20);
        mainPanel.add(txtSpaces, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton btnRegister = new JButton("Registrar Parqueadero");
        btnRegister.addActionListener(e -> registerParking());
        mainPanel.add(btnRegister, gbc);

        add(mainPanel);
    }

    private void setupMVP() {
        presenter = Presenter.getInstance();
        presenter.setView(this);
    }

    private void registerParking() {
        try {
            String name = txtName.getText().trim();
            String address = txtAddress.getText().trim();
            int spaces = Integer.parseInt(txtSpaces.getText().trim());

            if (name.isEmpty() || address.isEmpty()) {
                showMessage("Por favor complete todos los campos");
                return;
            }

            Schedule[] schedules = {new Schedule("Lunes-Viernes", "07:00", "19:00")};
            Parking parking = new Parking(name, address, spaces, schedules);
            presenter.addParking(parking);
            dispose();

        } catch (NumberFormatException e) {
            showMessage("El número de espacios debe ser un valor numérico");
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void showParkingDetails(Parking parking) {
        JOptionPane.showMessageDialog(this,
            String.format("Parqueadero registrado exitosamente:\n\n" +
                         "Nombre: %s\n" +
                         "Dirección: %s\n" +
                         "Espacios: %d",
                         parking.getName(),
                         parking.getAddress(),
                         parking.getTotalSpaces()));
    }

    @Override
    public void showReceptionistDetails(User receptionist) {
        
    }
}