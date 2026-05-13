package Vista;

import javax.swing.*;
import java.awt.*;

public class TextoDelineado extends JLabel {
    private Color outlineColor = Color.BLACK; // Color del borde
    private Color fillColor = Color.WHITE;    // Color del texto

    public TextoDelineado(String text, int horizontalAlignment, Color fillColor, Color outlineColor) {
        super(text, horizontalAlignment);
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        setFont(new Font("SansSerif", Font.BOLD, 48));
        setForeground(fillColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String text = getText();
        int x = getWidth() / 2 - g2d.getFontMetrics().stringWidth(text) / 2;
        int y = getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 2;

        // Dibuja el borde negro alrededor del texto
        g2d.setColor(outlineColor);
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (i != 0 || j != 0) {
                    g2d.drawString(text, x + i, y + j);
                }
            }
        }

        // Dibuja el texto principal encima (relleno)
        g2d.setColor(fillColor);
        g2d.drawString(text, x, y);
    }
}
