package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import co.edu.uptc.presenter.Contract;
import co.edu.uptc.presenter.Presenter;

public class ReceptionistWindow extends JFrame implements Contract.ReceptionistView {
    private JLabel lblAvailableSpaces;
    private Contract.Presenter presenter;
    private Timer updateTimer;

    public ReceptionistWindow(String firstName) {
        setTitle("Recepcionista - " + firstName);
        configureWindow();
        initComponents();
        setupMVP();
        setupTimer();
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

        // Panel superior con espacios disponibles
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblAvailableSpaces = new JLabel("Espacios disponibles: --");
        lblAvailableSpaces.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(lblAvailableSpaces);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel central con botones
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JButton btnEntry = new JButton("Registrar Entrada");
        btnEntry.addActionListener(e -> new VehicleEntryFrame().setVisible(true));
        centerPanel.add(btnEntry, gbc);

        JButton btnExit = new JButton("Registrar Salida");
        btnExit.addActionListener(e -> new VehicleExitFrame().setVisible(true));
        centerPanel.add(btnExit, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botón de cierre de sesión
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.addActionListener(e -> {
            if (updateTimer != null) {
                updateTimer.stop();
            }
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

    private void setupTimer() {
        updateTimer = new Timer(30000, e -> presenter.updateAvailableSpaces());
        updateTimer.start();
        presenter.updateAvailableSpaces();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (updateTimer != null) {
                    updateTimer.stop();
                }
            }
        });
    }

    @Override
    public void updateAvailableSpaces(int spaces, boolean warn) {
        lblAvailableSpaces.setText("Espacios disponibles: " + spaces);
        lblAvailableSpaces.setForeground(spaces < 5 ? Color.RED : Color.BLACK);
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void showTicket(String plate, String type, String dateTime, String parkingName) {
        
    }

    @Override
    public void showPaymentReceipt(String plate, String type, String entryTime, 
                                 String exitTime, String duration, String fee, 
                                 String amountPaid, String change) {
        
    }
}