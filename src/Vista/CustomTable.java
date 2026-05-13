package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.border.*;

public class CustomTable extends JTable {

    public CustomTable(DefaultTableModel modelo) {
        super(modelo);
        // Personalizar la apariencia de la tabla
        setGridColor(Color.GRAY);  // Color de las líneas de la cuadrícula
        setShowGrid(true);  // Mostrar líneas de la cuadrícula
        setBorder(null);
        setIntercellSpacing(new Dimension(5, 5));  // Espaciado entre celdas
        setRowHeight(30);  // Altura de las filas
        setBackground(Color.decode("#FFEBCC"));  // Fondo claro para las celdas

        // Estilo de la cabecera de la tabla
        getTableHeader().setBackground(Color.decode("#FF8C42"));  // Fondo de la cabecera (tono anaranjado)
        getTableHeader().setForeground(Color.BLACK);  // Color blanco para el texto de la cabecera
        getTableHeader().setBorder(null);
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));  // Fuente de la cabecera
        getTableHeader().setPreferredSize(new Dimension(getTableHeader().getPreferredSize().width, 40));  // Aumentar altura de la cabecera
    }

    // Sobrescribir el método para que las celdas no tengan bordes duros
    @Override
    public boolean getShowVerticalLines() {
        return false;  // No mostrar líneas verticales
    }

    @Override
    public boolean getShowHorizontalLines() {
        return false;  // No mostrar líneas horizontales
    }
}
