package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonRedondeado extends JButton {
    private final Color baseColor;
    private final Color hoverColor;
    private final Color borderColor;
    private final int cornerRadius;

    public BotonRedondeado(String text, Color baseColor, Color hoverColor, Color borderColor, int cornerRadius) {
        super(text);
        this.baseColor = baseColor;
        this.hoverColor = hoverColor;
        this.borderColor = borderColor;
        this.cornerRadius = cornerRadius;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Botón: efecto hover
        if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(baseColor);
        }

        // Sombra
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, cornerRadius, cornerRadius);

        // Fondo del botón
        g2.setColor(getModel().isRollover() ? hoverColor : baseColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Borde
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Texto
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Botones Interactivos");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        // Colores de los botones
        Color baseColor1 = new Color(243, 201, 105); // Amarillo dorado
        Color hoverColor1 = new Color(255, 220, 130);
        Color baseColor2 = new Color(162, 217, 177); // Verde menta
        Color hoverColor2 = new Color(180, 230, 190);
        Color borderColor = new Color(76, 76, 76); // Gris oscuro

        // Botones
        JButton startButton = new BotonRedondeado("Iniciar", baseColor1, hoverColor1, borderColor, 25);
        JButton loadButton = new BotonRedondeado("Cargar Partida", baseColor1, hoverColor1, borderColor, 25);
        JButton optionsButton = new BotonRedondeado("Opciones", baseColor2, hoverColor2, borderColor, 25);
        JButton infoButton = new BotonRedondeado("Información", baseColor2, hoverColor2, borderColor, 25);
        JButton exitButton = new BotonRedondeado("Salir", baseColor2, hoverColor2, borderColor, 25);

        startButton.setPreferredSize(new Dimension(150, 40));
        loadButton.setPreferredSize(new Dimension(150, 40));
        optionsButton.setPreferredSize(new Dimension(150, 40));
        infoButton.setPreferredSize(new Dimension(150, 40));
        exitButton.setPreferredSize(new Dimension(150, 40));

        // Añadir botones al frame
        frame.add(startButton);
        frame.add(loadButton);
        frame.add(optionsButton);
        frame.add(infoButton);
        frame.add(exitButton);

        frame.setVisible(true);
    }
}
