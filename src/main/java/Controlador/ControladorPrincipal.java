/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.MonitorTablas;
import Modelo.Socio;
import Modelo.SocioDAO;
import Modelo.SocioTablas;
import Vista.VistaActividades;
import Vista.VistaCuotaActividad;
import Vista.VistaGestionActividades;
import Vista.VistaLogin;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaPorDefecto;
import Vista.VistaPrincipal;
import Vista.VistaSocio;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;

/**
 *
 * @author juald
 */
public class ControladorPrincipal implements ActionListener {

    private Session sesion;
    private VistaLogin vLogin = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private VistaMonitor vMonitor = null;
    private VistaSocio vSocio;
    private VistaPorDefecto vPorDefecto;
    private VistaActividades vActividad;
    private VistaCuotaActividad vCuota;
    private VistaGestionActividades vGestionActividad;
    private ControladorMonitor controladorMonitor;
    private ControladorSocio controladorSocio;
    private ControladorActividad controladorActividad;
    private MonitorTablas mTablas;
    private SocioTablas socioTablas;
    private MonitorDAO mDAO;
    private SocioDAO sDAO;
    private ArrayList<Socio> listaSocios;

    /**
     * Este metodo es el constructor de la clase Controlador principal Se
     * encarga de inicializar los atributos y de crear las vistas de los
     * mensajes relacionados con la ventana principal y la propia ventana
     * principal
     *
     * @param sesion
     * @param vLogin
     */
    public ControladorPrincipal(Session sesion, VistaLogin vLogin) {
        this.sesion = sesion;
        this.vMensaje = new VistaMensaje();
        this.vPrincipal = new VistaPrincipal();
        this.vMonitor = new VistaMonitor();
        this.vSocio = new VistaSocio();
        this.vPorDefecto = new VistaPorDefecto();
        this.vLogin = vLogin;
        this.mDAO = new MonitorDAO(this.sesion);
        this.sDAO = new SocioDAO(this.sesion);
        this.mTablas = new MonitorTablas(vMonitor);
        this.socioTablas = new SocioTablas(vSocio);
        this.vActividad = new VistaActividades();
        this.vGestionActividad = new VistaGestionActividades();
        this.vCuota = new VistaCuotaActividad();

        addListeners();

        vPrincipal.setLayout(new CardLayout());

        vPrincipal.add(vPorDefecto);
        vPrincipal.add(vMonitor);
        vPrincipal.add(vSocio);
        vPrincipal.add(vGestionActividad);

        vPorDefecto.setVisible(true);
        vMonitor.setVisible(false);
        vSocio.setVisible(false);
        vActividad.setVisible(false);
        vGestionActividad.setVisible(false);
        vCuota.setVisible(false);

        vPrincipal.setLocationRelativeTo(null); //Para que la ventana se muestre en el centro de la pantalla
        vPrincipal.setVisible(true); // Para hacer la ventana visible
        vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Para que la ventana se cierra cuando le doy a cerrar

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
            case "CerrarVentanaPrincipal": {
                this.sesion.close();
                vMensaje.MensajeInformacion("Cerrando la aplicación");
                vPrincipal.dispose();
                System.exit(0);
                break;
            }

            case "GestionDeMonitores": {
                try {
                    controladorMonitor = new ControladorMonitor(this.vMonitor, this.sesion, this.mTablas);

                    this.vPorDefecto.setVisible(false);
                    this.vSocio.setVisible(false);
                    this.vActividad.setVisible(false);
                    this.vGestionActividad.setVisible(false);
                    this.vMonitor.setVisible(true);
                    this.vMonitor.TablaMonitor.setModel(mTablas);
                    DiseñoTablaMonitor();

                    pideMonitores();

                    break;
                } catch (Exception ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            case "GestionDeSocios": {
                controladorSocio = new ControladorSocio(this.vSocio, this.sesion, this.socioTablas);
                this.vPorDefecto.setVisible(false);
                this.vMonitor.setVisible(false);
                this.vActividad.setVisible(false);
                this.vGestionActividad.setVisible(false);
                this.vSocio.setVisible(true);
                this.vSocio.setSize(698, 335);
                this.vSocio.TablaSocio.setModel(socioTablas);
                DiseñoTablaSocio();

                try {
                    pideSocios();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }
            case "SociosPorActividad": {
                this.vPorDefecto.setVisible(false);
                this.vActividad.setVisible(true);
                this.vActividad.setLocationRelativeTo(null);
                this.vActividad.jTableSocioActividad.setModel(modeloTablaActividades);
                DiseñoTablaActividad();
                vaciarTablaActividades();
                pideSociosInscritos();

                break;
            }
            case "SociosInscritos": {
                MostrarSociosInscritos();
                break;
            }
            case "GestionDeActividades": {
                this.controladorActividad = new ControladorActividad(this.sesion, this.vGestionActividad);
                this.vGestionActividad.setSize(550, 400);
                this.vGestionActividad.setVisible(true);
                this.vPorDefecto.setVisible(false);
                this.vMonitor.setVisible(false);
                this.vSocio.setVisible(false);
                this.vActividad.setVisible(false);
                break;
            }
            case "CuotadeActividad": {
                this.vCuota.setVisible(true);
                this.vCuota.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vCuota.setLocationRelativeTo(null);
                this.vCuota.jTable1.setModel(modeloTablaCuotas);
                this.DiseñoTablaCuotas();
                this.vaciarTablaCuotas();
                break;
            }
            case "VerCuota": {
                MostrarCuotas();
                break;
            }
            case "Salir": {
                vActividad.dispose();
                break;
            }
        }
    }

    /**
     * Este método esta a la escucha de los posibles que se pueden en nuestra
     * ventana En este caso simplemente está a la escucha de que pulsemos el
     * botón de salir de la ventana
     */
    private void addListeners() {
        vPrincipal.jMenuItemMonitores.addActionListener(this);
        vPrincipal.jMenuItemSocios.addActionListener(this);
        vPrincipal.jMenuItemSalir.addActionListener(this);
        vPrincipal.jMenuItemActividades.addActionListener(this);
        vActividad.jButtonSociosInscritos.addActionListener(this);
        vActividad.jButtonSalir.addActionListener(this);
        vPrincipal.jMenuItemGestionActividades.addActionListener(this);
        vPrincipal.CuotaActividad.addActionListener(this);
        vCuota.jButtonVerCuota.addActionListener(this);
    }

    /**
     * Este metodo rellena la tabla de monitores con los monitores de la base de
     * datos
     */
    private void pideMonitores() {
        ArrayList<Monitor> monitores = this.mDAO.listaMonitores();
        this.mTablas.vaciarTablaMonitores();
        this.mTablas.rellenarTablaMonitores(monitores);
    }

    /**
     * Diseño de la tabla monitores con el espacio entre columnas y la
     * prohibición de editar celdas
     *
     */
    private void DiseñoTablaMonitor() {
        //Para no permitir el redimensionamiento de las columnas con el ratón
        vMonitor.TablaMonitor.getTableHeader().setResizingAllowed(false);
        vMonitor.TablaMonitor.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vMonitor.TablaMonitor.getColumnModel().getColumn(0).setPreferredWidth(60);
        vMonitor.TablaMonitor.getColumnModel().getColumn(1).setPreferredWidth(240);
        vMonitor.TablaMonitor.getColumnModel().getColumn(2).setPreferredWidth(90);
        vMonitor.TablaMonitor.getColumnModel().getColumn(3).setPreferredWidth(90);
        vMonitor.TablaMonitor.getColumnModel().getColumn(4).setPreferredWidth(150);
        vMonitor.TablaMonitor.getColumnModel().getColumn(5).setPreferredWidth(150);
        vMonitor.TablaMonitor.getColumnModel().getColumn(6).setPreferredWidth(60);
    }

    /**
     * Este metodo rellena la tabla de socios con los socios de la base de datos
     *
     * @throws SQLException
     */
    private void pideSocios() throws SQLException {
        ArrayList<Socio> socios = this.sDAO.listarSocios();
        this.socioTablas.vaciarTablaSocios();
        this.socioTablas.rellenarTablaSocios(socios);
    }

    /**
     * Diseño de la tabla socios con el espacio entre columnas y la prohibición
     * de editar celdas
     *
     */
    private void DiseñoTablaSocio() {
        //Para no permitir el redimensionamiento de las columnas con el ratón
        vSocio.TablaSocio.getTableHeader().setResizingAllowed(false);
        vSocio.TablaSocio.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vSocio.TablaSocio.getColumnModel().getColumn(0).setPreferredWidth(90);
        vSocio.TablaSocio.getColumnModel().getColumn(1).setPreferredWidth(250);
        vSocio.TablaSocio.getColumnModel().getColumn(2).setPreferredWidth(110);
        vSocio.TablaSocio.getColumnModel().getColumn(3).setPreferredWidth(110);
        vSocio.TablaSocio.getColumnModel().getColumn(4).setPreferredWidth(130);
        vSocio.TablaSocio.getColumnModel().getColumn(5).setPreferredWidth(320);
        vSocio.TablaSocio.getColumnModel().getColumn(6).setPreferredWidth(110);
        vSocio.TablaSocio.getColumnModel().getColumn(7).setPreferredWidth(60);
    }

    private void DiseñoTablaSocio2() {
        //Para no permitir el redimensionamiento de las columnas con el ratón
        vGestionActividad.jTableGestionDeActividades.getTableHeader().setResizingAllowed(false);
        vGestionActividad.jTableGestionDeActividades.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(0).setPreferredWidth(90);
        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(1).setPreferredWidth(250);
        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(2).setPreferredWidth(110);
        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(3).setPreferredWidth(110);
        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(4).setPreferredWidth(130);
        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(5).setPreferredWidth(320);
        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(6).setPreferredWidth(110);
        vGestionActividad.jTableGestionDeActividades.getColumnModel().getColumn(7).setPreferredWidth(60);
    }

    /**
     * Modelo por defecto para la tabla de actividades, en el mismo prohibimos
     * que las celdas se puedan editar
     */
    public DefaultTableModel modeloTablaActividades = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     *
     */
    public DefaultTableModel modeloTablaCuotas = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * Diseño de la tabla actividades con el espacio entre columnas y la
     * prohibición de editar celdas
     *
     */
    private void DiseñoTablaActividad() {
        vActividad.jTableSocioActividad.setModel(modeloTablaActividades);
        String[] columnasTabla = {"Correo del Socio", "Nombre del Socio"};
        modeloTablaActividades.setColumnIdentifiers(columnasTabla);

        vActividad.jTableSocioActividad.getColumnModel().getColumn(0).setPreferredWidth(60);
        vActividad.jTableSocioActividad.getColumnModel().getColumn(1).setPreferredWidth(60);
        //vActividad.jTableSocioActividad.getTableHeader().setResizingAllowed(false);
        //vActividad.jTableSocioActividad.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    /**
     * Metodo que vacía la tabla de actividades
     */
    public void vaciarTablaActividades() {
        while (modeloTablaActividades.getRowCount() > 0) {
            modeloTablaActividades.removeRow(0);
        }
    }

    /**
     * Metodo que muestra los nombres de las actividades en el jComboBox
     */
    public void pideSociosInscritos() {
        this.DiseñoTablaActividad();
        ActividadDAO act = new ActividadDAO(this.sesion);
        act.devolverActividades(vActividad.jComboBoxActividades);
        vActividad.setVisible(true);
    }

    /**
     * Metodo que muestra los socios inscritos a la actividad seleccionada en el
     * jComboBox
     */
    public void MostrarSociosInscritos() {
        ActividadDAO miActividad = new ActividadDAO(this.sesion);
        String Nombre_Actividad = (String) vActividad.jComboBoxActividades.getSelectedItem();
        String idActividad = miActividad.getIdActividad(Nombre_Actividad);
        this.vaciarTablaActividades();

        String tipoBD = (String) this.vLogin.jComboBoxServer.getSelectedItem();

        if (tipoBD.equals("Oracle")) {
            miActividad.sociosPorActividad(idActividad, modeloTablaActividades);
        } else {
            miActividad.sociosPorActividadMariaDB(idActividad, modeloTablaActividades);
        }
    }

    public void MostrarCuotas() {
        ActividadDAO miActividad = new ActividadDAO(this.sesion);
        SocioDAO miSocio = new SocioDAO(sesion);
        String idActividad = this.vCuota.jTextFieldCodActividad.getText();
        this.vaciarTablaCuotas();
        miActividad.getIdActividades(idActividad, modeloTablaCuotas); 
        
        int numSocios = this.vCuota.jTable1.getRowCount();     
        int precioActividad = miActividad.getPrecio(idActividad);
        int sinDescuento =  numSocios * (int) precioActividad;
        this.vCuota.jTextFieldPrecioAct.setText(String.valueOf(precioActividad));
        this.vCuota.jTextFieldNumSocios.setText(String.valueOf(numSocios));
        this.vCuota.jTextFieldCuotaMensualSin.setText(String.valueOf(sinDescuento));
    }

   

    private void DiseñoTablaCuotas() {
        this.vCuota.jTable1.setModel(modeloTablaCuotas);
        String[] columnasTabla = {"Nombre", "Categoría"};
        modeloTablaCuotas.setColumnIdentifiers(columnasTabla);

        vCuota.jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
        vCuota.jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
    }

    /**
     * Metodo que vacía la tabla de actividades
     */
    public void vaciarTablaCuotas() {
        while (modeloTablaCuotas.getRowCount() > 0) {
            modeloTablaCuotas.removeRow(0);
        }
    }
}
