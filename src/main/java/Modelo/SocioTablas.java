/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaSocio;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juald
 */
public class SocioTablas extends DefaultTableModel {

    private VistaSocio vSocio;

    /**
     * Constructor de la clase que hace una llamada al metodo dibujaTablaSocios
     * para que se muestre el diseño que queremos en la tabla
     * @param vSocio
     */
    public SocioTablas(VistaSocio vSocio) {
        this.vSocio = vSocio;
        dibujarTablaSocios(vSocio);
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
     * @param vSocio
     */
    public void dibujarTablaSocios(VistaSocio vSocio) {
        String[] columnasTabla = {"NumeroSocio", "Nombre", "DNI", "Fecha Nacimiento", "Teléfono", "Correo", "Fecha Entrada", "Categoría"};
        setColumnIdentifiers(columnasTabla);
    }

    /**
     * Metodo que rellena la tabla de socios con el array de socios pasado como parametro
     * @param socios
     */
    public void rellenarTablaSocios(ArrayList<Socio> socios) {
        Object[] fila = new Object[8];
        int numRegistros = socios.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = socios.get(i).getNumerosocio();
            fila[1] = socios.get(i).getNombre();
            fila[2] = socios.get(i).getDni();
            fila[3] = socios.get(i).getFechanacimiento();
            fila[4] = socios.get(i).getTelefono();
            fila[5] = socios.get(i).getCorreo();
            fila[6] = socios.get(i).getFechaentrada();
            fila[7] = socios.get(i).getCategoria();
            this.addRow(fila);
        }
    }

    /**
     * Metodo que vacia la tabla de socios
     */
    public void vaciarTablaSocios() {
        while (this.getRowCount() > 0) {
            this.removeRow(0);
        }
    }

    /**
     * Metodo que rellena la tabla de socio con el socio pasado como parametro
     * @param socio
     */
    public void rellenarTablaSocioUnico(Socio socio) {
        Object[] fila = new Object[8];

        fila[0] = socio.getNumerosocio();
        fila[1] = socio.getNombre();
        fila[2] = socio.getDni();
        fila[3] = socio.getFechanacimiento();
        fila[4] = socio.getTelefono();
        fila[5] = socio.getCorreo();
        fila[6] = socio.getFechaentrada();
        fila[7] = socio.getCategoria();
        this.addRow(fila);
    }
}
