package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.border.*;

public class CustomTable extends JTable {

    public CustomTable(DefaultTableModel modelo) {
        super(modelo);
        setFillsViewportHeight(true);
        setBackground(new Color(30, 41, 59)); // Slate 800
        setForeground(new Color(241, 245, 249)); // Slate 100
        setGridColor(new Color(51, 65, 85)); // Slate 700
        setShowGrid(false);
        setRowHeight(35);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setSelectionBackground(new Color(99, 102, 241, 80)); // Indigo subtle selection
        setSelectionForeground(Color.WHITE);
        setBorder(null);

        // Header style
        JTableHeader header = getTableHeader();
        header.setBackground(new Color(15, 23, 42)); // Slate 900
        header.setForeground(new Color(129, 140, 248)); // Indigo 400
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(99, 102, 241)));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (c instanceof JComponent jc) {
            jc.setBorder(new EmptyBorder(0, 15, 0, 15)); // Horizontal cell padding
        }
        if (!isRowSelected(row)) {
            // Alternating rows
            c.setBackground(row % 2 == 0 ? new Color(30, 41, 59) : new Color(15, 23, 42));
            c.setForeground(new Color(241, 245, 249));
        }
        return c;
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

