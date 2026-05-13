package Pruebas;

import Vista.BotonRedondeado;

import javax.swing.*;
import java.awt.*;



public class CustomFrame extends JFrame {

    private static final Color colorBase = Color.decode("#E89B6D");

    private static final Color hoverColor = new Color(
            Math.min(colorBase.getRed() + 30, 255),
            Math.min(colorBase.getGreen() + 30, 255),
            Math.min(colorBase.getBlue() + 30, 255)
    );

    public CustomFrame() {
        // Configuración del frame
        setTitle("Juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLayout(new BorderLayout(10, 10)); // Espaciado entre componentes

        // Botón de información
        BotonRedondeado infoButton = new BotonRedondeado("i", colorBase, hoverColor, Color.BLACK, 100);
        infoButton.setOpaque(false);
        infoButton.setFont(new Font("Arial", Font.BOLD, 20));
        infoButton.setPreferredSize(new Dimension(50, 50));
        infoButton.setFocusPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(infoButton, BorderLayout.EAST); // Ubicado en la esquina superior derecha
        infoPanel.setOpaque(false); // Fondo transparente

        // Título
        JLabel titleLabel = new JLabel("Título del Juego", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Botones principales
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] buttonLabels = {"Nuevo Juego", "Cargar Partida", "Opciones", "Salir"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 40)); // Tamaño uniforme
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado entre botones
        }

        // Agregar componentes al frame
        add(infoPanel, BorderLayout.NORTH);
        add(titleLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomFrame::new);
    }
}
