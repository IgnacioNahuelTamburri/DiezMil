package Vista;

import Controlador.Controlador;
import Pruebas.ImageButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.List;
import java.util.*;

public class VistaGrafica implements Vista {

    private static final Color fondo = new Color(245, 246, 250);

    private static final Color colorBase = new Color(0, 151, 230);

    private static final Color hoverColor = new Color(0, 130, 200);


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

    private void cargarImagenes(){
        ImageIcon icono = new ImageIcon("src/Images/Logo.png");
        iconMap.put("Icono", icono);
        ImageIcon menu = new ImageIcon("src/Imagenes/Diez_Mil_Icono.jpg");
        iconMap.put("Menu",menu);
        ImageIcon dado1 = new ImageIcon("src/Images/Dados/Dado1.png");
        iconMap.put("Dado 1",dado1);
        ImageIcon dado2 = new ImageIcon("src/Images/Dados/Dado2.png");
        iconMap.put("Dado 2",dado2);
        ImageIcon dado3 = new ImageIcon("src/Images/Dados/Dado3.png");
        iconMap.put("Dado 3",dado3);
        ImageIcon dado4 = new ImageIcon("src/Images/Dados/Dado4.png");
        iconMap.put("Dado 4",dado4);
        ImageIcon dado5 = new ImageIcon("src/Images/Dados/Dado5.png");
        iconMap.put("Dado 5",dado5);
        ImageIcon dado6 = new ImageIcon("src/Images/Dados/Dado6.png");
        iconMap.put("Dado 6",dado6);
        ImageIcon dado1Seleccionado = new ImageIcon("src/Images/Dados/Dado1Seleccionado.png");
        seleccionados.put("Dado 1 Seleccionado", dado1Seleccionado);
        ImageIcon dado2Seleccionado = new ImageIcon("src/Images/Dados/Dado2Seleccionado.png");
        seleccionados.put("Dado 2 Seleccionado",dado2Seleccionado);
        ImageIcon dado3Seleccionado = new ImageIcon("src/Images/Dados/Dado3Seleccionado.png");
        seleccionados.put("Dado 3 Seleccionado",dado3Seleccionado);
        ImageIcon dado4Seleccionado = new ImageIcon("src/Images/Dados/Dado4Seleccionado.png");
        seleccionados.put("Dado 4 Seleccionado",dado4Seleccionado);
        ImageIcon dado5Seleccionado = new ImageIcon("src/Images/Dados/Dado5Seleccionado.png");
        seleccionados.put("Dado 5 Seleccionado",dado5Seleccionado);
        ImageIcon dado6Seleccionado = new ImageIcon("src/Images/Dados/Dado6Seleccionado.png");
        seleccionados.put("Dado 6 Seleccionado", dado6Seleccionado);
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
        frame.setSize(600, 400);
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

        // Crear Titulo
        JLabel titulo = new JLabel("Diez Mil", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(new Color(47, 54, 64));

        // Crear Botones
        JButton nuevoJuego = new ImageButton("src/Images/Nueva.png", 150, 150);
        JButton cargar = new ImageButton("src/Images/Cargar.png", 150, 150);
        JButton opciones = new ImageButton("src/Images/Config.png", 50, 50);
        JButton informacion = new ImageButton("src/Images/Info.png", 50, 50);
        JButton salir = new ImageButton("src/Images/Salir.png", 50, 50);

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

        // Crear el panel principal con BorderLayout
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 225, 230)));
        topPanel.setLayout(new BorderLayout());

        // Panel izquierdo para la imagen y el texto
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Agregar la imagen (ícono)
        JButton profileIcon = new ImageButton("src/Images/User.png", 50, 50);
        leftPanel.add(profileIcon);

        // Agregar el texto
        JPanel roundedPanel = new JPanel();
        roundedPanel.setBackground(Color.WHITE);
        roundedPanel.setLayout(new BorderLayout());
        JLabel userInfo = new JLabel(nombre);
        userInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userInfo.setForeground(new Color(47, 54, 64));
        userInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        roundedPanel.add(userInfo);
        leftPanel.add(roundedPanel);

        // Panel derecho para los botones
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Botón de configuraciones
        rightPanel.add(opciones);

        // Agregar los paneles izquierdo y derecho al panel principal
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Agregar el panel principal al frame
        panel.add(topPanel, BorderLayout.NORTH);

        // Panel contenedor del Panel Inferior
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(fondo);
        panelContenedor.setLayout(new BorderLayout());
        JPanel panelInfIzq = new JPanel();
        panelInfIzq.setOpaque(false);
        panelInfIzq.setPreferredSize(new Dimension(75, 0));
        panelContenedor.add(panelInfIzq, BorderLayout.WEST);
        JPanel panelInfDer = new JPanel();
        panelInfDer.setOpaque(false);
        panelInfDer.setPreferredSize(new Dimension(75, 0));
        panelContenedor.add(panelInfDer, BorderLayout.EAST);

        // Crear Panel inferior con GridBagLayout para alinear separador en el centro
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 225, 230)));
        panelInferior.setLayout(new GridBagLayout()); // Usar GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Colocar primer botón
        gbc.gridx = 0;
        gbc.weightx = 1;
        panelInferior.add(createButtonPanel(informacion, "Informacion"), gbc);

        // Colocar separador en el centro
        gbc.gridx = 1;
        gbc.weightx = 0; // Separador no tiene peso, solo ocupa el espacio necesario
        gbc.gridheight = 2;  // Hacer que el separador se extienda por la altura de ambos botones
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(10, 50));  // Darle un tamaño adecuado al separador
        panelInferior.add(separator, gbc);

        // Colocar segundo botón
        gbc.gridx = 2;
        gbc.weightx = 1;
        panelInferior.add(createButtonPanel(salir, "Salir"), gbc);

        // Agregar el panel de botones al BorderLayout en la parte inferior
        panelContenedor.add(panelInferior, BorderLayout.CENTER);
        panel.add(panelContenedor, BorderLayout.SOUTH);

        // Crear Panel Central
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(fondo);
        panelCentral.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 0;
        gb.insets = new Insets(10, 10, 10, 10);

        // Colocar primer botón
        JPanel panelBoton1 = createButtonPanel(nuevoJuego, "Nueva Partida",Color.WHITE);
        panelCentral.add(panelBoton1, gb);

        gb.gridx = 1;
        JPanel panelBoton2 = createButtonPanel(cargar, "Cargar Partida",Color.WHITE);
        panelCentral.add(panelBoton2, gb);

        // Asegurarse de que los botones estén centrados y alineados uno al lado del otro
        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridwidth = 1;
        gb.anchor = GridBagConstraints.CENTER;
        panelCentral.add(panelBoton1, gb);

        gb.gridx = 1;
        panelCentral.add(panelBoton2, gb);
        panel.add(panelCentral, BorderLayout.CENTER);
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
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el texto debajo del botón

        // Agregar el botón y el texto al panel
        panel.add(boton);
        panel.add(label);

        return panel;
    }

    // Método que crea un panel con un botón y un texto debajo
    private JPanel createButtonPanel(JButton boton, String labelText, Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // BoxLayout en eje Y (vertical)

        // Crear botón
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear texto debajo del botón
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(47, 54, 64));
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
        titleLabel.setForeground(new Color(47, 54, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        configDialog.add(titleLabel, gbc);

        // Botón para cambiar vista
        BotonRedondeado cambiarVista = new BotonRedondeado("Cambiar Vista", colorBase, hoverColor, Color.WHITE, 10);
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
        BotonRedondeado cambiarNombre = new BotonRedondeado("Cambiar Nombre", colorBase, hoverColor, Color.WHITE, 10);
        cambiarNombre.addActionListener(e -> {
            controlador.eliminarJugador(nombre);
            registrar();
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        configDialog.add(cambiarNombre, gbc);

        // Botón para volver
        BotonRedondeado volver = new BotonRedondeado("Volver", new Color(127, 143, 166), new Color(113, 128, 147), Color.WHITE, 10);
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
        titleLabel.setForeground(new Color(47, 54, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa dos columnas
        dialog.add(titleLabel, gbc);

        // Label del nombre
        JLabel nameLabel = new JLabel("Ignacio Nahuel Tamburri", SwingConstants.LEFT);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(47, 54, 64));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Solo una columna
        dialog.add(nameLabel, gbc);

        // Label del legajo
        JLabel legajoLabel = new JLabel("Legajo: 165046", SwingConstants.LEFT);
        legajoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        legajoLabel.setForeground(new Color(47, 54, 64));
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialog.add(legajoLabel, gbc);

        // Botón para volver al menú
        JButton menuButton = new BotonRedondeado("Volver", new Color(127, 143, 166), new Color(113, 128, 147), Color.WHITE, 10);
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
        JButton reglas = new BotonRedondeado("Reglas", colorBase, hoverColor, Color.WHITE, 10);
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
        JPanel derecho = new JPanel();
        derecho.setBackground(fondo);
        generarTabla(derecho);

        JLabel titulo = new JLabel("Es su turno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        izquierdo.add(titulo, BorderLayout.NORTH);

        JPanel interno = new JPanel(new GridBagLayout());
        interno.setBackground(fondo);
        JPanel superior = new JPanel(new FlowLayout());
        superior.setBackground(fondo);
        JPanel inferior = new JPanel();
        inferior.setLayout(new BoxLayout(inferior, BoxLayout.Y_AXIS));
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


        BotonRedondeado elegir = new BotonRedondeado("Elegir", colorBase, hoverColor, Color.BLACK, 25);
        elegir.setVisible(false);
        elegir.setFocusPainted(false);
        elegir.setBackground(Color.LIGHT_GRAY);


        // Botón para tirar los dados
        JButton tirar = new ImageButton("src/Images/TirarDados.png");

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

        tirar.setFocusPainted(false);
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
        JLabel seleccionar = new JLabel("Seleccione que dados quiere quedarse.");


        //controlador.calcularPuntos();

        seleccionar.setFont(fuente);
        seleccionar.setBackground(fondo);
        panelSuperior.add(seleccionar);
        //panelSuperior.add(elegir);
    }


    public Boolean[] preguntarSeguir() {
        final Boolean[] quiere = new Boolean[1];
        quiere[0] = null;
        JDialog dialog = new JDialog(frame, "Pregunta", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new BorderLayout());
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
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(47, 54, 64));
        BotonRedondeado si = new BotonRedondeado("Seguir", colorBase, hoverColor, Color.WHITE, 10);
        si.addActionListener(e -> {
            quiere[0] = true;
            dialog.dispose();
        });
        BotonRedondeado no = new BotonRedondeado("Plantarse", new Color(127, 143, 166), new Color(113, 128, 147), Color.WHITE, 10);
        no.addActionListener(e -> {
            quiere[0] = false;
            dialog.dispose();
        });

        JPanel panel = new JPanel();

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
        espere.setForeground(new Color(113, 128, 147)); // Gray

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
        scroll.getViewport().setBackground(Color.decode("#FFEBCC"));  // Color de fondo del viewport
        scroll.setBorder(null);  // Sin borde para el JScrollPane

        panel.add(scroll, BorderLayout.CENTER);
    }


    public void ganador() {
        // Obtener el panel de ganador
        JPanel panel = panelMap.get("Ganador");
        panel.setLayout(new BorderLayout());

        // Etiqueta del ganador con un estilo más atractivo
        JLabel ganador = new JLabel("¡El ganador es " + controlador.turnoDe() + "!", JLabel.CENTER);
        ganador.setForeground(colorBase);
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
        BotonRedondeado volverAJugar = new BotonRedondeado("Volver a Jugar", colorBase, hoverColor, Color.WHITE, 10);

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