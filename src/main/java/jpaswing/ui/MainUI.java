package jpaswing.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MainUI extends JFrame implements ActionListener {
    private JButton btnPiloto;
    private JButton btnEscuderia;
    private JButton btnCircuito;

    @Autowired
    private PilotoUI pilotoUI;

    @Autowired
    private EscuderiaUI escuderiaUI;

    @Autowired
    private CircuitoUI circuitoUI;

    public MainUI() {
        initComponents();
        initLayout();
    }

    private void initComponents() {
        btnPiloto = new JButton("Abrir lista pilotos");
        btnPiloto.addActionListener(this);
        btnEscuderia = new JButton("Abrir lista escuderias");
        btnEscuderia.addActionListener(this);
        btnCircuito = new JButton("Abrir lista circuitos");
        btnCircuito.addActionListener(this);
    }

    private void initLayout() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(btnPiloto);
        buttonPanel.add(btnEscuderia);
        buttonPanel.add(btnCircuito);

        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("F1.png"));

        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(200, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledIcon);
        imagePanel.add(imageLabel);

        setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setTitle("Menú");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPiloto) {
            pilotoUI.setVisible(true);
        } else if (e.getSource() == btnEscuderia) {
            escuderiaUI.setVisible(true);
        } else if (e.getSource() == btnCircuito) {
            circuitoUI.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI mainUI = new MainUI();
            mainUI.setVisible(true);
        });
    }
}