package jpaswing.ui;

import jpaswing.controller.PilotoController;
import jpaswing.entity.Piloto;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

@Component
public class PilotoUI extends JFrame implements ListSelectionListener {
    private JTextField fieldNumero;
    private JTextField fieldNombre;
    private JTextField fieldNacimiento;
    private JTextField fieldEscuderia;
    private JTextField fieldNacionalidad;
    private JTextField fieldDebut;
    private JPanel panel;
    private Piloto piloto;
    private JButton btnFirst;
    private JButton btnPrevious;
    private JButton btnNext;
    private JButton btnLast;
    private PilotoController pilotoController;
    private JList list;
    private JSplitPane SplitPanel;

    public SplitPanel() {


        list = new JList((ListModel) fieldNombre);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

    JScrollPane listScrollPane = new JScrollPane(list);
        SplitPanel = new JSplitPane();
        SplitPanel.setFont(SplitPanel.getFont().deriveFont(Font.ITALIC));
        SplitPanel.setAlignmentX(JLabel.CENTER);

    JScrollPane pictureScrollPane = new JScrollPane(SplitPanel);

        SplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, pictureScrollPane);
        SplitPanel.setOneTouchExpandable(true);
        SplitPanel.setDividerLocation(150);

        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        pictureScrollPane.setMinimumSize(minimumSize);

        SplitPanel.setPreferredSize(new Dimension(400, 200));

        public void valueChanged(ListSelectionEvent e) {
            JList list = (JList)e.getSource();
            updateLabel(fieldNombre);
        }

            /*private void next(){
        this.piloto = pilotoController.next().orElse(null);
        updateData();
    }
    private void previous(){
        this.piloto = pilotoController.previous().orElse(null);
        updateData();
    }
    private void last(){
        this.piloto = pilotoController.last().orElse(null);
        updateData();
    }
    private void first(){
        this.piloto = pilotoController.first().orElse(null);
        updateData();
    }

 public PilotoUI(PilotoRepository pilotoRepository, PilotoController pilotoController){
        this.pilotoController = pilotoController;
        setTitle("Pilotos Formula 1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        initComponents();
        piloto = pilotoRepository.findFirstByOrderByIdAsc();
        updateData();
    }

    private void updateData(){
        fieldNumero.setText(Long.toString(this.piloto.getNumero()));
        fieldNombre.setText(this.piloto.getNombre());
        fieldNacimiento.setText(this.piloto.getNacimiento());
        fieldNacionalidad.setText(this.piloto.getNacionalidad());
        fieldEscuderia.setText(this.piloto.getEscuderia());
        fieldDebut.setText(this.piloto.getDebut());

    }

    private void initComponents(){
        panel = new JPanel();
        fieldNumero = new JTextField(10);
        fieldNombre = new JTextField(10);
        fieldNacimiento = new JTextField(10);
        fieldEscuderia = new JTextField(10);
        fieldNacionalidad = new JTextField(10);
        fieldDebut = new JTextField(10);
        JLabel l;*/




        /*this.setLayout(null);
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 500, 650);

        l = new JLabel("Dorsal:");
        l.setBounds(10, 10, 70, 20);
        panel1.add(l);
        fieldNumero.setEnabled(false);
        fieldNumero.setBounds(10 + 80, 10, 200, 20);
        panel1.add(fieldNumero);

        l = new JLabel("Name:");
        l.setBounds(10, 40, 70, 20);
        panel1.add(l);
        fieldNombre.setBounds(10 + 80, 40, 200, 20 );
        panel1.add(fieldNombre);
        this.add(panel1);

        l = new JLabel("Nacimiento:");
        l.setBounds(10, 70, 70, 20);
        panel1.add(l);
        fieldNacimiento.setBounds(10 + 80, 70, 200, 20);
        panel1.add(fieldNacimiento);

        l = new JLabel("Escudería:");
        l.setBounds(10, 100, 70, 20);
        panel1.add(l);
        fieldEscuderia.setBounds(10 + 80, 100, 200, 20);
        panel1.add(fieldEscuderia);

        l = new JLabel("Nacionalidad:");
        l.setBounds(10, 130, 70, 20);
        panel1.add(l);
        fieldNacionalidad.setBounds(10 + 80, 130, 200, 20);
        panel1.add(fieldNacionalidad);

        l = new JLabel("Debut:");
        l.setBounds(10, 160, 70, 20);
        panel1.add(l);
        fieldDebut.setBounds(10 + 80, 160, 200, 20);
        panel1.add(fieldDebut);


        btnFirst = new JButton("<<");
        btnFirst.addActionListener(e -> first());
        btnPrevious = new JButton("<");
        btnPrevious.addActionListener(e -> previous());
        btnNext = new JButton(">");
        btnNext.addActionListener(e -> next());
        btnLast = new JButton(">>");
        btnLast.addActionListener(e -> last());
        btnFirst.setBounds(20, 260, 60,40);
        panel1.add(btnFirst);

        btnPrevious.setBounds(100, 260, 60,40);
        panel1.add(btnPrevious);

        btnNext.setBounds(180, 260, 60,40);
        panel1.add(btnNext);

        btnLast.setBounds(260, 260, 60,40);
        panel1.add(btnLast);*/
    }

}
