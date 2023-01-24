/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.VistaGestionActividades;
import Vista.VistaMensaje;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author juald
 */
public class ControladorActividad {

    private VistaGestionActividades vAdministrarSocios;
    private Session conexion;
    private ArrayList<Socio> listaSocios;
    private Socio socioSeleccionado;
    private VistaMensaje vMensaje;

    public DefaultTableModel modeloTablaAdministrarSocios = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * Controlador del menu de Gestion de actividades, gestiona todos los eventos
     * relacionados con esta parte e inicializa las variables
     * @param conexion
     * @param vGestion
     */
    public ControladorActividad(Session conexion, VistaGestionActividades vGestion) {
        this.conexion = conexion;
        this.vAdministrarSocios = vGestion;
        this.vMensaje = new VistaMensaje();
        this.vAdministrarSocios.setVisible(true);

        SocioDAO socios = new SocioDAO(this.conexion);
        ArrayList<Actividad> listaActividades;

        this.vAdministrarSocios.jTableGestionDeActividades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                tblAdministrarSocios_mouseClicked(evt);
            }
        });

        this.vAdministrarSocios.jButtonDarAlta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                btnAlta_mouseClicked(evt);
            }
        });

        this.vAdministrarSocios.jButtonDarBaja.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                btnBaja_mouseClicked(evt);
            }
        });
        this.listaSocios = socios.listarSocios();
        this.dibujarTablaAdministrarSocios();
        this.rellenarTablaAdministrarSocios(listaSocios);
    }

    /**
     * Dibuja la tabla de socios con los campos que queremos mostrar
     * en este caso el numero, el nombre, el codigo de la actividad y el nombre 
     * de la misma
     */
    public void dibujarTablaAdministrarSocios() {
        this.vAdministrarSocios.jTableGestionDeActividades.setModel(modeloTablaAdministrarSocios);
        String[] columnasTabla = {"Numero de Socio", "Nombre de Socio", "Codigo de Actividad", "Nombre de Actividad"};
        modeloTablaAdministrarSocios.setColumnIdentifiers(columnasTabla);
        this.vAdministrarSocios.jTableGestionDeActividades.getTableHeader().setResizingAllowed(false);
        this.vAdministrarSocios.jTableGestionDeActividades.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    /**
     * Método que vacía la tabla de actividades para poder refrescarla más adelante
     */
    public void vaciarTablaActividades() {
        while (modeloTablaAdministrarSocios.getRowCount() > 0) {
            modeloTablaAdministrarSocios.removeRow(0);
        }
    }

    /**
     * Este método rellena la tabla de socios inscritos a una actividad con un array de socios que se le 
     * pasa por parámetro
     * @param Socios
     */
    public void rellenarTablaAdministrarSocios(ArrayList<Socio> Socios) {

        Object[] fila = new Object[4];
        Set<Actividad> actividades;

        for (int i = 0; i < Socios.size(); i++) {
            Socio socio = this.conexion.get(Socio.class, Socios.get(i).getNumerosocio());
            if (!socio.getActividadSet().isEmpty()) {
                actividades = socio.getActividadSet();
                Iterator<Actividad> iteradorActividades = actividades.iterator();

                while (iteradorActividades.hasNext()) {
                    fila[0] = Socios.get(i).getNumerosocio();
                    fila[1] = Socios.get(i).getNombre();
                    Actividad act = iteradorActividades.next();
                    fila[2] = act.getIdactividad();
                    fila[3] = act.getNombre();
                    modeloTablaAdministrarSocios.addRow(fila);
                }
            } else {
                fila[0] = Socios.get(i).getNumerosocio();
                fila[1] = Socios.get(i).getNombre();
                fila[2] = "NO INSCRITO";
                fila[3] = "NO INSCRITO";
                modeloTablaAdministrarSocios.addRow(fila);
            }
        }
    }

    private void tblAdministrarSocios_mouseClicked(MouseEvent evt) {
        int fila = this.vAdministrarSocios.jTableGestionDeActividades.getSelectedRow();

        String seleccionado = "[" + this.vAdministrarSocios.jTableGestionDeActividades.getValueAt(fila, 0)
                + "] " + this.vAdministrarSocios.jTableGestionDeActividades.getValueAt(fila, 1);

        String codigo = (String) this.vAdministrarSocios.jTableGestionDeActividades.getValueAt(fila, 0);
        this.vAdministrarSocios.jTextFieldSeleccionado.setText(seleccionado);
        this.socioSeleccionado = this.conexion.get(Socio.class, codigo);
        this.rellenaComboBox();
    }

    private void btnAlta_mouseClicked(MouseEvent evt) {
        Transaction transaction = this.conexion.beginTransaction();
        try {
            String idAct = (String) this.vAdministrarSocios.jComboBoxActividadesDisponibles.getSelectedItem();
            idAct = idAct.split(" ")[0];
            Actividad actividad = this.conexion.get(Actividad.class, idAct);
            actividad.addSocio(socioSeleccionado);
            this.conexion.save(actividad);
            transaction.commit();

            this.vaciarTablaActividades();
            SocioDAO socios = new SocioDAO(this.conexion);
            this.rellenarTablaAdministrarSocios(socios.listarSocios());
            this.rellenaComboBox();
        } catch (Exception e) {
            vMensaje.MensajeDeError("Ha ocurrido un error", e.getMessage());
            transaction.rollback();
        }
    }

    private void btnBaja_mouseClicked(MouseEvent evt) {
        Transaction transaction = this.conexion.beginTransaction();
        try {
            String idAct = (String) vAdministrarSocios.jComboBoxBaja.getSelectedItem();
            idAct = idAct.split(" ")[0];

            Actividad actividad = this.conexion.get(Actividad.class, idAct);
            actividad.eliminaSocio(socioSeleccionado);
            this.conexion.save(actividad);
            transaction.commit();

            this.vaciarTablaActividades();
            SocioDAO socios = new SocioDAO(this.conexion);
            this.rellenarTablaAdministrarSocios(socios.listarSocios());
            this.rellenaComboBox();
        } catch (Exception e) {
            this.vMensaje.MensajeDeError("Ha ocurrido un error", e.getMessage());
            transaction.rollback();
        }
    }

    private void rellenaComboBox() {
        ActividadDAO act = new ActividadDAO(this.conexion);
        Set<Actividad> actividadesSocio = this.socioSeleccionado.getActividadSet();
        Iterator<Actividad> iteradorActividades = actividadesSocio.iterator();
        ArrayList<Actividad> listaActividades = act.listaActividades();

        this.vAdministrarSocios.jComboBoxActividadesDisponibles.removeAllItems();
        this.vAdministrarSocios.jComboBoxBaja.removeAllItems();

        while (iteradorActividades.hasNext()) {
            Actividad aux = iteradorActividades.next();
            this.vAdministrarSocios.jComboBoxBaja.addItem(aux.getIdactividad() + " " + aux.getNombre());
            listaActividades.remove(aux);
        }

        for (int i = 0; i < listaActividades.size(); i++) {
            this.vAdministrarSocios.jComboBoxActividadesDisponibles.addItem(listaActividades.get(i).getIdactividad() + " " + listaActividades.get(i).getNombre());
        }
    }
}
