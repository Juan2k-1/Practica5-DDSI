/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaMonitor;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juald
 */
public class MonitorTablas extends DefaultTableModel {

    private VistaMonitor vMonitor;

    /**
     * Constructor de la clase que hace una llamada al metodo dibujaTablaMonitores
     * para que se muestre el diseño que queremos en la tabla
     * @param vMonitor
     */
    public MonitorTablas(VistaMonitor vMonitor) {
        this.vMonitor = vMonitor;
        dibujarTablaMonitores(vMonitor);
    }

    /**
     * Metodo que prohibe la edicion de las celdas de la tabla
     * @param row
     * @param column
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Metodo que diseña la tabla con los campos que queremos
     * @param vMonitor
     */
    public void dibujarTablaMonitores(VistaMonitor vMonitor) {
        String[] columnasTabla = {"Código", "Nombre", "DNI", "Teléfono", "Correo", "Fecha Incorporación", "Nick"};
        setColumnIdentifiers(columnasTabla);
    }

    /**
     * Metodo que rellena la tabla de monitores con el array de monitores pasado como parametro
     * @param monitores
     */
    public void rellenarTablaMonitores(ArrayList<Monitor> monitores) {
        Object[] fila = new Object[7];
        int numRegistros = monitores.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = monitores.get(i).getCodmonitor();
            fila[1] = monitores.get(i).getNombre();
            fila[2] = monitores.get(i).getDni();
            fila[3] = monitores.get(i).getTelefono();
            fila[4] = monitores.get(i).getCorreo();
            fila[5] = monitores.get(i).getFechaentrada();
            fila[6] = monitores.get(i).getNick();
            this.addRow(fila);
        }
    }

    /**
     * Metodo que vacia la tabla de monitores
     */
    public void vaciarTablaMonitores() {
        while (this.getRowCount() > 0) {
            this.removeRow(0);
        }
    }
}
