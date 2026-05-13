package Pruebas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class AlphaContainer {
    private ImageIcon originalIcon;
    private ImageIcon alphaIcon;

    public AlphaContainer(ImageIcon icon) {
        this.originalIcon = icon;
    }

    // Método para aplicar el alpha
    public void applyAlpha(float alpha) {
        Image img = originalIcon.getImage();
        int width = img.getWidth(null);
        int height = img.getHeight(null);

        // Crear una imagen de tipo ARGB para soportar transparencia
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        // Aplicamos el valor alpha para la transparencia
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Crear el nuevo ImageIcon con el alfa aplicado
        alphaIcon = new ImageIcon(bufferedImage);
    }

    // Obtener la imagen con alpha
    public ImageIcon getAlphaIcon() {
        return alphaIcon;
    }

    // Main para probar AlphaContainer con botones
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prueba AlphaContainer");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        // Ruta de las imágenes
        String imagePath1 = "src/Images/Info.png";  // Imagen de inicio

        // Cargar las imágenes
        ImageIcon icon1 = new ImageIcon(imagePath1);

        // Crear los AlphaContainers
        AlphaContainer alphaContainer1 = new AlphaContainer(icon1);
        AlphaContainer alphaContainer2 = new AlphaContainer(icon1);

        // Aplicar transparencia a las imágenes
        alphaContainer1.applyAlpha(0.5f);  // 50% transparencia
        alphaContainer2.applyAlpha(0.3f);  // 30% transparencia


        // Crear botones con las imágenes modificadas
        JButton button1 = new JButton(icon1);
        button1.setFocusPainted(false);
        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        button1.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseEntered(MouseEvent e) {
                 button1.setIcon(alphaContainer1.getAlphaIcon());
             }

             @Override
             public void mouseExited(MouseEvent e) {
                 button1.setIcon(icon1);
             }
             @Override
             public void mousePressed(MouseEvent e){
                 button1.setIcon(alphaContainer2.getAlphaIcon());
             }
             @Override
             public void mouseReleased(MouseEvent e){
                 button1.setIcon(alphaContainer1.getAlphaIcon());
             }
        });

        // Ajustar el tamaño de los botones al tamaño de la imagen
        button1.setPreferredSize(new Dimension(icon1.getIconWidth(), icon1.getIconHeight()));

        // Añadir botones al frame
        frame.add(button1);
        frame.getContentPane().setBackground(Color.BLUE);

        frame.setVisible(true);
    }
}
