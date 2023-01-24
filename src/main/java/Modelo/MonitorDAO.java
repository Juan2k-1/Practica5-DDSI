/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaMensaje;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author juald
 */
public class MonitorDAO {

    private Session sesion;
    private VistaMensaje vMensaje;

    /**
     *
     */
    public MonitorDAO() {
        this.sesion = null;
    }

    /**
     *
     * @param sesion
     */
    public MonitorDAO(Session sesion) {
        this.sesion = sesion;
        this.vMensaje = new VistaMensaje();
    }

    /**
     *
     * @return
     */
    public ArrayList<Monitor> listaMonitores() {
        Transaction transaction = this.sesion.beginTransaction();
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM MONITOR", Monitor.class);
        ArrayList<Monitor> listaMonitores = (ArrayList<Monitor>) consulta.list();
        transaction.commit();
        return listaMonitores;
    }

    /*public ArrayList<Monitor> listaMonitoresPorLetra(String letra) throws SQLException {
        ArrayList listaMonitores = new ArrayList<Monitor>();

        String consulta = "SELECT * FROM MONITOR WHERE nombre LIKE ?";
        this.ps = this.conexion.getConnect().prepareStatement(consulta);

        letra = letra + "%";
        this.ps.setString(1, letra);
        ResultSet rs = this.ps.executeQuery();

        while (rs.next()) {
            Monitor monitor = new Monitor(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7));
            listaMonitores.add(monitor);
        }
        return listaMonitores;
    }*/
    
    /**
     *
     * @param monitor
     */
    public void insertarMonitor(Monitor monitor) {

        Transaction transaction = sesion.beginTransaction();
        try {
            sesion.save(monitor);
            transaction.commit();
        } catch (Exception e) {
            this.vMensaje.MensajeDeError("Error al insertar un monitor", e.getMessage());
            transaction.rollback();
        }
    }

    /**
     *
     * @return @throws Exception
     */
    public String calcularCodigoMonitor() throws Exception {

        String mayor = "";

        int mayorNumeroMonitor = 0;
        ArrayList<Monitor> monitores = this.listaMonitores();

        for (int i = 0; i < monitores.size(); i++) {
            int numMonitor = Integer.parseInt(monitores.get(i).getCodmonitor().split("M")[1]);
            if (mayorNumeroMonitor < numMonitor) {
                mayorNumeroMonitor = numMonitor;
            }
        }

        if (mayorNumeroMonitor <= 99) {
            mayor = "M0" + (mayorNumeroMonitor + 1);
        } else if (mayorNumeroMonitor <= 9) {
            mayor = "M00" + (mayorNumeroMonitor + 1);
        } else {
            mayor = "M" + (mayorNumeroMonitor + 1);
        }
        return mayor;
    }

    /**
     *
     * @param codMonitor
     */
    public void eliminarMonitor(String codMonitor) {
        Transaction transaction = sesion.beginTransaction();
        try {
            Monitor eliminar = this.sesion.get(Monitor.class, codMonitor);
            this.sesion.delete(eliminar);
            transaction.commit();
        } catch (Exception e) {
            this.vMensaje.MensajeDeError("Error al eliminar un monitor", e.getMessage());
            transaction.rollback();
        }
    }

    /**
     *
     * @param update
     */
    public void actualizarMonitor(Monitor update) {
        Transaction transaction = sesion.beginTransaction();
        try {
            sesion.save(update);
            transaction.commit();
        } catch (Exception e) {
            this.vMensaje.MensajeDeError("Error al actualizar un monitor", e.getMessage());
            transaction.rollback();
        }
    }
}
