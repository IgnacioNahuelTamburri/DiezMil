package Pruebas;
import Vista.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class PruebaBorder {
    public static void main(String[] args) {
        // Crear el frame principal
        JFrame frame = new JFrame("Ejemplo de Panel Superior");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Crear el panel principal con BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(120, 190, 80)); // Color verde similar

        // Panel izquierdo para la imagen y el texto
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(120, 190, 80));
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Agregar la imagen (ícono)
        JButton profileIcon = new ImageButton("src/Images/User.png", 50, 50);
        leftPanel.add(profileIcon);

        // Agregar el texto
        RoundedPanel roundedPanel = new RoundedPanel(new Color(96, 152, 64));
        roundedPanel.setLayout(new BorderLayout());
        JLabel userInfo = new JLabel("Juan");
        userInfo.setFont(new Font("Arial", Font.BOLD, 16));
        userInfo.setForeground(Color.WHITE);
        userInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        roundedPanel.add(userInfo);
        leftPanel.add(roundedPanel);

        // Panel derecho para los botones
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(120, 190, 80));
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Botón de configuraciones
        JButton settingsButton = new ImageButton("src/Images/Config.png", 50, 50);
        rightPanel.add(settingsButton);

        // Agregar los paneles izquierdo y derecho al panel principal
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Agregar el panel principal al frame
        frame.add(topPanel, BorderLayout.NORTH);

        // Panel contenedor del Panel Inferior
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(Color.ORANGE);
        panelContenedor.setLayout(new BorderLayout());
        JPanel panelInfIzq = new JPanel();
        panelInfIzq.setOpaque(false);
        panelInfIzq.setPreferredSize(new Dimension(75, 0));
        panelContenedor.add(panelInfIzq, BorderLayout.WEST);
        JPanel panelInfDer = new JPanel();
        panelInfDer.setOpaque(false);
        panelInfDer.setPreferredSize(new Dimension(75, 0));
        panelContenedor.add(panelInfDer, BorderLayout.EAST);

        // Crear Panel inferior con GridBagLayout para alinear separador en el centro
        JPanel panelInferior = new RoundedPanel(new Color(120, 190, 80));
        panelInferior.setLayout(new GridBagLayout()); // Usar GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Colocar primer botón
        gbc.gridx = 0;
        gbc.weightx = 1;
        panelInferior.add(createButtonPanel("src/Images/Info.png", "Informacion"), gbc);

        // Colocar separador en el centro
        gbc.gridx = 1;
        gbc.weightx = 0; // Separador no tiene peso, solo ocupa el espacio necesario
        gbc.gridheight = 2;  // Hacer que el separador se extienda por la altura de ambos botones
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(10, 50));  // Darle un tamaño adecuado al separador
        panelInferior.add(separator, gbc);

        // Colocar segundo botón
        gbc.gridx = 2;
        gbc.weightx = 1;
        panelInferior.add(createButtonPanel("src/Images/Salir.png", "Salir"), gbc);

        // Agregar el panel de botones al BorderLayout en la parte inferior
        panelContenedor.add(panelInferior, BorderLayout.CENTER);
        frame.add(panelContenedor, BorderLayout.SOUTH);

        // Crear Panel Central
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.ORANGE);
        panelCentral.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 0;
        gb.insets = new Insets(10, 10, 10, 10);

        // Colocar primer botón
        RoundedPanel panelBoton1 = createButtonPanel("src/Images/Nueva.png", "Nueva Partida",Color.RED);
        panelCentral.add(panelBoton1, gb);

        gb.gridx = 1;
        RoundedPanel panelBoton2 = createButtonPanel("src/Images/Cargar.png", "Cargar Partida",Color.BLUE);
        panelCentral.add(panelBoton2, gb);

        // Asegurarse de que los botones estén centrados y alineados uno al lado del otro
        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridwidth = 1;
        gb.anchor = GridBagConstraints.CENTER;
        panelCentral.add(panelBoton1, gb);

        gb.gridx = 1;
        panelCentral.add(panelBoton2, gb);
        frame.add(panelCentral, BorderLayout.CENTER);

        // Hacer visible el frame
        frame.setVisible(true);
    }

    // Método que crea un panel con un botón y un texto debajo
    private static JPanel createButtonPanel(String ImageRoute, String labelText) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // BoxLayout en eje Y (vertical)

        // Crear botón
        ImageButton button = new ImageButton(ImageRoute, 50, 50);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón

        // Crear texto debajo del botón
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el texto debajo del botón

        // Agregar el botón y el texto al panel
        panel.add(button);
        panel.add(label);

        return panel;
    }

    // Método que crea un panel con un botón y un texto debajo
    private static RoundedPanel createButtonPanel(String ImageRoute, String labelText, Color color) {
        RoundedPanel panel = new RoundedPanel(color);
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // BoxLayout en eje Y (vertical)

        // Crear botón
        ImageButton button = new ImageButton(ImageRoute, 50, 50);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
        button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear texto debajo del botón
        JLabel label = new JLabel(labelText);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el texto debajo del botón

        // Agregar el botón y el texto al panel
        panel.add(button);
        panel.add(label);

        return panel;
    }
}
