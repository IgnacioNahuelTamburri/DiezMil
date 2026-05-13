package Vista;


import javax.swing.*;
import java.awt.*;
public class ImagePanel extends JPanel {
    private Image imagen;
    private Color colorInicio;
    private Color colorFin;

    public ImagePanel(String rutaImagen){
        this(rutaImagen, null, null);
    }
    // Constructor para inicializar la imagen y los colores del degradado
    public ImagePanel(String rutaImagen, Color colorInicio, Color colorFin) {
        this.imagen = new ImageIcon(rutaImagen).getImage();
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar la imagen de fondo escalada
        int width = getWidth();
        int height = getHeight();
        g2d.drawImage(imagen, 0, 0, width, height, this);

        if(colorFin != null){
            // Crear un degradado con transparencia
            GradientPaint gradiente = new GradientPaint(
                    0, 0, colorInicio, // Coordenadas y color inicial
                    0, height, colorFin // Coordenadas y color final
            );
            g2d.setPaint(gradiente);

            // Aplicar transparencia al degradado
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% de opacidad
            g2d.fillRect(0, 0, width, height); // Dibujar el degradado
        }
    }
}
