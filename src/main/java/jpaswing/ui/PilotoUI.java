package jpaswing.ui;

import javax.swing.*;
import javax.swing.event.*;

public class PilotoUI extends JPanel implements ListSelectionListener {
    private JTextField fieldNumero;
    private JTextField fieldNombre;
    private JTextField fieldNacimiento;
    private JTextField fieldEscuderia;
    private JTextField fieldNacionalidad;
    private JTextField fieldDebut;

    public PilotoUI() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel l;

        l = new JLabel("Número:");
        l.setBounds(10, 10, 70, 20);
        add(l);
        fieldNumero = new JTextField();
        fieldNumero.setBounds(10 + 80, 10, 200, 20);
        add(fieldNumero);

        l = new JLabel("Nombre:");
        l.setBounds(10, 40, 70, 20);
        add(l);
        fieldNombre = new JTextField();
        fieldNombre.setBounds(10 + 80, 40, 200, 20);
        add(fieldNombre);

        l = new JLabel("Nacimiento:");
        l.setBounds(10, 70, 70, 20);
        add(l);
        fieldNacimiento = new JTextField();
        fieldNacimiento.setBounds(10 + 80, 70, 200, 20);
        add(fieldNacimiento);

        l = new JLabel("Escudería:");
        l.setBounds(10, 100, 70, 20);
        add(l);
        fieldEscuderia = new JTextField();
        fieldEscuderia.setBounds(10 + 80, 100, 200, 20);
        add(fieldEscuderia);

        l = new JLabel("Nacionalidad:");
        l.setBounds(10, 130, 70, 20);
        add(l);
        fieldNacionalidad = new JTextField();
        fieldNacionalidad.setBounds(10 + 80, 130, 200, 20);
        add(fieldNacionalidad);

        l = new JLabel("Debut:");
        l.setBounds(10, 160, 70, 20);
        add(l);
        fieldDebut = new JTextField();
        fieldDebut.setBounds(10 + 80, 160, 200, 20);
        add(fieldDebut);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
