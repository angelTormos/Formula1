package jpaswing.ui;

import jpaswing.entity.Piloto;
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
public class PilotoUI extends JFrame implements ListSelectionListener {
    private JLabel labelImagen;
    private JTextField fieldNumero;
    private JTextField fieldNombre;
    private JTextField fieldNacimiento;
    private JTextField fieldEscuderia;
    private JTextField fieldNacionalidad;
    private JTextField fieldDebut;
    private JPanel detailPanel;
    private JPanel imagePanel;
    private JList<Piloto> list;
    private JSplitPane splitPanel;
    private DefaultListModel<Piloto> listModel;
    private JButton btnBackToMain;

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/formula1";

    public PilotoUI() {
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
        fieldNumero = new JTextField(20);
        fieldNombre = new JTextField(20);
        fieldNacimiento = new JTextField(20);
        fieldEscuderia = new JTextField(20);
        fieldNacionalidad = new JTextField(20);
        fieldDebut = new JTextField(20);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        detailPanel = new JPanel();
        detailPanel.setLayout(new GridLayout(6, 2));
        detailPanel.add(new JLabel("Dorsal"));
        detailPanel.add(fieldNumero);
        detailPanel.add(new JLabel("Nombre"));
        detailPanel.add(fieldNombre);
        detailPanel.add(new JLabel("Nacimiento"));
        detailPanel.add(fieldNacimiento);
        detailPanel.add(new JLabel("Escuderia"));
        detailPanel.add(fieldEscuderia);
        detailPanel.add(new JLabel("Nacionalidad"));
        detailPanel.add(fieldNacionalidad);
        detailPanel.add(new JLabel("Debut"));
        detailPanel.add(fieldDebut);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(labelImagen, BorderLayout.CENTER);

        btnBackToMain = new JButton("Volver al menu principal");
        btnBackToMain.addActionListener(e -> volverAMainUI());
    }

    private void initLayout() {
        JPanel listPanel = new JPanel(new BorderLayout());
        JScrollPane listScrollPane = new JScrollPane(list);
        listPanel.add(listScrollPane, BorderLayout.CENTER);

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnBackToMain);
        add(bottomPanel, BorderLayout.SOUTH);

        add(splitPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }


    private void cargarDatos() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql = "SELECT imagen, numero, nombre, nacimiento, escuderia, nacionalidad, debut FROM Piloto";
            rs = stmt.executeQuery(sql);

            listModel.clear();
            while (rs.next()) {
                Piloto piloto = new Piloto();
                piloto.setImagen(rs.getString("imagen"));
                piloto.setNumero(rs.getInt("numero"));
                piloto.setNombre(rs.getString("nombre"));
                piloto.setNacimiento(rs.getString("nacimiento"));
                piloto.setEscuderia(rs.getString("escuderia"));
                piloto.setNacionalidad(rs.getString("nacionalidad"));
                piloto.setDebut(rs.getString("debut"));

                listModel.addElement(piloto);
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
        JList<Piloto> list = (JList<Piloto>) e.getSource();
        Piloto selectedPiloto = list.getSelectedValue();
        if (selectedPiloto != null) {
            updateFields(selectedPiloto);
        }
    }

    private void updateFields(Piloto piloto) {
        try {
            URL url = new URL(piloto.getImagen());
            ImageIcon imageIcon = new ImageIcon(url);
            labelImagen.setIcon(imageIcon);
        } catch (Exception e) {
            labelImagen.setIcon(null);
            e.printStackTrace();
        }

        fieldNumero.setText(String.valueOf(piloto.getNumero()));
        fieldNombre.setText(piloto.getNombre());
        fieldNacimiento.setText(piloto.getNacimiento());
        fieldEscuderia.setText(piloto.getEscuderia());
        fieldNacionalidad.setText(piloto.getNacionalidad());
        fieldDebut.setText(piloto.getDebut());
    }

    private void volverAMainUI() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PilotoUI ui = new PilotoUI();
            ui.setVisible(true);
        });
    }
}
