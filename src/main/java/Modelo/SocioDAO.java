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
public class SocioDAO {

    private Session sesion;
    private VistaMensaje vMensaje;

    /**
     *
     */
    public SocioDAO() {
        this.sesion = null;
    }

    /**
     *
     * @param sesion
     */
    public SocioDAO(Session sesion) {
        this.sesion = sesion;
        this.vMensaje = new VistaMensaje();
    }

    /**
     *
     * @return
     */
    public ArrayList<Socio> listarSocios() {
        Transaction transaction = this.sesion.beginTransaction();
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO", Socio.class);
        ArrayList<Socio> listaSocios = (ArrayList<Socio>) consulta.list();
        transaction.commit();
        return listaSocios;
    }

    /*
    public ArrayList<Socio> listaSocioPorLetra(String letra) throws SQLException {

        ArrayList<Socio> listarSocios = new ArrayList<Socio>();

        String consulta = "SELECT * FROM SOCIO WHERE nombre LIKE ?";
        ps = conexion.getConnect().prepareStatement(consulta);

        letra = letra + "%";
        ps.setString(1, letra);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Socio socio = new Socio(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                    rs.getString(8));
            listarSocios.add(socio);
        }
        return listarSocios;
    }*/
    /**
     *
     * @param socio
     */
    public void insertarSocio(Socio socio) {
        Transaction transaction = this.sesion.beginTransaction();
        try {
            this.sesion.save(socio);
            transaction.commit();
        } catch (Exception e) {
            this.vMensaje.MensajeDeError("Error al insertar un socio", e.getMessage());
            transaction.rollback();
        }
    }

    /**
     *
     * @return
     */
    public String calcularCodigoSocio() {

        String mayor = "";

        int mayorNumeroSocio = 0;
        ArrayList<Socio> socios = this.listarSocios();

        for (int i = 0; i < socios.size(); i++) {
            int numSocio = Integer.parseInt(socios.get(i).getNumerosocio().split("S")[1]);
            if (mayorNumeroSocio < numSocio) {
                mayorNumeroSocio = numSocio;
            }
        }

        if (mayorNumeroSocio <= 99) {
            mayor = "S0" + (mayorNumeroSocio + 1);
        } else if (mayorNumeroSocio <= 9) {
            mayor = "S00" + (mayorNumeroSocio + 1);
        } else {
            mayor = "S" + (mayorNumeroSocio + 1);
        }
        return mayor;
    }

    /**
     *
     * @param numSocio
     */
    public void eliminarSocio(String numSocio) {
        Transaction transaction = this.sesion.beginTransaction();
        try {
            Socio eliminar = this.sesion.get(Socio.class, numSocio);
            this.sesion.delete(eliminar);
            transaction.commit();
        } catch (Exception e) {
            this.vMensaje.MensajeDeError("Error al eliminar un socio", e.getMessage());
            transaction.rollback();
        }
    }

    /**
     *
     * @param update
     */
    public void actualizarSocio(Socio update) {
        Transaction transaction = this.sesion.beginTransaction();
        try {
            this.sesion.save(update);
            transaction.commit();
        } catch (Exception e) {
            this.vMensaje.MensajeDeError("Error al actualizar un socio", e.getMessage());
            transaction.rollback();
        }
    }
    
    /**
     *
     * @param numSocio
     * @return
     */
    public Socio filtrarPorNumero(String numSocio) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE NUMEROSOCIO = :numsocio", Socio.class);
        consulta.setParameter("numsocio", numSocio);
        Transaction transaction = this.sesion.beginTransaction();  
        Socio socio = (Socio) consulta.getSingleResult();
        transaction.commit();
        return socio;
    }
    
    /**
     *
     * @param nombre
     * @return
     */
    public ArrayList<Socio> filtrarPorNombre(String nombre) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE NOMBRE LIKE ?", Socio.class);
        consulta.setParameter(1, nombre + "%");
        Transaction transaction = this.sesion.beginTransaction();       
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.list();
        transaction.commit();
        return socios;
    }
    
    /**
     *
     * @param DNI
     * @return
     */
    public Socio filtrarPorDNI(String DNI) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE DNI = :DNI", Socio.class);
        consulta.setParameter("DNI", DNI);
        Transaction transaction = this.sesion.beginTransaction();  
        Socio socio = (Socio) consulta.getSingleResult();
        transaction.commit();
        return socio;
    }
    
    /**
     *
     * @param fecha
     * @return
     */
    public ArrayList<Socio> filtrarPorFechaNac(String fecha) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE FECHANACIMIENTO LIKE ?", Socio.class);
        consulta.setParameter(1, fecha);
        Transaction transaction = this.sesion.beginTransaction();       
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.getResultList();
        transaction.commit();
        return socios;
    }
    
    /**
     *
     * @param fecha
     * @return
     */
    public ArrayList<Socio> filtrarPorFechaEnt(String fecha) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE FECHAENTRADA LIKE ?", Socio.class);
        consulta.setParameter(1, fecha);
        Transaction transaction = this.sesion.beginTransaction();       
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.getResultList();
        transaction.commit();
        return socios;
    }
    
    /**
     *
     * @param telefono
     * @return
     */
    public Socio filtrarPorTelefono(String telefono) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE TELEFONO = ?", Socio.class);
        consulta.setParameter(1, telefono);
        Transaction transaction = this.sesion.beginTransaction();  
        Socio socio = (Socio) consulta.getSingleResult();
        transaction.commit();
        return socio;
    }
    
    /**
     *
     * @param correo
     * @return
     */
    public ArrayList<Socio> filtrarPorCorreo(String correo) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE CORREO LIKE ?", Socio.class);
        consulta.setParameter(1, correo + "%");
        Transaction transaction = this.sesion.beginTransaction();       
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.list();
        transaction.commit();
        return socios;
    }
    
    /**
     *
     * @param categoria
     * @return
     */
    public ArrayList<Socio> filtrarPorCategoria(String categoria) {
        Query consulta = this.sesion.createNativeQuery("SELECT * FROM SOCIO WHERE CATEGORIA LIKE ?", Socio.class);
        consulta.setParameter(1, categoria);
        Transaction transaction = this.sesion.beginTransaction();       
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.list();
        transaction.commit();
        return socios;
    }
}
