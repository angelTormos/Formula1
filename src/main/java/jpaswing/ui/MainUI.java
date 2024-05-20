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

    @Autowired
    private PilotoUI pilotoUI;

    @Autowired
    private EscuderiaUI escuderiaUI;

    public MainUI() {
        initComponents();
        initLayout();
    }

    private void initComponents() {
        btnPiloto = new JButton("Abrir lista pilotos");
        btnPiloto.addActionListener(this);
        btnEscuderia = new JButton("Abrir abrir lista escuderias");
        btnEscuderia.addActionListener(this);
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(btnPiloto);
        panel.add(btnEscuderia);

        add(panel);
        setTitle("Main UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPiloto) {
            pilotoUI.setVisible(true);
        } else if (e.getSource() == btnEscuderia) {
            escuderiaUI.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI mainUI = new MainUI();
            mainUI.setVisible(true);
        });
    }
}
