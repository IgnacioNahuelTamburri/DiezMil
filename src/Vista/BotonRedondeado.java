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
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        boolean isPressed = getModel().isPressed();
        boolean isHovered = getModel().isRollover();

        int width = getWidth();
        int height = getHeight();

        // 1. Soft Shadow
        if (!isPressed) {
            int shadowOpacity = isHovered ? 40 : 25;
            for (int i = 1; i <= 3; i++) {
                g2.setColor(new Color(0, 0, 0, shadowOpacity / i));
                g2.fillRoundRect(i, i + 1, width - (i * 2), height - (i * 2) - 1, cornerRadius, cornerRadius);
            }
        }

        // 2. Button Background
        Color bg = isPressed ? baseColor.darker() : (isHovered ? hoverColor : baseColor);
        g2.setColor(bg);
        
        // Push button down slightly if pressed
        int offset = isPressed ? 2 : 0;
        g2.fillRoundRect(0, offset, width - 1, height - 1 - offset, cornerRadius, cornerRadius);

        // 3. Border
        if (borderColor != null) {
            // Draw a slightly glowing border when hovered
            if (isHovered) {
                g2.setColor(borderColor.brighter());
                g2.setStroke(new BasicStroke(1.5f));
            } else {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.0f));
            }
            g2.drawRoundRect(0, offset, width - 1, height - 1 - offset, cornerRadius, cornerRadius);
        }

        // 4. Text
        g2.setColor(getForeground() != null ? getForeground() : Color.WHITE);
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (width - fm.stringWidth(getText())) / 2;
        int y = (height + fm.getAscent()) / 2 - fm.getDescent() + offset - 1;
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
