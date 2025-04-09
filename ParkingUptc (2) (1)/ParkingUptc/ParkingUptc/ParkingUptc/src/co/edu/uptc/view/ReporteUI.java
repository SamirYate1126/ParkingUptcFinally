package co.edu.uptc.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReporteUI extends JFrame {

    public ReporteUI() {
        setTitle("ParkingUPTC - Generar Reporte");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton volverBtn = new JButton("← Volver al Menú");
        topPanel.add(volverBtn);

        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fechaPanel.add(new JLabel("Digite la fecha para la generación del reporte:"));
        JTextField fechaField = new JTextField(8);
        fechaField.setText("24/04/24");
        fechaPanel.add(fechaField);
        JButton calendarBtn = new JButton("📅");
        fechaPanel.add(calendarBtn);
        JButton generarBtn = new JButton("Generar");
        fechaPanel.add(generarBtn);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(fechaPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        JLabel parqueaderoLabel = new JLabel("Parqueadero");
        DefaultTableModel parqueaderoModel = new DefaultTableModel(
                new Object[][] {{"24/04/24", "700,000", "80"},
                {"24/04/24", "600,000", "70"},
                {"24/04/24", "500,000", "60"},
                {"", "", ""},
                {"", "", ""}},
                new String[] {"Fecha", "Total de Ingresos", "Total de Vehículos Ingresados"}
        );
        JTable parqueaderoTable = new JTable(parqueaderoModel);
        parqueaderoTable.setRowHeight(30);
        JScrollPane parqueaderoScroll = new JScrollPane(parqueaderoTable);

        JLabel recepcionistasLabel = new JLabel("Recepcionistas");
        DefaultTableModel recepcionistasModel = new DefaultTableModel(
                new Object[][] {
                        {"24/04/24", "José Vargas", "700,000", "80"},
                        {"24/04/24", "Ana Díaz", "600,000", "70"}
                },
                new String[] {"Fecha", "Nombres y Apellidos", "Total de Ingresos", "Total de Vehículos Ingresados"}
        );
        JTable recepcionistasTable = new JTable(recepcionistasModel);
        recepcionistasTable.setRowHeight(30);
        JScrollPane recepcionistasScroll = new JScrollPane(recepcionistasTable);

        tablesPanel.add(parqueaderoLabel);
        tablesPanel.add(parqueaderoScroll);
        tablesPanel.add(Box.createVerticalStrut(10));
        tablesPanel.add(recepcionistasLabel);
        tablesPanel.add(recepcionistasScroll);

        mainPanel.add(tablesPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReporteUI reporte = new ReporteUI();
            reporte.setVisible(true);
        });
    }
}
