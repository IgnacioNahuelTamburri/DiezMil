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
        Color bgColor = new Color(245, 246, 250);
        Color primaryColor = new Color(0, 151, 230);
        Color textColor = new Color(47, 54, 64);
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
        
        buttonOK = new JButton("OK");
        buttonOK.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buttonOK.setBackground(primaryColor);
        buttonOK.setForeground(Color.WHITE);
        buttonOK.setFocusPainted(false);
        buttonOK.setBorderPainted(false);
        buttonOK.setOpaque(true);
        buttonOK.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonOK.setPreferredSize(new Dimension(100, 35));
        
        buttonPanel.add(buttonOK);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onOK() {
        dispose();
    }
}
