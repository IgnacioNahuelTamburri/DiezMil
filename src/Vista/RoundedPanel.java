package Vista;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 20;
    private boolean gradient = false;

    // Constructor
    public RoundedPanel(Color bgColor) {
        this.backgroundColor = bgColor;
        setOpaque(false);  // Hacer que el panel sea transparente para ver el fondo redondeado
    }

    public RoundedPanel(Color bgColor, boolean gradient){
        this(bgColor);
        this.gradient = gradient;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        // Dibujar el fondo redondeado
        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            int width = getWidth();
            int height = getHeight();
            if(gradient){
                // Crear un color más claro basado en el color base
                Color lighterColor = backgroundColor.brighter();

                // Crear el degradado desde el color base hacia el color más claro
                GradientPaint gradient = new GradientPaint(
                        0, getHeight(), backgroundColor, // Color base en la parte inferior
                        0, 0, lighterColor         // Color más claro en la parte superior
                );

                // Aplicar el degradado como fondo
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
            }
            g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);  // Fondo redondeado
        }
    }

    // Método para cambiar el color de fondo
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();  // Volver a dibujar el panel con el nuevo color de fondo
    }
}
