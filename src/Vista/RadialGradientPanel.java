package Vista;
import javax.swing.*;
import java.awt.*;

public class RadialGradientPanel extends JPanel {
    private Color centerColor;
    private Color edgeColor;

    public RadialGradientPanel(Color centerColor, Color edgeColor) {
        this.centerColor = centerColor;
        this.edgeColor = edgeColor;
        setPreferredSize(new Dimension(400, 400)); // Tamaño del panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        float radius = Math.min(width, height) / 2f;

        // Crear un degradado radial
        RadialGradientPaint gradientPaint = new RadialGradientPaint(
                new Point(width / 2, height / 2), // Centro del degradado
                radius, // Radio del degradado
                new float[]{0f, 0.2f, 0.6f, 0.8f, 1f}, // Fracciones
                new Color[]{
                        new Color(255, 255, 100), // Amarillo
                        new Color(255, 200, 100), // Naranja claro
                        new Color(253, 197, 92), // Naranja mas claro
                        new Color(255, 170, 50), // Naranja intermedio
                        new Color(255, 140, 0), // Naranja fuerte
                } // Colores correspondientes
        );

        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, width, height); // Dibujar el degradado en el fondo
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Degradado Naranja Radial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RadialGradientPanel gradientPanel = new RadialGradientPanel(new Color(255, 209, 161), new Color(255, 140, 0));
        frame.add(gradientPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
