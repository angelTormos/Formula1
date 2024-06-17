package jpaswing.ui;

import jpaswing.entity.Piloto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    private JButton btnMain;
    private JButton btnSaveToFile;
    private JButton btnEscuderia;
    private JButton btnCircuito;
    private JTextField searchField;
    private List<Piloto> pilotos;

    @Autowired
    @Lazy
    private EscuderiaUI escuderiaUI;
    @Autowired
    @Lazy
    private CircuitoUI circuitoUI;

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

        btnMain = new JButton("Menu principal");
        btnMain.addActionListener(e -> volverAMainUI());

        btnSaveToFile = new JButton("Descargar informacion");
        btnSaveToFile.addActionListener(e -> saveToFile());

        btnEscuderia = new JButton("Escuderias");
        btnEscuderia.addActionListener(e -> irEscuderiaUI());

        btnCircuito = new JButton("Circuitos");
        btnCircuito.addActionListener(e -> irCircuitoUI());

        searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }
            public void removeUpdate(DocumentEvent e) {
                filterList();
            }
            public void changedUpdate(DocumentEvent e) {
                filterList();
            }
        });
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

        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        upperPanel.add(btnMain);
        upperPanel.add(btnEscuderia);
        upperPanel.add(btnCircuito);
        add(upperPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(new JLabel("Buscar:"));
        bottomPanel.add(searchField);
        bottomPanel.add(btnSaveToFile);
        add(bottomPanel, BorderLayout.SOUTH);

        add(splitPanel, BorderLayout.CENTER);

        setTitle("Pilotos");
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
            pilotos = new ArrayList<>(); // Inicializar la lista de pilotos
            while (rs.next()) {
                Piloto piloto = new Piloto();
                piloto.setImagen(rs.getString("imagen"));
                piloto.setNumero(rs.getInt("numero"));
                piloto.setNombre(rs.getString("nombre"));
                piloto.setNacimiento(rs.getString("nacimiento"));
                piloto.setEscuderia(rs.getString("escuderia"));
                piloto.setNacionalidad(rs.getString("nacionalidad"));
                piloto.setDebut(rs.getString("debut"));

                pilotos.add(piloto);
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

    private void filterList() {
        String filter = searchField.getText().trim().toLowerCase();
        listModel.clear();

        for (Piloto piloto : pilotos) {
            if (piloto.getNombre().toLowerCase().contains(filter) ||
                    piloto.getEscuderia().toLowerCase().contains(filter) ||
                    piloto.getNacionalidad().toLowerCase().contains(filter)) {
                listModel.addElement(piloto);
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
    private void irEscuderiaUI(){
        escuderiaUI.setVisible(true);
        dispose();
    }
    private void irCircuitoUI(){
        circuitoUI.setVisible(true);
        dispose();
    }
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar información de pilotos");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".html")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".html");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write("<html><body>");
                writer.write("<h1>Información de Pilotos</h1>");
                writer.write("<table border='1'>");
                writer.write("<tr>");
                writer.write("<th>Dorsal</th>");
                writer.write("<th>Nombre</th>");
                writer.write("<th>Nacimiento</th>");
                writer.write("<th>Escuderia</th>");
                writer.write("<th>Nacionalidad</th>");
                writer.write("<th>Debut</th>");
                writer.write("<th>Imagen</th>");
                writer.write("</tr>");
                for (int i = 0; i < listModel.size(); i++) {
                    Piloto piloto = listModel.getElementAt(i);
                    writer.write("<tr>");
                    writer.write("<td>" + piloto.getNumero() + "</td>");
                    writer.write("<td>" + piloto.getNombre() + "</td>");
                    writer.write("<td>" + piloto.getNacimiento() + "</td>");
                    writer.write("<td>" + piloto.getEscuderia() + "</td>");
                    writer.write("<td>" + piloto.getNacionalidad() + "</td>");
                    writer.write("<td>" + piloto.getDebut() + "</td>");
                    writer.write("<td><img src='" + piloto.getImagen() + "' width='100'></td>");
                    writer.write("</tr>");
                }
                writer.write("</table>");
                writer.write("</body></html>");
                JOptionPane.showMessageDialog(this, "Información guardada en " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PilotoUI ui = new PilotoUI();
            ui.setVisible(true);
        });
    }
}
