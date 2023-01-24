/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import com.sun.jdi.IntegerValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author juald
 */
public class ActividadDAO {

    private Session sesion;

    /**
     * Constructor de la clase, inicializa el atributo privado a nulo
     */
    public ActividadDAO() {
        this.sesion = null;
    }

    /**
     * Constructor parametrizado que inicializa el atributo de la clase con el
     * parametro pasado
     *
     * @param sesion
     */
    public ActividadDAO(Session sesion) {
        this.sesion = sesion;
    }

    /**
     * Metodo que selecciona el nombre de todas las actividades y las vuelca en
     * el jComboBox
     *
     * @param cb
     */
    public void devolverActividades(JComboBox cb) {

        Query consulta = this.sesion.createNativeQuery("SELECT NOMBRE FROM ACTIVIDAD");
        cb.removeAllItems();
        Transaction transaction = this.sesion.beginTransaction();

        ArrayList<String> nombreActividades = (ArrayList<String>) consulta.list();
        for (int i = 0; i < nombreActividades.size(); i++) {
            cb.addItem(nombreActividades.get(i));
        }
        transaction.commit();
    }

    /**
     * Metodo que lista toda la informacion de la tabla Actividad y la devuelve
     * en un arrayList
     *
     * @return
     */
    public ArrayList<Actividad> listaActividades() {
        String consult = "SELECT * FROM ACTIVIDAD";

        Transaction transaction = this.sesion.beginTransaction();

        Query consulta = this.sesion.createNativeQuery(consult, Actividad.class);
        ArrayList<Actividad> actividades = (ArrayList<Actividad>) consulta.list();

        transaction.commit();

        return actividades;
    }

    /**
     * Metodo para mostrar los socios inscritos a una actividad mediante una
     * llamada al procedimiento PACTIVIDADESSOCIOS de oracle
     *
     * @param idActividad
     * @param tabla
     */
    public void sociosPorActividad(String idActividad, DefaultTableModel tabla) {

        Transaction transaction = this.sesion.beginTransaction();
        StoredProcedureQuery llamada = this.sesion.createStoredProcedureCall("PACTIVIDADESSOCIOS")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, OracleTypes.class, ParameterMode.REF_CURSOR)
                .setParameter(1, idActividad);
        llamada.execute();
        ArrayList<Object[]> resultado = (ArrayList<Object[]>) llamada.getResultList();
        transaction.commit();

        for (int i = 0; i < resultado.size(); i++) {
            pintaTabla(tabla, (String) resultado.get(i)[0], (String) resultado.get(i)[1]);
        }
    }

    /**
     * Metodo para mostrar los socios inscritos a una actividad mediante una
     * llamada al procedimiento PACTIVIDADESSOCIOS de mariaDB
     *
     * @param idActividad
     * @param tabla
     */
    public void sociosPorActividadMariaDB(String idActividad, DefaultTableModel tabla) {

        Transaction transaction = this.sesion.beginTransaction();
        StoredProcedureQuery llamada = this.sesion.createStoredProcedureCall("PACTIVIDADESSOCIOS")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .setParameter(1, idActividad);
        llamada.execute();
        ArrayList<Object[]> resultado = (ArrayList<Object[]>) llamada.getResultList();
        transaction.commit();

        for (int i = 0; i < resultado.size(); i++) {
            pintaTabla(tabla, (String) resultado.get(i)[0], (String) resultado.get(i)[1]);
        }
    }

    /**
     * Metodo que devuelve la idActividad de la actividad que se pasa como
     * parametro
     *
     * @param NombreActividad
     * @return
     */
    public String getIdActividad(String NombreActividad) {

        Transaction transaction = this.sesion.beginTransaction();
        Query consulta = this.sesion.createNativeQuery("SELECT IDACTIVIDAD FROM ACTIVIDAD WHERE NOMBRE = :nombre");
        consulta.setParameter("nombre", NombreActividad);
        ArrayList<String> idActividades = (ArrayList<String>) consulta.list();
        String idActividad = idActividades.get(0);
        //String idActividad = consulta.getSingleResult();
        transaction.commit();
        return idActividad;
    }

    public void getIdActividades(String idActividad, DefaultTableModel tabla) {
        Transaction transaction = this.sesion.beginTransaction();
        Query consulta = this.sesion.createNativeQuery("SELECT S.NOMBRE, S.CATEGORIA FROM SOCIO S INNER JOIN REALIZA ACT ON S.NUMEROSOCIO = ACT.NUMEROSOCIO WHERE ACT.IDACTIVIDAD LIKE ?");
        consulta.setParameter(1, idActividad);
        ArrayList<Object[]> socios = (ArrayList<Object[]>) consulta.getResultList();
        transaction.commit();
        
        for (int i = 0; i < socios.size(); i++) {
            rellenarTablaCuotas(tabla, (String) socios.get(i)[0], (String) socios.get(i)[1].toString());
        }
    }
    
    public int getPrecio(String idactividad) {
        Transaction transaction = this.sesion.beginTransaction();
        Query consulta = this.sesion.createNativeQuery("SELECT A.PRECIOBASEMES FROM ACTIVIDAD A WHERE A.IDACTIVIDAD LIKE ?");
        consulta.setParameter(1, idactividad);
        BigDecimal precio = (BigDecimal) consulta.getSingleResult();
        int precio2 = precio.intValue();
        transaction.commit();
        return precio2;
    }

    /**
     * Metodo que pinta la tabla de los socios inscritos con el diseño que
     * queremos
     *
     * @param Tabla
     * @param Nombre
     * @param Correo
     */
    public void pintaTabla(DefaultTableModel Tabla, String Nombre, String Correo) {
        Object[] fila = new Object[2];
        fila[0] = Nombre;
        fila[1] = Correo;
        Tabla.addRow(fila);
    }

    public void rellenarTablaCuotas(DefaultTableModel tabla, String nombre, String Categoria) {
        Object[] fila = new Object[2];
        fila[0] = nombre;
        fila[1] = Categoria;
        tabla.addRow(fila);
    }
}
