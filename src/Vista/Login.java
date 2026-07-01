package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel nombre;
    private JTextField nombreTF;
    private JLabel titulo;
    private JLabel subtitulo;
    private final Vista vista;

    public Login(Vista vista) {
        this.vista = vista;
        inicializarComponentes();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Ingreso al Juego");
        setIconImage(new ImageIcon("src/Images/Logo.png").getImage());

        nombre.setLabelFor(nombreTF);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void inicializarComponentes() {
        Color bgColor = new Color(15, 23, 42); // Slate 900
        Color primaryColor = new Color(99, 102, 241); // Indigo 500
        Color primaryHover = new Color(79, 70, 229); // Indigo 600
        Color dangerColor = new Color(239, 68, 68); // Red 500
        Color dangerHover = new Color(220, 38, 38); // Red 600
        Color textColor = new Color(241, 245, 249); // Slate 100
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(bgColor);
        contentPane.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior (Título y subtítulo)
        JPanel headerPanel = new JPanel(new BorderLayout(0, 8));
        headerPanel.setBackground(bgColor);
        titulo = new JLabel("¡Bienvenido a 10000!", SwingConstants.CENTER);
        titulo.setFont(titleFont);
        titulo.setForeground(primaryColor);
        subtitulo = new JLabel("Por favor, ingrese su nombre para jugar.", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(148, 163, 184)); // Slate 400
        headerPanel.add(titulo, BorderLayout.CENTER);
        headerPanel.add(subtitulo, BorderLayout.SOUTH);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Panel central (Formulario)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nombre = new JLabel("Nombre del Jugador:");
        nombre.setFont(mainFont);
        nombre.setForeground(textColor);
        
        nombreTF = new JTextField(15);
        nombreTF.setFont(mainFont);
        nombreTF.setForeground(textColor);
        nombreTF.setBackground(new Color(30, 41, 59)); // Slate 800
        nombreTF.setCaretColor(Color.WHITE);
        nombreTF.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105), 1, true), // Slate 600
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        nombreTF.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                nombreTF.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor, 2, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombreTF.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(71, 85, 105), 1, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nombre, gbc);

        gbc.gridx = 1;
        centerPanel.add(nombreTF, gbc);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Panel inferior (Botones)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(bgColor);
        
        buttonOK = new BotonRedondeado("Jugar", primaryColor, primaryHover, null, 15);
        buttonOK.setPreferredSize(new Dimension(120, 40));
        
        buttonCancel = new BotonRedondeado("Cancelar", dangerColor, dangerHover, null, 15);
        buttonCancel.setPreferredSize(new Dimension(120, 40));
        
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onOK() {
        String nombreJugador = nombreTF.getText().trim();
        if(nombreJugador.isEmpty()) {
            subtitulo.setText("Error: El nombre no puede estar vacío.");
            subtitulo.setForeground(new Color(232, 65, 24)); // Rojo para el error
            nombreTF.requestFocus();
        } else {
            vista.setJugador(nombreJugador);
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }
}
