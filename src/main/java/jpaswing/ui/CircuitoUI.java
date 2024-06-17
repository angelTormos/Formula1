package jpaswing.ui;

import jpaswing.entity.Circuito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
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
public class CircuitoUI extends JFrame implements ListSelectionListener {
    private JLabel labelImagen;
    private JTextField fieldNombre;
    private JTextField fieldFirstRace;
    private JTextField fieldVueltaRapida;
    private JTextField fieldLocalizacion;
    private JTextField fieldDistancia;
    private JTextField fieldVueltas;
    private JPanel detailPanel;
    private JPanel imagePanel;
    private JList<Circuito> list;
    private JSplitPane splitPanel;
    private DefaultListModel<Circuito> listModel;
    private JButton btnBackToMain;
    private JButton btnSaveToFile;
    private JButton btnPiloto;
    private JButton btnEscuderia;
    private JTextField searchField;
    private List<Circuito> circuitos;

    @Autowired
    @Lazy
    private PilotoUI pilotoUI;
    @Autowired
    @Lazy
    private EscuderiaUI escuderiaUI;

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/formula1";

    public CircuitoUI() {
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
        fieldFirstRace = new JTextField(20);
        fieldVueltaRapida = new JTextField(20);
        fieldLocalizacion = new JTextField(20);
        fieldDistancia = new JTextField(20);
        fieldVueltas = new JTextField(20);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        detailPanel = new JPanel();
        detailPanel.setLayout(new GridLayout(6, 2));
        detailPanel.add(new JLabel("Nombre"));
        detailPanel.add(fieldNombre);
        detailPanel.add(new JLabel("Primera Carrera"));
        detailPanel.add(fieldFirstRace);
        detailPanel.add(new JLabel("Vuelta Rapida"));
        detailPanel.add(fieldVueltaRapida);
        detailPanel.add(new JLabel("Localización"));
        detailPanel.add(fieldLocalizacion);
        detailPanel.add(new JLabel("Distancia"));
        detailPanel.add(fieldDistancia);
        detailPanel.add(new JLabel("Nº Vueltas"));
        detailPanel.add(fieldVueltas);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(labelImagen, BorderLayout.CENTER);

        btnBackToMain = new JButton("Menu principal");
        btnBackToMain.addActionListener(e -> volverAMainUI());

        btnSaveToFile = new JButton("Descargar informacion");
        btnSaveToFile.addActionListener(e -> saveToFile());

        btnPiloto = new JButton("Pilotos");
        btnPiloto.addActionListener(e -> irPiloto());

        btnEscuderia = new JButton("Escuderias");
        btnEscuderia.addActionListener(e -> irEscuderia());

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
        imagePanel.setMinimumSize(new Dimension(400, 224));

        splitPanel.setPreferredSize(new Dimension(800, 600));

        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        upperPanel.add(btnBackToMain);
        upperPanel.add(btnPiloto);
        upperPanel.add(btnEscuderia);
        add(upperPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(new JLabel("Buscar:"));
        bottomPanel.add(searchField);
        bottomPanel.add(btnSaveToFile);
        add(bottomPanel, BorderLayout.SOUTH);

        add(splitPanel, BorderLayout.CENTER);

        setTitle("Circuitos");
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
            String sql = "SELECT imagen, nombre, primera, vuelta, localizacion, distancia, vueltas FROM Circuito";
            rs = stmt.executeQuery(sql);

            listModel.clear();
            circuitos = new ArrayList<>();
            while (rs.next()) {
                Circuito circuito = new Circuito();
                circuito.setImagen(rs.getString("imagen"));
                circuito.setNombre(rs.getString("nombre"));
                circuito.setFirstRace(rs.getString("primera"));
                circuito.setVueltaRapida(rs.getString("vuelta"));
                circuito.setLocalizacion(rs.getString("localizacion"));
                circuito.setDistancia(rs.getString("distancia"));
                circuito.setVueltas(rs.getInt("vueltas"));

                circuitos.add(circuito);
                listModel.addElement(circuito);
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

        for (Circuito circuito : circuitos) {
            if (circuito.getNombre().toLowerCase().contains(filter) ||
                    circuito.getLocalizacion().toLowerCase().contains(filter)) {
                listModel.addElement(circuito);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList<Circuito> list = (JList<Circuito>) e.getSource();
        Circuito selectedCircuito = list.getSelectedValue();
        if (selectedCircuito != null) {
            updateFields(selectedCircuito);
        }
    }

    private void updateFields(Circuito circuito) {
        try {
            URL url = new URL(circuito.getImagen());
            BufferedImage image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(500, 281, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            labelImagen.setIcon(imageIcon);
        } catch (Exception e) {
            labelImagen.setIcon(null);
            e.printStackTrace();
        }

        fieldNombre.setText(circuito.getNombre());
        fieldFirstRace.setText(circuito.getFirstRace());
        fieldVueltaRapida.setText(circuito.getVueltaRapida());
        fieldLocalizacion.setText(circuito.getLocalizacion());
        fieldDistancia.setText(circuito.getDistancia());
        fieldVueltas.setText(String.valueOf(circuito.getVueltas()));
    }

    private void volverAMainUI() {
        dispose();
    }
    private void irPiloto(){
        pilotoUI.setVisible(true);
        dispose();
    }
    private void irEscuderia(){
        escuderiaUI.setVisible(true);
        dispose();
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar información de circuitos");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".html")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".html");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write("<html><body>");
                writer.write("<h1>Información de Circuitos</h1>");
                writer.write("<table border='1'>");
                writer.write("<tr>");
                writer.write("<th>Nombre</th>");
                writer.write("<th>Primera carrera</th>");
                writer.write("<th>Vuelta Rapida</th>");
                writer.write("<th>Localización</th>");
                writer.write("<th>Distancia</th>");
                writer.write("<th>Nº Vueltas</th>");
                writer.write("<th>Imagen</th>");
                writer.write("</tr>");
                for (int i = 0; i < listModel.size(); i++) {
                    Circuito circuito = listModel.getElementAt(i);
                    writer.write("<tr>");
                    writer.write("<td>" + circuito.getNombre() + "</td>");
                    writer.write("<td>" + circuito.getFirstRace() + "</td>");
                    writer.write("<td>" + circuito.getVueltaRapida() + "</td>");
                    writer.write("<td>" + circuito.getLocalizacion() + "</td>");
                    writer.write("<td>" + circuito.getDistancia() + "</td>");
                    writer.write("<td>" + circuito.getVueltas() + "</td>");
                    writer.write("<td><img src='" + circuito.getImagen() + "' width='100'></td>");
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
            CircuitoUI ui = new CircuitoUI();
            ui.setVisible(true);
        });
    }
}
