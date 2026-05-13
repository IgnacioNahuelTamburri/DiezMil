package Pruebas;

import javax.swing.*;
import java.awt.*;

public class MenuBotones {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Botón "Nuevo Juego"
        JButton btnNuevoJuego = new ImageButton("src/Images/Nueva.png", 100, 100);

        // Botón "Cargar Partida"
        JButton btnCargarPartida = new ImageButton("src/Images/Cargar.png", 100, 100);

        // Botón "Información" redondo con icono "i"
        ImageButton btnInformacion = new ImageButton("src/Images/Info.png", 100, 100);

        // Botón "Opciones" redondo con icono de tuerca
        ImageButton btnOpciones = new ImageButton("src/Images/Config.png", 100, 100);

        // Botón "Salir"
        ImageButton btnSalir = new ImageButton("src/Images/Salir.png", 100, 100);

        // Agregar los botones al frame
        frame.add(btnNuevoJuego);
        frame.add(btnCargarPartida);
        frame.add(btnInformacion);
        frame.add(btnOpciones);
        frame.add(btnSalir);

        // Mostrar el frame
        frame.setVisible(true);
    }
}
