/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.MonitorTablas;
import Vista.VistaActualizacionMonitor;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaNuevoMonitor;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author juald
 */
public class ControladorMonitor implements ActionListener {

    private VistaMensaje vMensaje = null;
    private VistaMonitor vMonitor = null;
    private VistaNuevoMonitor vNuevoMonitor = null;
    private VistaActualizacionMonitor vActualizar = null;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private Session sesion;
    private ArrayList<Monitor> ListaMonitores;
    private MonitorTablas mTablas;

    /**
     * Este metodo es el constructor de la clase Controlador principal Se
     * encarga de inicializar los atributos y de crear las vistas de los
     * mensajes relacionados con la ventana principal y la propia ventana
     * principal
     *
     * @param vMonitor
     * @param sesion
     * @param mTablas
     * @throws java.lang.Exception
     */
    public ControladorMonitor(VistaMonitor vMonitor, Session sesion, MonitorTablas mTablas) throws Exception {

        this.vMensaje = new VistaMensaje();
        this.vNuevoMonitor = new VistaNuevoMonitor();
        this.vActualizar = new VistaActualizacionMonitor();
        this.vMonitor = vMonitor;
        this.sesion = sesion;
        this.mTablas = mTablas;

        MonitorDAO miMonitor = new MonitorDAO(sesion);
        String codMonitor = "";
        try {
            codMonitor = miMonitor.calcularCodigoMonitor();
        } catch (Exception ex) {
            Logger.getLogger(ControladorSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.vNuevoMonitor.jTextFieldCodigo.setText(codMonitor);

        addListeners();
    }

    /**
     * Este método esta a la escucha de los posibles que se pueden en nuestra
     * ventana En este caso simplemente está a la escucha de que pulsemos el
     * botón de salir de la ventana
     */
    private void addListeners() {
        vMonitor.jButtonAddMonitor.addActionListener(this);
        vMonitor.jButtonDeleteMonitor.addActionListener(this);
        vMonitor.jButtonUpdateMonitor.addActionListener(this);
        vNuevoMonitor.jButtonInsertarNuevoMonitor.addActionListener(this);
        vNuevoMonitor.jButtonCancelar.addActionListener(this);
        vActualizar.jButtonActualizar.addActionListener(this);
        vActualizar.jButtonCancelar.addActionListener(this);
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
            case "NuevoMonitor": {
                this.vNuevoMonitor.setLocationRelativeTo(null);
                this.vNuevoMonitor.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vNuevoMonitor.setVisible(true);
                this.vNuevoMonitor.jTextFieldCodigo.setEditable(false); // A veces no detecta el comando y hay que hacerlo en el asistente de interfaces
                break;
            }
            case "BajaMonitor": {
                this.eliminarMonitor();
                break;
            }
            case "ActualizacionMonitor": {
                this.vActualizar.setLocationRelativeTo(null);
                this.vActualizar.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vActualizar.setVisible(true);
                this.vActualizar.dispose();
                break;
            }
            case "Insertar": {
                this.insertarNuevoMonitor();
                vNuevoMonitor.dispose();
                break;
            }
            case "Cancelar": {
                vNuevoMonitor.dispose();
                break;
            }
            case "Cancelar2": {
                vActualizar.dispose();
                break;
            }
            case "Actualizar": {
                this.updateMonitor();
                vNuevoMonitor.dispose();
                break;
            }
        }
    }

    /**
     * Inserta un nuevo monitor en la base de datos, introduciendo los valores de los campos necesarios y mostrandolo en la tabla de monitores
     */
    public void insertarNuevoMonitor() {
        try {
            MonitorDAO miMonitor = new MonitorDAO(this.sesion);

            String codMonitor = miMonitor.calcularCodigoMonitor();
            this.vNuevoMonitor.jTextFieldCodigo.setText(codMonitor);
            String nombre = vNuevoMonitor.jTextFieldNombre.getText();
            String dni = vNuevoMonitor.jTextFieldDNI.getText();
            String telefono = vNuevoMonitor.jTextFieldTelefono.getText();
            String correo = vNuevoMonitor.jTextFieldCorreo.getText();
            String nick = vNuevoMonitor.jTextFieldNick.getText();

            Date fechaChooser = vNuevoMonitor.jDateChooserFechaEntrada.getDate();
            String fechaEntrada = "";
            if (fechaChooser != null) {
                fechaEntrada = formatoFecha.format(fechaChooser);
            }

            Monitor monitor = new Monitor(codMonitor, nombre, dni, telefono, correo, fechaEntrada, nick);
            boolean validarDni = validarDNI(dni);
            boolean validarTlf = validarTlf(telefono);
            boolean validarFechaEntrada = validarFechaEntrada(fechaChooser);
            if (!validarDni) {
                this.vMensaje.MensajeDeError("Error al introducir el DNI");
            } else if (!validarTlf) {
                this.vMensaje.MensajeDeError("Error al introducir el numero de telefono");
            } else if (!validarFechaEntrada) {
                this.vMensaje.MensajeDeError("Error, la fecha de entrada ha de ser anterior a la fecha actual ");
            } else if (miMonitor.listaMonitores().contains(monitor)) {
                this.vMensaje.MensajeDeError("Error, el monitor introducido ya existe en la BD");
            } else {
                miMonitor.insertarMonitor(monitor);
                mTablas.vaciarTablaMonitores();
                mTablas.rellenarTablaMonitores(miMonitor.listaMonitores());
            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Elimina de la base de datos al monitor seleccionado de la lista de monitores de la tabla de monitores
     */
    public void eliminarMonitor() {
        try {
            MonitorDAO monitorDAO = new MonitorDAO(this.sesion);

            int fila = vMonitor.TablaMonitor.getSelectedRow();
            this.ListaMonitores = monitorDAO.listaMonitores();
            Monitor seleccionado = this.ListaMonitores.get(fila);

            String codMonitor = seleccionado.getCodmonitor();
            /*String nombre = seleccionado.getNombre();
            String dni = seleccionado.getDni();
            String telefono = seleccionado.getTelefono();
            String correo = seleccionado.getCorreo();
            String nick = seleccionado.getNick();
            String fechaEntrada = seleccionado.getFechaentrada();*/

            int opcion = JOptionPane.showConfirmDialog(null, "¿Desea eliminarlo?");
            System.out.println("Opcion: " + opcion);

            if (opcion == 0) {
                try {
                    //eliminar = new Monitor(codMonitor, nombre, dni, telefono, correo, fechaEntrada, nick);
                    //MonitorDAO miMonitor = new MonitorDAO(this.sesion);
                    monitorDAO.eliminarMonitor(codMonitor);

                    ListaMonitores = monitorDAO.listaMonitores();
                    mTablas.vaciarTablaMonitores();
                    mTablas.rellenarTablaMonitores(ListaMonitores);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualiza la información del monitor seleccionado de la lista de monitores mostrada en la tabla de monitores
     */
    public void updateMonitor() {
        MonitorDAO monitorDAO = new MonitorDAO(this.sesion);
        try {
            int fila = vMonitor.TablaMonitor.getSelectedRow();
            this.ListaMonitores = monitorDAO.listaMonitores();
            Monitor seleccionado = this.ListaMonitores.get(fila);

            String codMonitor = seleccionado.getCodmonitor();
            String nombre = vActualizar.jTextFieldNombre.getText();
            String dni = vActualizar.jTextFieldDNI.getText();
            String telefono = vActualizar.jTextFieldTelefono.getText();
            String correo = vActualizar.jTextFieldCorreo.getText();
            String nick = vActualizar.jTextFieldNick.getText();

            Date fechaChooser = vActualizar.jDateChooserFechaEntrada.getDate();
            String fechaEntrada = "";
            if (fechaChooser != null) {
                fechaEntrada = formatoFecha.format(fechaChooser);
            }
            boolean validarDni = validarDNI(dni);
            boolean validarTlf = validarTlf(telefono);
            boolean validarFechaEntrada = validarFechaEntrada(fechaChooser);
            if (!validarDni) {
                this.vMensaje.MensajeDeError("Error al introducir el DNI");
            } else if (!validarTlf) {
                this.vMensaje.MensajeDeError("Error al introducir el numero de telefono");
            } else if (!validarFechaEntrada) {
                this.vMensaje.MensajeDeError("Error, la fecha de entrada ha de ser anterior a la fecha actual ");
            } else {
                seleccionado.setCodmonitor(codMonitor);
                seleccionado.setNombre(nombre);
                seleccionado.setDni(dni);
                seleccionado.setTelefono(telefono);
                seleccionado.setCorreo(correo);
                seleccionado.setFechaentrada(fechaEntrada);
                seleccionado.setNick(nick);

                monitorDAO.actualizarMonitor(seleccionado);
                this.mTablas.vaciarTablaMonitores();
                ListaMonitores = monitorDAO.listaMonitores();
                this.mTablas.rellenarTablaMonitores(ListaMonitores);
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método nos sirve para validar los dni introducidos en la app
     * Usa el método matches de la clase String, el cual permite la entrada de un parámetro de tipo regex de la clase Pattern
     * Regex es la expresion regular que queremos buscar y comprobar
     * Devuelve true o false si, y solo si, la cadena concuerda con la expresion regular dada
     * @param dni
     * @return
     */
    public boolean validarDNI(String dni) {
        return dni.matches("^[0-9]{7,8}[T|R|W|A|G|M|Y|F|P|D|X|B|N|J|Z|S|Q|V|H|L|C|K|E]$");
    }

    /**
     * Este método nos sirve para validar los telefonos introducidos en la app
     * Usa el método matches de la clase String, el cual permite la entrada de un parámetro de tipo regex de la clase Pattern
     * Regex es la expresion regular que queremos buscar y comprobar
     * Devuelve true o false si, y solo si, la cadena concuerda con la expresion regular dada
     * @param tlf
     * @return
     */
    public boolean validarTlf(String tlf) {
        return tlf.matches("^[0-9]{9}$");
    }

    /**
     * Este método nos sirve para validar la fecha de entrada introducida en la app
     * Usa el método matches de la clase String, el cual permite la entrada de un parámetro de tipo regex de la clase Pattern
     * Regex es la expresion regular que queremos buscar y comprobar
     * Devuelve true o false si, y solo si, la cadena concuerda con la expresion regular dada
     * @param fechaEntrada
     * @return
     */
    public boolean validarFechaEntrada(Date fechaEntrada) {
        return fechaEntrada.before(Date.from(Instant.now()));
    }
}
