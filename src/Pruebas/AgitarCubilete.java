package Pruebas;
import Vista.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AgitarCubilete {
    private JFrame ventana;
    private ImagePanel panel;
    private JButton cubilete;
    private Timer timer;
    private Random random;

    public AgitarCubilete() {
        ventana = new JFrame("Animación de Cubilete");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 400);

        panel = new ImagePanel("src/Images/Fondo_Dados.jpg"); // Layout nulo para controlar las posiciones

        random = new Random();

        // Botón que actúa como cubilete
        cubilete = new JButton();
        ImageIcon imagen = new ImageIcon("src/Images/Cubilete.jpeg");
        Image resize = imagen.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        cubilete.setIcon(new ImageIcon(resize));
        cubilete.setFont(new Font("Arial", Font.BOLD, 20));
        cubilete.setBounds(250, 150, 120, 120); // Posición inicial
        cubilete.setFocusPainted(false);
        panel.add(cubilete);

        // Botón para iniciar la animación
        JButton botonAgitar = new JButton("Agitar");
        botonAgitar.setBounds(250, 300, 120, 40);
        panel.add(botonAgitar);

        ventana.add(panel);
        ventana.setVisible(true);

        // Acción para iniciar la animación
        botonAgitar.addActionListener(e -> agitarCubilete());
    }

    private void agitarCubilete() {
        // Reinicia posición original
        int startX = 250;
        int startY = 150;

        // Timer para mover el cubilete
        timer = new Timer(50, new ActionListener() {
            int contador = 0;
            int maxMovimientos = 20; // Número total de movimientos

            @Override
            public void actionPerformed(ActionEvent e) {
                // Mueve el cubilete de forma aleatoria
                int offsetX = random.nextInt(10) - 5; // Movimiento horizontal (-5 a 5)
                int offsetY = random.nextInt(10) - 5; // Movimiento vertical (-5 a 5)
                cubilete.setBounds(startX + offsetX, startY + offsetY, 120, 120);

                contador++;
                if (contador >= maxMovimientos) {
                    timer.stop(); // Detiene la animación
                    cubilete.setBounds(startX, startY, 120, 120); // Reestablece posición
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgitarCubilete::new);
    }
}

