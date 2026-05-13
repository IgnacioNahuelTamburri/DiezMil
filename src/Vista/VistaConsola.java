package Vista;

import Controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VistaConsola implements Vista {

    private Controlador controlador;
    private JFrame frame;
    private final JTextField textField;
    private final JTextArea textArea;
    private String nombre;
    private String estado;

    public VistaConsola(){
        // Crear Frame
        frame = new JFrame("Consola Diez Mil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);

        // Crear JTextArea
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));

        // Colocar JScrollPane al JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Crear JTextField
        textField = new JTextField();
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Evento Presionar Enter
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                agregarTexto("> "+ input +"\n");
                textField.setText("");

                // Procesar Comando
                procesarComando(input, textArea);
            }
        });

        // Agregar componentes al JFrame
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(textField, BorderLayout.SOUTH);

        // Mostrar el JFrame
        frame.setVisible(true);
    }

    public void iniciar(String nombre){
        registrar(nombre);
    }

    public void iniciar(){
        registrar();
    }

    public void setControlador(Controlador controlador){
        this.controlador = controlador;
    }

    private void registrar() {
        agregarTexto("Ingrese su nombre: \n");

        // Crear un CountDownLatch para esperar la entrada del usuario
        CountDownLatch latch = new CountDownLatch(1);

        // Crear una referencia al ActionListener
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                agregarTexto("> " + input + "\n");
                setJugador(input);

                // Limpiar campo de texto y remover este listener
                textField.setText("");
                textField.removeActionListener(this);

                // Liberar el bloqueo
                latch.countDown();
            }
        };

        // Agregar el listener al campo de texto
        textField.addActionListener(listener);

        // Esperar a que el usuario ingrese el nombre
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cambiar el estado antes de mostrar el menú
        estado = "menu";
        menu();
    }


    private void registrar(String nombre){
        setJugador(nombre);
        menu();
    }

    public void setJugador(String nombre){
        this.nombre = nombre;
        controlador.agregarJugador(nombre);
    }

    private void menu(){
        agregarTexto("=============Diez Mil=============\n1.\tNuevo Juego.\n2.\tCargar Partida.\n3.\tOpciones.\n4.\tInformacion.\n5.\tSalir.\n==================================\n");
        estado = "menu";
    }

    private void nuevoJuego(){
        controlador.iniciarJuego();
    }

    public void cambioDeTurno(){
        String turnoDe = controlador.turnoDe();
        if(turnoDe.equals(nombre)){
            jugarTurno();
        }else{
            esperarTurno(turnoDe);
        }
    }

    private void jugarTurno() {
        estado = "jugando";
        agregarTexto("==================================\nEs su turno.\n");

        // Manejar la tirada inicial
        manejarTirada();
    }

    private void manejarTirada() {
        // Obtener los dados
        List<Integer> dados = controlador.getDados();
        String cantidad = (dados.size() == 1) ? "el dado" : "los dados";

        // Mostrar mensaje para tirar
        agregarTexto("Presione Enter para tirar " + cantidad + "...\n");

        // Configurar el estado para esperar entrada
        estado = "tirar";

        // Agregar un ActionListener temporal al JTextField
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Procesar tirada
                textField.setText(""); // Limpiar campo de texto
                textField.removeActionListener(this); // Eliminar listener
                procesarTirada();
            }
        });
    }

    private void procesarTirada() {
        agregarTexto("Tirando los dados...\n\n");

        // Simular un retraso en la tirada para mejor UX
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener resultados
                List<Integer> dados = controlador.tirarDados();

                // Mostrar resultados
                agregarTexto("El resultado de la tirada es:\n");
                for (int i : dados) {
                    switch (i) {
                        case 1 -> agregarTexto("⚀ (Dado 1) ");
                        case 2 -> agregarTexto("⚁ (Dado 2) ");
                        case 3 -> agregarTexto("⚂ (Dado 3) ");
                        case 4 -> agregarTexto("⚃ (Dado 4) ");
                        case 5 -> agregarTexto("⚄ (Dado 5) ");
                        case 6 -> agregarTexto("⚅ (Dado 6) ");
                    }
                }
                agregarTexto("\n");

                // Proceder a seleccionar dados
                ((Timer) e.getSource()).stop(); // Detener el temporizador
                manejarSeleccion(dados);
            }
        });
        timer.setRepeats(false); // Asegurarse de que no se repita
        timer.start();
    }

    private void manejarSeleccion(List<Integer> dados) {
        estado = "seleccionar";
        agregarTexto("Seleccione el/los índices de los dados que quiere quedarse (separados por espacios):\n");

        // Agregar un ActionListener temporal para manejar la selección
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                agregarTexto("> "+ input +"\n");
                textField.setText(""); // Limpiar campo de texto
                textField.removeActionListener(this); // Eliminar listener

                try {
                    // Procesar selección
                    String[] indices = input.split(" ");
                    List<Integer> dadosSeleccionados = new ArrayList<>();
                    for (String indice : indices) {
                        dadosSeleccionados.add(dados.get(Integer.parseInt(indice)));
                    }

                    controlador.elegirDados(dadosSeleccionados);
                    int puntos = controlador.calcularPuntos();
                    agregarTexto("Los dados seleccionados suman un total de " + puntos + " puntos.\n");

                    if (!controlador.puedeSeguir()) {
                        agregarTexto("No puede seguir tirando, por lo tanto pierde todos los puntos acumulados en el turno!\n");
                        controlador.finalizarTurno();
                    } else {
                        agregarTexto("Puntos acumulados: " + controlador.getAcumulados() + "\n");
                        preguntarSeguir();
                    }
                } catch (Exception ex) {
                    agregarTexto("Entrada inválida. Intente nuevamente.\n");
                    manejarSeleccion(dados); // Reintentar
                }
            }
        });
    }

    private void preguntarSeguir() {
        estado = "preguntar";
        agregarTexto("¿Quiere seguir (1) o plantarse (2)?\n");

        // Agregar un ActionListener temporal para manejar la respuesta
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                agregarTexto("> "+ input +"\n");
                textField.setText(""); // Limpiar campo de texto
                textField.removeActionListener(this); // Eliminar listener

                switch (input) {
                    case "1" -> manejarTirada();
                    case "2" -> controlador.plantarse();
                    default -> {
                        agregarTexto("Seleccione una opción válida.\n");
                        preguntarSeguir(); // Reintentar
                    }
                }
            }
        });
    }

    private void esperarTurno(String nombreJugador){
        agregarTexto("==================================\nEs el turno de "+nombreJugador+", por favor espere...\n==================================\n");
    }

    public void finTurno(){
        agregarTexto("==================================\nTurno finalizado.\nNuevos Puntos:\n");
        List<String> nombre = controlador.getJugadores();
        List<Integer> puntos = controlador.getPuntos();
        for(int i=0;i< nombre.size(); i++){
            agregarTexto(nombre.get(i)+": "+puntos.get(i)+"\n");
        }
        agregarTexto("==================================\n");
        cambioDeTurno();
    }

    public void ganador(){
        agregarTexto("==================================\nEl jugador "+controlador.turnoDe()+" ha conseguido los 10000 puntos y ha ganado!!\n==================================\n");
        //mostrarTop();
    }

    private void cargar(){
        agregarTexto("==================================\nCargando Partida anterior...\nPresione Enter para continuar...\n");
        estado = "cargando";
    }

    private void opciones(){
        agregarTexto("=============Opciones=============\n1.\tCambiar Nombre.\n2.\tCambiar Vista.\n3.\tVolver.\n==================================\n");
        estado = "opciones";
    }

    private void informacion(){
        agregarTexto("============Informacion===========\n1.\tReglas.\n2.\tAcerca de.\n3.\tInicio.\n==================================\n");
        estado = "informacion";
    }

    private void redirigir(){
        try{
            Desktop.getDesktop().browse(new URI("https://juegos.dinamicasgrupales.com.ar/el-diez-mil-con-cinco-dados/"));
        }catch (Exception e){
            agregarTexto("No se ha podido redirigir a la pagina. \nIntente nuevamente mas tarde.\n");
        }
        informacion();
    }

    private void acercaDe(){
        agregarTexto("=============Acerca de============\nDesarrollado por: Ignacio Nahuel Tamburri.\nLegajo: 165046\n==================================\nPresione Enter para volver al inicio.\n");
        estado = "acercaDe";
    }

    private void salir(){
        controlador.eliminarJugador(nombre);
        frame.dispose();
    }

    public void errorCargar(){
        agregarTexto("Hubo un error al cargar la partida.\n");
        menu();
    }

    public void errorGuardar(){
        agregarTexto("Hubo un error al guardar la partida.\n");
    }

    public void noGuardado(){
        agregarTexto("No hay partida guardada.\n");
        menu();
    }

    private void procesarComando(String comando, JTextArea textArea) {
        // Evaluar en que estado se encuentra el juego
        switch (estado) {
            case "menu":
                switch (comando){
                    case "1" -> nuevoJuego();
                    case "2" -> cargar();
                    case "3" -> opciones();
                    case "4" -> informacion();
                    case "5" -> salir();
                    default -> agregarTexto("Seleccione una opcion valida por favor.\n");
                }
                break;
            case "cargando":
                controlador.cargarPartida();
                break;
            case "opciones":
                switch (comando){
                    case "1" -> {
                        controlador.eliminarJugador(nombre);
                        registrar();
                    }
                    case "2" -> {
                        Vista grafica = new VistaGrafica();
                        controlador.setVista(grafica);
                        salir();
                        grafica.iniciar(nombre);
                    }
                    case "3" -> menu();
                    default -> agregarTexto("Seleccione una opcion valida por favor.");
                }
                break;
            case "informacion":
                switch (comando){
                    case "1" -> redirigir();
                    case "2" -> acercaDe();
                    case "3" -> menu();
                    default -> agregarTexto("Seleccione una opcion valida por favor.");
                }
                break;
            case "acercaDe":
                informacion();
                break;
            default:
        }
    }

    private void agregarTexto(String texto) {
        textArea.append(texto + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

}