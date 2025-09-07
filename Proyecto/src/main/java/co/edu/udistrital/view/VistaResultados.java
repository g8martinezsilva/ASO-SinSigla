/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author Gabriela
 */
public class VistaResultados extends JFrame {
    private JTextArea areaInfo;
    private JButton btnReiniciar;

    public VistaResultados(ActionListener reiniciarListener) {
        setTitle("ASO - Resultados de ordenamiento");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        areaInfo = new JTextArea();
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaInfo);
        add(scroll, BorderLayout.CENTER);
        
        // Botón reiniciar abajo
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(reiniciarListener);
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnReiniciar);
        add(panelBoton, BorderLayout.SOUTH);
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void mostrarJson(String json) {
        areaInfo.setText(json);
        areaInfo.setCaretPosition(0);
    }
}