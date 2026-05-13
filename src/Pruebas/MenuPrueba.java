package Pruebas;

import Vista.BotonRedondeado;
import Vista.ImagePanel;
import Vista.TextoDelineado;

import javax.swing.*;
import java.awt.*;

public class MenuPrueba extends JFrame {

    public MenuPrueba() {
        // Configuración de la ventana principal
        setTitle("Diez Mil - Menú Principal");
        setSize(1920/2, 1080/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        ImagePanel imagePanel = new ImagePanel(
                "src/images/Fondo_Dados.jpg", // Ruta de tu imagen
                new Color(0, 0, 0, 0),       // Color inicial (transparente)
                new Color(0, 0, 0, 200)      // Color final (negro con algo de transparencia)
        );
        setContentPane(imagePanel);
        // ------------------ Sección 1: Encabezado ------------------
        TextoDelineado titulo = new TextoDelineado("DIEZ MIL", JLabel.CENTER, new Color(243, 201, 105), Color.BLACK);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 48));
        add(titulo, BorderLayout.NORTH);

        // ------------------ Sección 2: Imagen central ------------------
        /*ImagePanel icono = new ImagePanel("src/images/Diez_Mil_Icono.jpg"); // Cambia por tu ruta de imagen

        add(icono, BorderLayout.CENTER);*/

        // ------------------ Sección 3: Botones interactivos ------------------
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setOpaque(false);

        // Crear los botones
        JButton btnIniciar = new BotonRedondeado("Iniciar", new Color(243, 201, 105), new Color(76, 76, 76), Color.BLACK, 25);
        JButton btnCargar = new BotonRedondeado("Cargar Partida", new Color(243, 201, 105), new Color(76, 76, 76), Color.BLACK, 25);
        JButton btnOpciones = new BotonRedondeado("Opciones", new Color(162, 217, 177), new Color(76, 76, 76), Color.BLACK, 25);
        JButton btnInfo = new BotonRedondeado("Información", new Color(162, 217, 177), new Color(76, 76, 76), Color.BLACK, 25);
        JButton btnSalir = new BotonRedondeado("Salir", new Color(162, 217, 177), new Color(76, 76, 76), Color.BLACK, 25);

        // Agrupar botones en dos secciones
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(btnIniciar);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnCargar);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(new JSeparator(SwingConstants.HORIZONTAL));
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(btnOpciones);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnInfo);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnSalir);
        panelBotones.add(Box.createVerticalStrut(20));

        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Método para redimensionar una imagen
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrueba::new);
    }
}
