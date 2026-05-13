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
        Color bgColor = new Color(245, 246, 250); // Fondo claro
        Color primaryColor = new Color(0, 151, 230); // Azul moderno
        Color dangerColor = new Color(232, 65, 24); // Rojo
        Color textColor = new Color(47, 54, 64);
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(bgColor);
        contentPane.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        // Panel superior (Título y subtítulo)
        JPanel headerPanel = new JPanel(new BorderLayout(0, 5));
        headerPanel.setBackground(bgColor);
        titulo = new JLabel("¡Bienvenido a 10000!", SwingConstants.CENTER);
        titulo.setFont(titleFont);
        titulo.setForeground(primaryColor);
        subtitulo = new JLabel("Por favor, ingrese su nombre para jugar.", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(113, 128, 147));
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
        nombreTF.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nombre, gbc);

        gbc.gridx = 1;
        centerPanel.add(nombreTF, gbc);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Panel inferior (Botones)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(bgColor);
        
        buttonOK = createFlatButton("Jugar", primaryColor, Color.WHITE);
        buttonCancel = createFlatButton("Cancelar", dangerColor, Color.WHITE);
        
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createFlatButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 40));
        
        // Efecto hover simple
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        
        return btn;
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
