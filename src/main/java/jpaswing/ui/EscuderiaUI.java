package jpaswing.ui;

import jpaswing.entity.Escuderia;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class EscuderiaUI extends JFrame implements ListSelectionListener {
    private JLabel labelImagen;
    private JTextField fieldNombre;
    private JTextField fieldMotor;
    private JTextField fieldChasis;
    private JTextField fieldNacionalidad;
    private JPanel detailPanel;
    private JPanel imagePanel;
    private JButton btnFirst;
    private JButton btnPrevious;
    private JButton btnNext;
    private JButton btnLast;
    private JList<Piloto> list;
    private JSplitPane splitPanel;
    private DefaultListModel<Piloto> listModel;

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/formula1";

    public EscuderiaUI() {
        initComponents();
        initLayout();
        try {
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        labelImagen = new JLabel();
        fieldNombre = new JTextField(20);
        fieldMotor = new JTextField(20);
        fieldChasis = new JTextField(20);
        fieldNacionalidad = new JTextField(20);


        btnFirst = new JButton("First");
        btnPrevious = new JButton("Previous");
        btnNext = new JButton("Next");
        btnLast = new JButton("Last");

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        detailPanel = new JPanel();
        detailPanel.setLayout(new GridLayout(6, 2));

        detailPanel.add(new JLabel("Nombre"));
        detailPanel.add(fieldNombre);
        detailPanel.add(new JLabel("Nacimiento"));
        detailPanel.add(fieldMotor);
        detailPanel.add(new JLabel("Escuderia"));
        detailPanel.add(fieldChasis);
        detailPanel.add(new JLabel("Nacionalidad"));
        detailPanel.add(fieldNacionalidad);


        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(labelImagen, BorderLayout.CENTER);
    }

    private void initLayout() {
        JPanel listPanel = new JPanel(new BorderLayout());
        JScrollPane listScrollPane = new JScrollPane(list);
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane rightSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, detailPanel, imagePanel);
        rightSplitPanel.setOneTouchExpandable(true);
        rightSplitPanel.setDividerLocation(200);

        splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, rightSplitPanel);
        splitPanel.setOneTouchExpandable(true);
        splitPanel.setDividerLocation(200);

        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        detailPanel.setMinimumSize(minimumSize);
        imagePanel.setMinimumSize(minimumSize);

        splitPanel.setPreferredSize(new Dimension(800, 600));

        setLayout(new BorderLayout());
        add(splitPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnFirst);
        buttonPanel.add(btnPrevious);
        buttonPanel.add(btnNext);
        buttonPanel.add(btnLast);
        return buttonPanel;
    }

    private void cargarDatos() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql = "SELECT imagen, nombre, chasis, motor, nacionalidad FROM Escuderia";
            rs = stmt.executeQuery(sql);

            listModel.clear();
            while (rs.next()) {
                Escuderia escuderia = new Escuderia();
                escuderia.setImagen(rs.getString("imagen"));
                escuderia.setNombre(rs.getString("nombre"));
                escuderia.setChasis(rs.getString("chasis"));
                escuderia.setMotor(rs.getString("motor"));
                escuderia.setNacionalidad(rs.getString("nacionalidad"));

                listModel.addElement(escuderia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList<Escuderia> list = (JList<Escuderia>) e.getSource();
        Escuderia selectedEscuderia = list.getSelectedValue();
        if (selectedEscuderia != null) {
            updateFields(selectedEscuderia);
        }
    }

    private void updateFields(Escuderia escuderia) {
        try {
            URL url = new URL(escuderia.getImagen());
            ImageIcon originalIcon = new ImageIcon(url);

            Image originalImage = originalIcon.getImage();
            int width = originalImage.getWidth(null);
            int height = originalImage.getHeight(null);
            int newWidth = 350;
            int newHeight = (int) (((double) newWidth / width) * height);
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            labelImagen.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            labelImagen.setIcon(null);
            e.printStackTrace();
        }

        fieldNombre.setText(escuderia.getNombre());
        fieldMotor.setText(escuderia.getNacimiento());
        fieldChasis.setText(escuderia.getEscuderia());
        fieldNacionalidad.setText(escuderia.getNacionalidad());
    }
    private void volverAMainUI() {
        MainUI mainUI = new MainUI();
        mainUI.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PilotoUI ui = new PilotoUI();
            ui.setVisible(true);
        });
    }
}