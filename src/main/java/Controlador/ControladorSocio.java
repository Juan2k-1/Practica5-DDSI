/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Modelo.SocioDAO;
import Modelo.SocioTablas;
import Vista.VistaActualizacionSocio;
import Vista.VistaMensaje;
import Vista.VistaNuevoSocio;
import Vista.VistaSocio;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author juald
 */
public class ControladorSocio implements ActionListener {

    private VistaMensaje vMensaje = null;
    private VistaSocio vSocio = null;
    private VistaNuevoSocio vNuevoSocio = null;
    private VistaActualizacionSocio vActualizar = null;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private Session sesion;
    private ArrayList<Socio> ListaSocios;
    private SocioTablas sTablas;

    /**
     * Este metodo es el constructor de la clase Controlador principal Se
     * encarga de inicializar los atributos y de crear las vistas de los
     * mensajes relacionados con la ventana principal y la propia ventana
     * principal
     *
     * @param vSocio
     * @param sesion
     * @param sTablas
     */
    public ControladorSocio(VistaSocio vSocio, Session sesion, SocioTablas sTablas) {

        this.vMensaje = new VistaMensaje();
        this.vNuevoSocio = new VistaNuevoSocio();
        this.vActualizar = new VistaActualizacionSocio();
        this.vSocio = vSocio;
        this.sesion = sesion;
        this.sTablas = sTablas;

        SocioDAO miSocio = new SocioDAO(sesion);

        String NumeroSocio = "";
        NumeroSocio = miSocio.calcularCodigoSocio();
        this.vNuevoSocio.jTextFieldCodigo.setText(NumeroSocio);
        addListeners();
    }

    /**
     * Este método esta a la escucha de los posibles que se pueden en nuestra
     * ventana En este caso simplemente está a la escucha de que pulsemos el
     * botón de salir de la ventana
     */
    private void addListeners() {
        this.vSocio.jButtonAddSocio.addActionListener(this);
        this.vSocio.jButtonDeleteSocio.addActionListener(this);
        this.vSocio.jButtonUpdateSocio.addActionListener(this);
        this.vSocio.jComboBoxBusqueda.addActionListener(this);
        this.vSocio.jButtonBuscar.addActionListener(this);
        this.vNuevoSocio.jButtonInsertarNuevoSocio.addActionListener(this);
        this.vNuevoSocio.jButtonCancelar.addActionListener(this);
        this.vActualizar.jButtonActualizar.addActionListener(this);
        this.vActualizar.jButtonCancelar.addActionListener(this);
    }

    /**
     * Este método es el encargado de llevar a cabo acciones diferentes según el
     * evento que trate En este caso solo trata el evento de pulsar el botón de
     * cerrar la ventana principal recogido por el addListeners()
     *
     * @param e El parametro e representa el evento que queremos tratar en el
     * método
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "NuevoSocio": {
                this.vNuevoSocio.setLocationRelativeTo(null);
                this.vNuevoSocio.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vNuevoSocio.setVisible(true);
                //this.vNuevoSocio.jTextFieldCodigo.setEnabled(false);
                break;
            }

            case "BajaSocio": {
                this.eliminarSocio();
                break;
            }
            case "ActualizacionSocio": {
                this.vActualizar.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vActualizar.setSize(550, 270);
                this.vActualizar.setLocationRelativeTo(null);
                this.vActualizar.setVisible(true);
                this.vActualizar.dispose();
                break;
            }
            case "Insertar": {
                this.insertarNuevoSocio();
                vNuevoSocio.dispose();
                break;
            }
            case "Cancelar": {
                vNuevoSocio.dispose();
                break;
            }
            case "Cancelar2": {
                vActualizar.dispose();
                break;
            }
            case "Actualizar": {
                this.updateSocio();
                //vNuevoSocio.dispose();
                break;
            }
            case "Buscar": {
                busqueda();
                break;
            }
        }
    }

    /**
     * Metodo creado para organizar la busqueda de los socios según el filtro 
     * seleccionado por el usuario
     */
    public void busqueda() {
        String busqueda = (String) this.vSocio.jComboBoxBusqueda.getSelectedItem();
        if (busqueda.equals("NumeroSocio")) {
            buscarPorNumero();
        } else if (busqueda.equals("Nombre")) {
            buscarPorNombre();
        } else if (busqueda.equals("DNI")) {
            buscarPorDNI();
        } else if (busqueda.equals("FechaNacimiento")) {
            buscarPorFechaNac();
        } else if (busqueda.equals("FechaEntrada")) {
            buscarPorFechaEnt();
        } else if (busqueda.equals("Teléfono")) {
            buscarPorTelefono();
        } else if (busqueda.equals("Correo")) {
            buscarPorCorreo();
        } else {
            buscarPorCategoria();
        }
    }

    /**
     * Metodo para buscar los socios cuyo numero socio se introduzca en el jTextField
     */
    public void buscarPorNumero() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        String busqueda = this.vSocio.jTextFieldBusqueda.getText();
        Socio socio = miSocio.filtrarPorNumero(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocioUnico(socio);
    }

    /**
     * Metodo que busca los socios cuyo nombre se introduzca en el jTextField
     */
    public void buscarPorNombre() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        String busqueda = this.vSocio.jTextFieldBusqueda.getText();
        ArrayList<Socio> socios = miSocio.filtrarPorNombre(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocios(socios);
    }

    /**
     * Metodo que busca al socio cuyo DNI se introduzca en el jTextField
     */
    public void buscarPorDNI() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        String busqueda = this.vSocio.jTextFieldBusqueda.getText();
        Socio socio = miSocio.filtrarPorDNI(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocioUnico(socio);
    }

    /**
     * Metodo que busca los socios cuya fecha de nacimiento se introduzca en el jTextField
     */
    public void buscarPorFechaNac() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        Date fecha = this.vSocio.jDateChooserBusqueda.getDate();
        String busqueda = formatoFecha.format(fecha);
        System.out.println(busqueda);
        ArrayList<Socio> socios = miSocio.filtrarPorFechaNac(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocios(socios);
    }
    
    /**
     * Metodo que busca los socios cuya fecha de entrada se introduzca en el jTextField
     */
    public void buscarPorFechaEnt() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        Date fecha = this.vSocio.jDateChooserBusqueda.getDate();
        String busqueda = formatoFecha.format(fecha);
        ArrayList<Socio> socios = miSocio.filtrarPorFechaEnt(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocios(socios);
    }
    
    /**
     * Metodo que busca al socio cuyo telefono se introduzca en el jTextField
     */
    public void buscarPorTelefono() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        String busqueda = this.vSocio.jTextFieldBusqueda.getText();
        Socio socio = miSocio.filtrarPorTelefono(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocioUnico(socio);
    }
    
    /**
     * Metodo que busca los socios cuyo correo se introduzca en el jTextField
     */
    public void buscarPorCorreo() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        String busqueda = this.vSocio.jTextFieldBusqueda.getText();
        ArrayList<Socio> socios = miSocio.filtrarPorCorreo(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocios(socios);
    }
    
    /**
     * Metodo que busca los socios cuya categoria se introduzca en el jTextField
     */
    public void buscarPorCategoria() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        String busqueda = this.vSocio.jTextFieldBusqueda.getText().toUpperCase();
        ArrayList<Socio> socios = miSocio.filtrarPorCategoria(busqueda);
        this.sTablas.vaciarTablaSocios();
        this.sTablas.rellenarTablaSocios(socios);
    }

    /**
     * Metodo que inserta un nuevo socio en la base de datos
     * comprobando si los parámetros introducidos son correctos o no
     */
    public void insertarNuevoSocio() {
        SocioDAO miSocio = new SocioDAO(this.sesion);

        String NumeroSocio = miSocio.calcularCodigoSocio();
        this.vNuevoSocio.jTextFieldCodigo.setText(NumeroSocio);

        String nombre = vNuevoSocio.jTextFieldNombre.getText();
        String dni = vNuevoSocio.jTextFieldDNI.getText();
        Date fechaChooser = vNuevoSocio.jDateChooserFechaNacimiento.getDate();
        String fechaNacimiento = "";
        if (fechaChooser != null) {
            fechaNacimiento = formatoFecha.format(fechaChooser);
        }
        String telefono = vNuevoSocio.jTextFieldTelefono.getText();
        String correo = vNuevoSocio.jTextFieldCorreo.getText();
        char categoria = vNuevoSocio.jTextFieldCategoria.getText().charAt(0);
        Date fechaChooser2 = vNuevoSocio.jDateChooserFechaEntrada.getDate();
        String fechaEntrada = "";
        if (fechaChooser2 != null) {
            fechaEntrada = formatoFecha.format(fechaChooser2);
        }

        Socio socio = new Socio(NumeroSocio, nombre, dni, fechaNacimiento, telefono, correo, fechaEntrada, categoria);
        boolean validarDni = validarDNI(dni);
        boolean validarTlf = validarTlf(telefono);
        boolean validarFechaEntrada = validarFechaEntrada(fechaChooser2, fechaChooser);
        boolean validarFechaNacimiento = validarFechaNacimiento(fechaChooser);
        if (!validarDni) {
            this.vMensaje.MensajeDeError("Error al introducir el DNI");
        } else if (!validarTlf) {
            this.vMensaje.MensajeDeError("Error al introducir el numero de telefono");
        } else if (!validarFechaEntrada) {
            this.vMensaje.MensajeDeError("Error, la fecha de entrada ha de ser posterior a la fecha de nacimiento");
        } else if (!validarFechaNacimiento) {
            this.vMensaje.MensajeDeError("Error, la fecha de nacimiento es posterior a la fecha actual");
        } else if (miSocio.listarSocios().contains(socio)) {
            this.vMensaje.MensajeDeError("Error, el socio introducido ya existe en la BD");
        } else {
            miSocio.insertarSocio(socio);
            this.sTablas.vaciarTablaSocios();
            this.sTablas.rellenarTablaSocios(miSocio.listarSocios());
        }
    }

    /**
     * Metodo que elimina un socio de la base de datos
     */
    public void eliminarSocio() {
        SocioDAO miSocio = new SocioDAO(this.sesion);

        int fila = vSocio.TablaSocio.getSelectedRow();
        this.ListaSocios = miSocio.listarSocios();
        Socio seleccionado = this.ListaSocios.get(fila);

        String numeroSocio = seleccionado.getNumerosocio();
        /*String nombre = seleccionado.getNombre();
        String dni = seleccionado.getDni();
        String fechaNacimiento = seleccionado.getFechanacimiento();
        String telefono = seleccionado.getTelefono();
        String correo = seleccionado.getCorreo();
        char categoria = seleccionado.getCategoria();
        String fechaEntrada = seleccionado.getFechaentrada();*/

        int opcion = JOptionPane.showConfirmDialog(null, "¿Desea eliminarlo?");
        System.out.println("Opcion: " + opcion);
        if (opcion == 0) {
            //eliminar = new Socio(numeroSocio, nombre, dni, fechaNacimiento, telefono, correo, fechaEntrada, categoria);
            miSocio.eliminarSocio(numeroSocio);
            this.sTablas.vaciarTablaSocios();
            this.ListaSocios = miSocio.listarSocios();
            this.sTablas.rellenarTablaSocios(ListaSocios);
        }
    }

    /**
     * Actualiza la información del socio seleccionado
     * y comprueba que si los parámetros introducidos son correctos
     */
    public void updateSocio() {
        SocioDAO miSocio = new SocioDAO(this.sesion);
        try {
            int fila = vSocio.TablaSocio.getSelectedRow();
            this.ListaSocios = miSocio.listarSocios();
            Socio seleccionado = this.ListaSocios.get(fila);

            String numeroSocio = seleccionado.getNumerosocio();
            String nombre = vActualizar.jTextFieldNombre.getText();
            String dni = vActualizar.jTextFieldDNI.getText();
            Date fechaChooser = vActualizar.jDateChooserFechaNacimiento.getDate();
            String fechaNacimiento = "";
            if (fechaChooser != null) {
                fechaNacimiento = formatoFecha.format(fechaChooser);
            }

            String telefono = vActualizar.jTextFieldTelefono.getText();
            String correo = vActualizar.jTextFieldCorreo.getText();
            char categoria = vActualizar.jTextFieldCategoria.getText().toCharArray()[0];
            Date fechaChooser2 = vActualizar.jDateChooserFechaEntrada.getDate();
            String fechaEntrada = "";
            if (fechaChooser2 != null) {
                fechaEntrada = formatoFecha.format(fechaChooser2);
            }

            boolean validarDni = validarDNI(dni);
            boolean validarTlf = validarTlf(telefono);
            boolean validarFechaEntrada = validarFechaEntrada(fechaChooser2, fechaChooser);
            boolean validarFechaNacimiento = validarFechaNacimiento(fechaChooser);
            if (!validarDni) {
                this.vMensaje.MensajeDeError("Error al introducir el DNI");
            } else if (!validarTlf) {
                this.vMensaje.MensajeDeError("Error al introducir el numero de teléfono");
            } else if (!validarFechaEntrada) {
                this.vMensaje.MensajeDeError("Error, la fecha de entrada ha de ser posterior a la fecha de nacimiento");
            } else if (!validarFechaNacimiento) {
                this.vMensaje.MensajeDeError("Error, la fecha de nacimiento es posterior a la fecha actual");
            } else {
                seleccionado.setNumerosocio(numeroSocio);
                seleccionado.setNombre(nombre);
                seleccionado.setDni(dni);
                seleccionado.setFechanacimiento(fechaNacimiento);
                seleccionado.setTelefono(telefono);
                seleccionado.setCorreo(correo);
                seleccionado.setFechaentrada(fechaEntrada);
                seleccionado.setCategoria(categoria);

                miSocio.actualizarSocio(seleccionado);
                this.sTablas.vaciarTablaSocios();
                ListaSocios = miSocio.listarSocios();
                this.sTablas.rellenarTablaSocios(ListaSocios);
            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para comprobar si los DNIs introducidos son correctos
     * Hace uso del método matches de la clase String que usa la clase Pattern
     * para buscar coincidencias entre la cadena introducida y el patron que deseamos comparar
     * @param dni
     * @return
     */
    public boolean validarDNI(String dni) {
        return dni.matches("^[0-9]{7,8}[T|R|W|A|G|M|Y|F|P|D|X|B|N|J|Z|S|Q|V|H|L|C|K|E]$");
    }

    /**
     * Metodo para comprobar si los telefonos introducidos son correctos
     * Hace uso del método matches de la clase String que usa la clase Pattern
     * para buscar coincidencias entre la cadena introducida y el patron que deseamos comparar
     * @param tlf
     * @return
     */
    public boolean validarTlf(String tlf) {
        return tlf.matches("^[0-9]{9}$");
    }

    /**
     * Metodo para comprobar si la fecha de entrada introducidas son correctas
     * Hace uso del método matches de la clase String que usa la clase Pattern
     * para buscar coincidencias entre la cadena introducida y el patron que deseamos comparar
     * @param fechaEntrada
     * @param fechaNacimiento
     * @return
     */
    public boolean validarFechaEntrada(Date fechaEntrada, Date fechaNacimiento) {
        return fechaEntrada.after(fechaNacimiento);
    }

    /**
     * Metodo para comprobar si las fechas de nacimiento introducidas son correctas
     * Hace uso del método matches de la clase String que usa la clase Pattern
     * para buscar coincidencias entre la cadena introducida y el patron que deseamos comparar
     * @param fechaNacimiento
     * @return
     */
    public boolean validarFechaNacimiento(Date fechaNacimiento) {
        return fechaNacimiento.before(Date.from(Instant.now()));
    }
}
