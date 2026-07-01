package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MensajeError extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel mensajeLabel;

    public MensajeError(String mensaje) {
        inicializarComponentes(mensaje);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setIconImage(new ImageIcon("src/Images/Logo.png").getImage());

        buttonOK.addActionListener(e -> onOK());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes(String mensaje) {
        Color bgColor = new Color(15, 23, 42); // Slate 900
        Color primaryColor = new Color(99, 102, 241); // Indigo 500
        Color primaryHover = new Color(79, 70, 229); // Indigo 600
        Color textColor = new Color(241, 245, 249); // Slate 100
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);

        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(bgColor);
        contentPane.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        mensajeLabel = new JLabel("<html><div style='text-align: center;'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        mensajeLabel.setFont(mainFont);
        mensajeLabel.setForeground(textColor);
        contentPane.add(mensajeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(bgColor);
        
        buttonOK = new BotonRedondeado("OK", primaryColor, primaryHover, null, 12);
        buttonOK.setPreferredSize(new Dimension(100, 35));
        
        buttonPanel.add(buttonOK);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onOK() {
        dispose();
    }
}
