package Pruebas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageButton extends JButton {
    private ImageIcon icon;
    private ImageIcon iconHover;
    private ImageIcon iconPressed;

    public ImageButton(String imagePath){
        this(imagePath, 100, 100);
    }

    // Constructor que recibe la ruta de la imagen
    public ImageButton(String imagePath, int sizeX, int sizeY) {
        ImageIcon icono = new ImageIcon(imagePath);  // Cargar la imagen desde la ruta
        Image image = icono.getImage().getScaledInstance(sizeX, sizeY, Image.SCALE_SMOOTH);
        this.icon = new ImageIcon(image);
        setContentAreaFilled(false);           // Sin fondo
        setFocusPainted(false);                // Sin foco
        setBorderPainted(false);               // Sin borde
        setIcon(this.icon);

        // Crear el contenedor AlphaContainer para la imagen
        AlphaContainer alphaContainer = new AlphaContainer(icon);

        // Aplicar las transparencias para las versiones hover y pressed
        alphaContainer.applyAlpha(0.7f);

        // Crear iconos para el hover y el presionado, con tamaños ajustados
        this.iconHover = alphaContainer.getAlphaIcon(); // Imagen con efecto hover
        this.iconPressed = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth() - 10, icon.getIconHeight() - 10, Image.SCALE_SMOOTH));  // Aumentar tamaño

        AlphaContainer alphaContainer1 = new AlphaContainer(iconPressed);
        alphaContainer1.applyAlpha(0.5f);
        this.iconPressed = alphaContainer1.getAlphaIcon();

        // Ajustar el tamaño del botón al tamaño de la imagen
        Dimension imageSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        setPreferredSize(imageSize);           // Establecer el tamaño preferido al tamaño de la imagen

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(iconHover);  // Cambiar a la imagen con efecto hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(icon);  // Restaurar la imagen original cuando el mouse sale
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(iconPressed);  // Cambiar a la imagen presionada
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(iconHover);  // Restaurar la imagen hover al soltar el clic
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Botones con Imagen");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        // Ruta de las imágenes
        String imagePath1 = "src/Images/Info.png";  // Imagen de inicio

        // Botones con imágenes
        JButton startButton = new ImageButton(imagePath1);

        frame.getContentPane().setBackground(Color.BLUE);
        // Añadir botones al frame
        frame.add(startButton);

        frame.setVisible(true);
    }
}
