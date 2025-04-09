package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import co.edu.uptc.presenter.Contract;
import co.edu.uptc.presenter.Presenter;
import co.edu.uptc.model.*;

public class AdminWindow extends JFrame implements Contract.AdminView {
    private Contract.Presenter presenter;

    public AdminWindow() {
        setTitle("Administrador - ParkingUPTC");
        configureWindow();
        initComponents();
        setupMVP();
    }

    private void configureWindow() {
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitle = new JLabel("PANEL DE ADMINISTRACIÓN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel central con botones
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Botón Registrar Parqueadero
        JButton btnRegisterParking = new JButton("Registrar Parqueadero");
        btnRegisterParking.addActionListener(e -> new ParkingManagementFrame().setVisible(true));
        centerPanel.add(btnRegisterParking, gbc);

        // Botón Crear Recepcionista
        JButton btnCreateReceptionist = new JButton("Crear Recepcionista");
        btnCreateReceptionist.addActionListener(e -> new ReceptionistManagementFrame().setVisible(true));
        centerPanel.add(btnCreateReceptionist, gbc);

        // Botón Cambiar Credenciales (En proceso)
        JButton btnChangeCredentials = new JButton("Cambiar Credenciales de Recepcionista");
        btnChangeCredentials.addActionListener(e -> presenter.openChangeCredentialsWindow());

        centerPanel.add(btnChangeCredentials, gbc);

        // Botón Generar Reporte (En proceso)
        JButton btnGenerateReport = new JButton("Generar Reporte de Ventas");
        btnGenerateReport.addActionListener(e -> {
            ReporteUI reporteFrame = new ReporteUI();
            reporteFrame.setVisible(true);
        });
        centerPanel.add(btnGenerateReport, gbc);


        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Botón Cerrar Sesión
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        bottomPanel.add(btnLogout);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setupMVP() {
        presenter = Presenter.getInstance();
        presenter.setView(this);
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
        JOptionPane.showMessageDialog(this,
            String.format("Recepcionista registrado exitosamente:\n\n" +
                         "Nombre: %s %s\n" +
                         "Documento: %s\n" +
                         "Email: %s\n" +
                         "Usuario: %s\n" +
                         "Contraseña: %s",
                         receptionist.getFirstName(),
                         receptionist.getLastName(),
                         receptionist.getDocumentId(),
                         receptionist.getEmail(),
                         receptionist.getUsername(),
                         receptionist.getPassword()));
    }
}