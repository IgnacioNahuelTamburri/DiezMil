package Vista;
import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color baseColor;

    // Constructor que toma un color base
    public GradientPanel(Color baseColor) {
        this.baseColor = baseColor;
    }

    // Sobrescribir el método paintComponent para dibujar el degradado
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Crear un color más claro basado en el color base
        Color lighterColor = baseColor.brighter();

        // Crear el degradado desde el color base hacia el color más claro
        GradientPaint gradient = new GradientPaint(
                0, getHeight(), baseColor, // Color base en la parte inferior
                0, 0, lighterColor         // Color más claro en la parte superior
        );

        // Aplicar el degradado como fondo
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    // Método para cambiar el color base en tiempo de ejecución
    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
        repaint(); // Redibujar el panel
    }

    // Método principal para probar el GradientPanel
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gradient Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crear un GradientPanel con un color base
        GradientPanel gradientPanel = new GradientPanel(new Color(70, 130, 180)); // Azul acero

        // Agregar el panel al frame
        frame.add(gradientPanel);

        frame.setVisible(true);
    }
}