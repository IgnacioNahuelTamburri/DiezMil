package Pruebas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedLabel extends JLabel {
    private Color backgroundColor;

    // Constructor
    public RoundedLabel(String text, Color bgColor) {
        super(text);
        this.backgroundColor = bgColor;
        setOpaque(false);  // Hacer que el JLabel sea transparente para poder dibujar encima
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Crear el fondo redondeado
        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 20 es el radio de las esquinas redondeadas
        }

        // Dibujar el texto
        super.paintComponent(g);
    }

    // Método para cambiar el color de fondo
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();  // Volver a dibujar el JLabel
    }
}

