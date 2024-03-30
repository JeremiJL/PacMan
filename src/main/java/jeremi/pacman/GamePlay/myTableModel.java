package jeremi.pacman.GamePlay;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class myTableModel extends AbstractTableModel {


    private ImageIcon[][] elements;
    private String[] columns;

    public myTableModel(ImageIcon[][] elements, String[] columns) {
        this.elements = elements;
        this.columns = columns;
    }

    @Override
    public int getRowCount() {
        return elements.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return elements[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        elements[rowIndex][columnIndex] = (ImageIcon) aValue;
        fireTableCellUpdated(rowIndex,columnIndex);

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }



    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }




}
