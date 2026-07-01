package Vista;

import Controlador.Controlador;
import Pruebas.ImageButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.*;

public class VistaGrafica implements Vista {

    private static final Color fondo = new Color(15, 23, 42); // Slate 900
    private static final Color colorBase = new Color(99, 102, 241); // Indigo 500
    private static final Color hoverColor = new Color(79, 70, 229); // Indigo 600
    private static final Color panelBg = new Color(30, 41, 59); // Slate 800
    private static final Color textColor = new Color(241, 245, 249); // Slate 100
    private static final Color textMuted = new Color(148, 163, 184); // Slate 400
    private static final Color accentColor = new Color(6, 182, 212); // Cyan 500


    private final JFrame frame = new JFrame("Diez Mil");

    private CardLayout cardLayout;

    private JPanel cardPanel;

    private Map<String, JPanel> panelMap;

    private final Map<String, ImageIcon> iconMap = new HashMap<>();

    private final Map<String, ImageIcon> seleccionados = new HashMap<>();

    private String nombre;

    private Controlador controlador;

    private Dimension tamanoPantalla;

    private Font fuente = new Font("Arial",Font.BOLD,16);

    private List<Integer> dadosSeleccionados = new ArrayList<>();

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void iniciar() {
        tamanoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        cargarImagenes();
        registrar();
    }

    public void iniciar(String nombre){
        tamanoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        cargarImagenes();
        registrar(nombre);
    }

    private void cargarImagenes() {
        ImageIcon icono = new ImageIcon("src/Images/Logo.png");
        iconMap.put("Icono", icono);
        ImageIcon menu = new ImageIcon("src/Imagenes/Diez_Mil_Icono.jpg");
        iconMap.put("Menu", menu);

        // Generate beautiful modern dice programmatically!
        for (int i = 1; i <= 6; i++) {
            iconMap.put("Dado " + i, generarDadoIcono(i, false));
            seleccionados.put("Dado " + i + " Seleccionado", generarDadoIcono(i, true));
        }
    }

    private ImageIcon generarDadoIcono(int valor, boolean seleccionado) {
        int size = 96; // Generoso tamaño para los dados
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Shadow/Glow
        if (seleccionado) {
            // Neon cyan glow for selection
            for (int i = 0; i < 4; i++) {
                g2.setColor(new Color(6, 182, 212, 40 - (i * 10))); // Cyan with fade
                g2.fillRoundRect(2 + i, 2 + i, size - 4 - (i * 2), size - 4 - (i * 2), 20, 20);
            }
        } else {
            // Soft dark shadow for standard
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(4, 6, size - 8, size - 10, 20, 20);
        }

        // 2. Body of the Die
        int margin = 8;
        int dieSize = size - (margin * 2);
        // Base color
        Color base = seleccionado ? new Color(6, 182, 212) : new Color(30, 41, 59); // Cyan vs Slate 800
        
        // Render slight gradient for the die body to give a modern 3D look
        GradientPaint bodyGp = new GradientPaint(
            margin, margin, base.brighter(),
            margin, margin + dieSize, base.darker()
        );
        g2.setPaint(bodyGp);
        g2.fillRoundRect(margin, margin, dieSize, dieSize, 18, 18);

        // 3. Border
        if (seleccionado) {
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2f));
        } else {
            g2.setColor(new Color(71, 85, 105)); // Slate 600
            g2.setStroke(new BasicStroke(1.5f));
        }
        g2.drawRoundRect(margin, margin, dieSize, dieSize, 18, 18);

        // 4. Draw Dots (Pips)
        Color dotColor = seleccionado ? Color.WHITE : new Color(241, 245, 249); // White vs Slate 100
        g2.setColor(dotColor);
        int dotSize = 12;
        int halfSize = size / 2;
        
        // Coordinates relative to the center
        int low = margin + 14;
        int mid = halfSize;
        int high = margin + dieSize - 14 - dotSize;
        
        switch (valor) {
            case 1:
                drawDot(g2, mid - (dotSize/2), mid - (dotSize/2), dotSize);
                break;
            case 2:
                drawDot(g2, low, low, dotSize);
                drawDot(g2, high, high, dotSize);
                break;
            case 3:
                drawDot(g2, low, low, dotSize);
                drawDot(g2, mid - (dotSize/2), mid - (dotSize/2), dotSize);
                drawDot(g2, high, high, dotSize);
                break;
            case 4:
                drawDot(g2, low, low, dotSize);
                drawDot(g2, low, high, dotSize);
                drawDot(g2, high, low, dotSize);
                drawDot(g2, high, high, dotSize);
                break;
            case 5:
                drawDot(g2, low, low, dotSize);
                drawDot(g2, low, high, dotSize);
                drawDot(g2, mid - (dotSize/2), mid - (dotSize/2), dotSize);
                drawDot(g2, high, low, dotSize);
                drawDot(g2, high, high, dotSize);
                break;
            case 6:
                drawDot(g2, low, low, dotSize);
                drawDot(g2, low, mid - (dotSize/2), dotSize);
                drawDot(g2, low, high, dotSize);
                drawDot(g2, high, low, dotSize);
                drawDot(g2, high, mid - (dotSize/2), dotSize);
                drawDot(g2, high, high, dotSize);
                break;
        }

        g2.dispose();
        return new ImageIcon(img);
    }

    private void drawDot(Graphics2D g2, int x, int y, int size) {
        g2.fillOval(x, y, size, size);
    }

    private void registrar() {
        Login login = new Login(this);
        login.pack();
        int x = (tamanoPantalla.width - login.getWidth()) / 2;
        int y = (tamanoPantalla.height - login.getHeight()) / 2;
        login.setLocation(x, y);
        login.setVisible(true);

        // Falta algo para que no haya nombres repetidos.

        if(this.nombre != null){
            menu();
        }
    }

    private void registrar(String nombre) {
        setJugador(nombre);
        menu();
    }

    public void setJugador(String nombre){
        this.nombre = nombre;
        controlador.agregarJugador(nombre);
    }

    private void menu(){
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertarIcono();

        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);
        cardPanel = new JPanel(cardLayout);
        frame.add(cardPanel);

        panelMap = new HashMap<>();

        JPanel menu = new JPanel();
        menu.setBackground(fondo);
        cardPanel.add(menu, "Menu");
        panelMap.put("Menu", menu);

        JPanel esperar = new JPanel();
        esperar.setBackground(fondo);
        cardPanel.add(esperar,"Esperar");
        panelMap.put("Esperar", esperar);

        JPanel jugar = new JPanel();
        jugar.setBackground(fondo);
        cardPanel.add(jugar,"Jugar");
        panelMap.put("Jugar",jugar);

        JPanel ganador = new JPanel();
        ganador.setBackground(fondo);
        cardPanel.add(ganador,"Ganador");
        panelMap.put("Ganador",ganador);

        inicializarMenu();
        cardLayout.show(cardPanel,"Menu");

        frame.setVisible(true);
    }

    private void insertarIcono() {
        frame.setIconImage(iconMap.get("Icono").getImage());
    }

    private void inicializarMenu() {
        JPanel panel = panelMap.get("Menu");
        panel.setLayout(new BorderLayout());

        // Crear Botones
        JButton nuevoJuego = new ImageButton("src/Images/Nueva.png", 140, 140);
        JButton cargar = new ImageButton("src/Images/Cargar.png", 140, 140);
        JButton opciones = new ImageButton("src/Images/Config.png", 32, 32);
        JButton informacion = new ImageButton("src/Images/Info.png", 32, 32);
        JButton salir = new ImageButton("src/Images/Salir.png", 32, 32);

        // Crear ActionListeners para los botones
        nuevoJuego.addActionListener(e -> controlador.iniciarJuego());

        cargar.addActionListener(e -> controlador.cargarPartida());

        opciones.addActionListener(e -> {
            inicializarConfiguracion();
        });

        informacion.addActionListener(e -> {
            inicializarInformacion();
        });

        salir.addActionListener(e -> salir());

        // 1. Sidebar Panel (Izquierda)
        JPanel sidebar = new JPanel();
        sidebar.setBackground(panelBg);
        sidebar.setPreferredSize(new Dimension(240, 500));
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(51, 65, 85))); // Slate 700 divider

        // Profile Area (Top of Sidebar)
        JPanel profilePanel = new JPanel();
        profilePanel.setOpaque(false);
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JButton profileIcon = new ImageButton("src/Images/User.png", 64, 64);
        profileIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userInfo = new JLabel(nombre, JLabel.CENTER);
        userInfo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        userInfo.setForeground(textColor);
        userInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInfo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        profilePanel.add(profileIcon);
        profilePanel.add(userInfo);
        sidebar.add(profilePanel, BorderLayout.NORTH);

        // Navigation Menu Options (Bottom of Sidebar)
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new GridLayout(3, 1, 10, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));

        JPanel optWrapper = createSidebarBtn(opciones, "Configuración");
        JPanel infoWrapper = createSidebarBtn(informacion, "Información");
        JPanel exitWrapper = createSidebarBtn(salir, "Salir del Juego");

        navPanel.add(optWrapper);
        navPanel.add(infoWrapper);
        navPanel.add(exitWrapper);
        sidebar.add(navPanel, BorderLayout.SOUTH);

        panel.add(sidebar, BorderLayout.WEST);

        // 2. Right Content Panel (Derecha)
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(fondo);
        content.setBorder(BorderFactory.createEmptyBorder(45, 45, 45, 45));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("DIEZ MIL", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(accentColor);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JLabel subtitle = new JLabel("¡Lanza los dados y suma puntos!", JLabel.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(textMuted);
        subtitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        titlePanel.add(subtitle, BorderLayout.SOUTH);

        content.add(titlePanel, BorderLayout.NORTH);

        // Game Action Cards (Center)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 0;
        gb.insets = new Insets(10, 15, 10, 15);

        JPanel panelBoton1 = createButtonPanel(nuevoJuego, "Nueva Partida", panelBg);
        JPanel panelBoton2 = createButtonPanel(cargar, "Cargar Partida", panelBg);

        centerPanel.add(panelBoton1, gb);
        gb.gridx = 1;
        centerPanel.add(panelBoton2, gb);

        content.add(centerPanel, BorderLayout.CENTER);
        panel.add(content, BorderLayout.CENTER);
    }

    private JPanel createSidebarBtn(JButton button, String text) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        wrapper.setOpaque(false);
        wrapper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(textMuted);

        // Add mouse click action to delegate to the button
        wrapper.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(textColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(textMuted);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                button.doClick();
            }
        });

        wrapper.add(button);
        wrapper.add(label);
        return wrapper;
    }

    // Método que crea un panel con un botón y un texto debajo
    private JPanel createButtonPanel(JButton boton, String labelText) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // BoxLayout en eje Y (vertical)

        // Crear botón
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón

        // Crear texto debajo del botón
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(textColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el texto debajo del botón

        // Agregar el botón y el texto al panel
        panel.add(boton);
        panel.add(label);

        return panel;
    }

    // Método que crea un panel con un botón y un texto debajo
    private JPanel createButtonPanel(JButton boton, String labelText, Color color) {
        JPanel panel = new RoundedPanel(color);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105), 1), // Slate 600
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // BoxLayout en eje Y (vertical)

        // Crear botón
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear texto debajo del botón
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(textColor);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el texto debajo del botón

        // Agregar el botón y el texto al panel
        panel.add(boton);
        panel.add(label);

        return panel;
    }

    private void inicializarConfiguracion(){

        JDialog configDialog = new JDialog(this.frame, "Configuración", true);

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(fondo);

        configDialog.setContentPane(imagePanel); // Establecer el panel
        configDialog.setLayout(new GridBagLayout()); // Usar GridBagLayout para los componentes

        // Crear y agregar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("Configuración", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        configDialog.add(titleLabel, gbc);

        // Botón para cambiar vista
        BotonRedondeado cambiarVista = new BotonRedondeado("Cambiar Vista", colorBase, hoverColor, null, 10);
        cambiarVista.addActionListener(e -> {
            Vista consola = new VistaConsola();
            controlador.setVista(consola);
            salir();
            consola.iniciar(nombre);
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        configDialog.add(cambiarVista, gbc);

        // Botón para cambiar nombre
        BotonRedondeado cambiarNombre = new BotonRedondeado("Cambiar Nombre", colorBase, hoverColor, null, 10);
        cambiarNombre.addActionListener(e -> {
            controlador.eliminarJugador(nombre);
            registrar();
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        configDialog.add(cambiarNombre, gbc);

        // Botón para volver
        BotonRedondeado volver = new BotonRedondeado("Volver", new Color(71, 85, 105), new Color(51, 65, 85), null, 10);
        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cierra el diálogo y vuelve al menú
                configDialog.dispose();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        configDialog.add(volver, gbc);

        // Ajustes finales
        configDialog.setSize(400, 250); // Tamaño de la ventana
        configDialog.setLocationRelativeTo(this.frame); // Centrar respecto a la ventana principal
        configDialog.setVisible(true);

    }

    private void inicializarInformacion(){

        JDialog dialog = new JDialog(this.frame, "Información", true);

        JPanel panel = new JPanel();
        panel.setBackground(fondo);
        dialog.setContentPane(panel);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label del título
        JLabel titleLabel = new JLabel("Información", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa dos columnas
        dialog.add(titleLabel, gbc);

        // Label del nombre
        JLabel nameLabel = new JLabel("Ignacio Nahuel Tamburri", SwingConstants.LEFT);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Solo una columna
        dialog.add(nameLabel, gbc);

        // Label del legajo
        JLabel legajoLabel = new JLabel("Legajo: 165046", SwingConstants.LEFT);
        legajoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        legajoLabel.setForeground(textMuted);
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialog.add(legajoLabel, gbc);

        // Botón para volver al menú
        JButton menuButton = new BotonRedondeado("Volver", new Color(71, 85, 105), new Color(51, 65, 85), null, 10);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        dialog.add(menuButton, gbc);

        // Botón para redirigir a una página
        JButton reglas = new BotonRedondeado("Reglas", colorBase, hoverColor, null, 10);
        reglas.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://juegos.dinamicasgrupales.com.ar/el-diez-mil-con-cinco-dados/"));
            } catch (Exception exception) {
                generarMensajeError("No se ha podido acceder al sitio web.");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(reglas, gbc);

        // Configuración final
        dialog.setSize(400, 200); // Tamaño de la ventana
        dialog.setLocationRelativeTo(this.frame); // Centrar respecto a la ventana principal
        dialog.setVisible(true);

    }

    public void generarMensajeError(String texto){
        MensajeError mensajeError = new MensajeError(texto);
        mensajeError.pack();
        if(texto.equals("No puedes seguir tirando, pierdes todos los puntos acumulados!.")){
            mensajeError.setTitle("No puede seguir!");
        }else{
            mensajeError.setTitle("Error");
        }
        if(texto.length() > 20){
            mensajeError.setSize(500,200);
        }else{
            mensajeError.setSize(300,200);
        }
        int x = (tamanoPantalla.width - mensajeError.getWidth()) / 2;
        int y = (tamanoPantalla.height - mensajeError.getHeight()) / 2;
        mensajeError.setLocation(x,y);
        mensajeError.setVisible(true);
    }

    public void cambioDeTurno(){
        limpiarPaneles();
        String turnoDe = controlador.turnoDe();
        if(turnoDe.equals(nombre)){
            jugarTurno();
        }else{
            esperarTurno(turnoDe);
        }
    }

    public void jugarTurno() {
        JPanel panel = panelMap.get("Jugar");
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        this.dadosSeleccionados.clear();
        panel.setLayout(new GridBagLayout());

        JPanel izquierdo = new JPanel(new BorderLayout());
        izquierdo.setBackground(fondo);
        izquierdo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel derecho = new JPanel();
        derecho.setBackground(fondo);
        derecho.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        generarTabla(derecho);

        JLabel titulo = new JLabel("Es su turno", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(accentColor);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        izquierdo.add(titulo, BorderLayout.NORTH);

        JPanel interno = new JPanel(new GridBagLayout());
        interno.setBackground(fondo);
        
        JPanel superior = new RoundedPanel(new Color(20, 27, 45)); // Deep felt rolling mat
        superior.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        superior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel inferior = new JPanel();
        inferior.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
        inferior.setBackground(fondo);

        GridBagConstraints restriccionesInternas = new GridBagConstraints();
        restriccionesInternas.gridx = 0;
        restriccionesInternas.gridy = 0;
        restriccionesInternas.weightx = 1.0;
        restriccionesInternas.weighty = 2.0;
        restriccionesInternas.fill = GridBagConstraints.BOTH;
        interno.add(superior, restriccionesInternas);
        restriccionesInternas.gridy = 1;
        restriccionesInternas.weighty = 1.0;
        interno.add(inferior, restriccionesInternas);

        GridBagConstraints gridBagConstraintsIz = new GridBagConstraints();
        gridBagConstraintsIz.gridx = 0;
        gridBagConstraintsIz.gridy = 0;
        gridBagConstraintsIz.weightx = 2.0 / 3.0;
        gridBagConstraintsIz.weighty = 1.0;
        gridBagConstraintsIz.fill = GridBagConstraints.BOTH;

        GridBagConstraints gridBagConstraintsDe = new GridBagConstraints();
        gridBagConstraintsDe.gridx = 1;
        gridBagConstraintsDe.gridy = 0;
        gridBagConstraintsDe.weightx = 1.0 / 3.0;
        gridBagConstraintsDe.weighty = 1.0;
        gridBagConstraintsDe.fill = GridBagConstraints.BOTH;

        BotonRedondeado elegir = new BotonRedondeado("Elegir Dados", colorBase, hoverColor, null, 15);
        elegir.setPreferredSize(new Dimension(180, 45));
        elegir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        elegir.setVisible(false);

        // Botón para tirar los dados
        BotonRedondeado tirar = new BotonRedondeado("Tirar Dados 🎲", new Color(16, 185, 129), new Color(5, 150, 105), null, 15);
        tirar.setPreferredSize(new Dimension(180, 45));
        tirar.setFont(new Font("Segoe UI", Font.BOLD, 15));

        tirar.addActionListener(e -> {
            controlador.tirarDados();
            imprimirDados(superior, controlador.getDados());
            elegir.setVisible(true);
            tirar.setVisible(false);
        });
        elegir.addActionListener(e -> {
            controlador.elegirDados(dadosSeleccionados);
            if(controlador.puedeSeguir()){
                Boolean[] quiere = preguntarSeguir();
                if(quiere[0] == null){
                    controlador.desseleccionarDados(dadosSeleccionados);
                }
                else if(quiere[0]){
                    tirar.setVisible(true);
                    elegir.setVisible(false);
                }
                else{
                    controlador.plantarse();
                }
            }else{
                generarMensajeError("No puede seguir tirando, pierde todos los puntos acumulados!");
                controlador.finalizarTurno();
            }
            elegir.setVisible(false);
            tirar.setVisible(true);

        });

        inferior.add(elegir);
        inferior.add(tirar);

        izquierdo.add(interno, BorderLayout.CENTER);

        panel.add(izquierdo, gridBagConstraintsIz);
        panel.add(derecho, gridBagConstraintsDe);
        cardLayout.show(cardPanel,"Jugar");
    }

    private void imprimirDados(JPanel panelSuperior, List<Integer> dados) {
        panelSuperior.removeAll();
        panelSuperior.revalidate();
        panelSuperior.repaint();
        dadosSeleccionados.clear();
        Set<Icon> iconos = new HashSet<>(iconMap.values());

        for (Integer integer : dados) {
            JButton dado = new JButton(iconMap.get("Dado " + integer));
            dado.setBorderPainted(false);
            dado.setContentAreaFilled(false);
            dado.setFocusPainted(false);
            dado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            dado.addActionListener(e -> {
                if (iconos.contains(dado.getIcon())) {
                    dadosSeleccionados.add(integer);
                    dado.setIcon(this.seleccionados.get("Dado " + integer + " Seleccionado"));
                } else {
                    dadosSeleccionados.remove(integer);
                    dado.setIcon(this.iconMap.get("Dado " + integer));
                }
            });

            panelSuperior.add(dado);
        }
        JLabel seleccionar = new JLabel("Seleccione qué dados quiere quedarse.");
        seleccionar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        seleccionar.setForeground(textMuted);
        panelSuperior.add(seleccionar);
    }


    public Boolean[] preguntarSeguir() {
        final Boolean[] quiere = new Boolean[1];
        quiere[0] = null;
        JDialog dialog = new JDialog(frame, "Pregunta", true);
        dialog.setSize(350, 160);
        
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(fondo);
        dialog.setContentPane(content);

        int cant = controlador.getDados().size();
        if(cant == 0){
            cant = 5;
        }
        String cantidad = "";
        if(cant == 1) {
            cantidad = "un dado";
        }else{
            cantidad = "los "+cant+" dados";
        }
        JLabel label = new JLabel("¿Quiere seguir tirando "+cantidad+" o plantarse?", JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(textColor);
        label.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        BotonRedondeado si = new BotonRedondeado("Seguir", colorBase, hoverColor, null, 10);
        si.setPreferredSize(new Dimension(110, 36));
        si.addActionListener(e -> {
            quiere[0] = true;
            dialog.dispose();
        });
        BotonRedondeado no = new BotonRedondeado("Plantarse", new Color(71, 85, 105), new Color(51, 65, 85), null, 10);
        no.setPreferredSize(new Dimension(110, 36));
        no.addActionListener(e -> {
            quiere[0] = false;
            dialog.dispose();
        });

        JPanel panel = new JPanel();
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        panel.add(si);
        panel.add(no);

        dialog.add(label, BorderLayout.CENTER);
        dialog.add(panel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        return quiere;
    }


    public void finTurno(){
        cambioDeTurno();
    }

    public void esperarTurno(String nombre){
        JPanel panel = panelMap.get("Esperar");

        // Panel izquierdo (información de turno)
        JPanel izquierdo = new JPanel();
        izquierdo.setLayout(new BoxLayout(izquierdo, BoxLayout.Y_AXIS)); // Mejor disposición vertical
        izquierdo.setOpaque(false);
        izquierdo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Relleno para no estar pegado

        // Panel derecho (tabla de jugadores)
        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setOpaque(false);
        generarTabla(derecho);
        derecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Relleno

        // Etiqueta de turno
        JLabel turno = new JLabel("Es el turno de: " + nombre + ".", JLabel.CENTER);
        turno.setFont(new Font("Segoe UI", Font.BOLD, 24));
        turno.setForeground(colorBase);

        // Etiqueta de espera
        JLabel espere = new JLabel("Por favor espere...", JLabel.CENTER);
        espere.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        espere.setForeground(textMuted);

        // Agregar etiquetas al panel izquierdo
        izquierdo.add(turno);
        izquierdo.add(Box.createVerticalStrut(20)); // Espacio entre los textos
        izquierdo.add(espere);

        // GridBagLayout para el panel principal
        GridBagConstraints gridBagConstraintsIz = new GridBagConstraints();
        gridBagConstraintsIz.gridx = 0;
        gridBagConstraintsIz.gridy = 0;
        gridBagConstraintsIz.weightx = 1.0 / 3.0;
        gridBagConstraintsIz.weighty = 1.0;
        gridBagConstraintsIz.fill = GridBagConstraints.BOTH;

        GridBagConstraints gridBagConstraintsDe = new GridBagConstraints();
        gridBagConstraintsDe.gridx = 1;
        gridBagConstraintsDe.gridy = 0;
        gridBagConstraintsDe.weightx = 2.0 / 3.0;
        gridBagConstraintsDe.weighty = 1.0;
        gridBagConstraintsDe.fill = GridBagConstraints.BOTH;

        // Layout del panel principal
        panel.setLayout(new GridBagLayout());
        panel.add(izquierdo, gridBagConstraintsIz);
        panel.add(derecho, gridBagConstraintsDe);

        // Cambio de tarjeta en el CardLayout
        cardLayout.show(cardPanel, "Esperar");
    }

    private void generarTabla(JPanel panel) {
        panel.setLayout(new BorderLayout());

        // Crear el modelo de la tabla
        String[] nombreColumnas = {"Jugador", "Puntos"};
        DefaultTableModel modelo = new DefaultTableModel(nombreColumnas, 0);

        // Suponiendo que 'controlador' es un objeto que proporciona los jugadores y sus puntos
        List<Integer> puntos = controlador.getPuntos();
        List<String> nombres = controlador.getJugadores();

        // Llenar la tabla con los datos de jugadores y puntos
        for (int i = 0; i < puntos.size(); i++) {
            Object[] fila = {nombres.get(i), puntos.get(i)};
            modelo.addRow(fila);
        }

        // Usar la TablaCustom en lugar de JTable estándar
        CustomTable tabla = new CustomTable(modelo);

        // Añadir la tabla al panel con un JScrollPane
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(panelBg);  // Color de fondo del viewport (Slate 800)
        scroll.setBorder(null);  // Sin borde para el JScrollPane

        panel.add(scroll, BorderLayout.CENTER);
    }


    public void ganador() {
        // Obtener el panel de ganador
        JPanel panel = panelMap.get("Ganador");
        panel.setLayout(new BorderLayout());

        // Etiqueta del ganador con un estilo más atractivo
        JLabel ganador = new JLabel("¡El ganador es " + controlador.turnoDe() + "!", JLabel.CENTER);
        ganador.setForeground(accentColor);
        ganador.setFont(new Font("Segoe UI", Font.BOLD, 42));  // Tamaño de fuente grande
        ganador.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado adicional

        // Añadir el texto al panel en la parte superior
        panel.add(ganador, BorderLayout.NORTH);

        // Crear un panel para la tabla (si quieres ponerla debajo del texto)
        JPanel leaderboard = new JPanel();
        leaderboard.setBackground(fondo);
        leaderboard.setLayout(new BorderLayout());  // Usar BorderLayout para añadir la tabla

        // Generar y añadir la tabla al panel
        generarTabla(leaderboard);

        // Añadir la tabla al centro del panel
        panel.add(leaderboard, BorderLayout.CENTER);

        // Crear un panel de control para los botones (volver al menú, etc.)
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);

        // Botón de volver al menú
        BotonRedondeado volverAJugar = new BotonRedondeado("Volver a Jugar", colorBase, hoverColor, null, 10);
        volverAJugar.setPreferredSize(new Dimension(180, 40));

        volverAJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.iniciarJuego();
            }
        });

        // Añadir el botón al panel de control
        controlPanel.add(volverAJugar);

        // Añadir el panel de control en la parte inferior
        panel.add(controlPanel, BorderLayout.SOUTH);

        // Mostrar el panel en el cardLayout
        cardLayout.show(cardPanel, "Ganador");
    }


    private void salir(){
        controlador.eliminarJugador(nombre);
        frame.dispose();
    }

    public void errorGuardar(){
        generarMensajeError("Hubo un error al guardar la partida");
    }

    public void errorCargar(){
        generarMensajeError("Hubo un error al cargar la partida");
    }

    public void noGuardado(){
        generarMensajeError("No hay partida guardada");
    }

    private void limpiarPaneles(){
        for(Component comp : cardPanel.getComponents()){
            if(comp instanceof JPanel panel){
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
            }
        }
    }

}