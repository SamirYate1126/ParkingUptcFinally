package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import co.edu.uptc.presenter.Contract;
import co.edu.uptc.presenter.Presenter;
import co.edu.uptc.model.Parking;
import co.edu.uptc.model.User;

public class ReceptionistManagementFrame extends JFrame implements Contract.AdminView {
    private JTextField txtDocumentId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private Contract.Presenter presenter;

    public ReceptionistManagementFrame() {
        setTitle("Registrar Recepcionista");
        configureWindow();
        initComponents();
        setupMVP();
    }

    private void configureWindow() {
        setSize(400, 350);
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

       
        addField(mainPanel, gbc, "Documento:", txtDocumentId = new JTextField(20), 0);

        
        addField(mainPanel, gbc, "Nombres:", txtFirstName = new JTextField(20), 1);

        
        addField(mainPanel, gbc, "Apellidos:", txtLastName = new JTextField(20), 2);


        addField(mainPanel, gbc, "Teléfono:", txtPhone = new JTextField(20), 3);

        addField(mainPanel, gbc, "Email:", txtEmail = new JTextField(20), 4);

        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton btnRegister = new JButton("Registrar Recepcionista");
        btnRegister.addActionListener(e -> registerReceptionist());
        mainPanel.add(btnRegister, gbc);

        add(mainPanel);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, 
                         JTextField field, int y) {
        gbc.gridx = 0; gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void setupMVP() {
        presenter = Presenter.getInstance();
        presenter.setView(this);
    }

    private void registerReceptionist() {
        String documentId = txtDocumentId.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        if (documentId.isEmpty() || firstName.isEmpty() || 
            lastName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showMessage("Por favor complete todos los campos");
            return;
        }

        User receptionist = new User(
            documentId, firstName, lastName, "", "", "receptionist",
            phone, "UPTC", email, documentId
        );

        presenter.addReceptionist(receptionist);
        dispose();
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void showParkingDetails(Parking parking) {
      
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
