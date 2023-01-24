/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

/**
 *
 * @author juald
 */
public class VistaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VistaPrincipal
     */
    public VistaPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BarraMenuPrincipal = new javax.swing.JMenuBar();
        MenuItemMonitores = new javax.swing.JMenu();
        jMenuItemMonitores = new javax.swing.JMenuItem();
        MenuItemSocios = new javax.swing.JMenu();
        jMenuItemSocios = new javax.swing.JMenuItem();
        MenuItemActividades = new javax.swing.JMenu();
        jMenuItemGestionActividades = new javax.swing.JMenuItem();
        jMenuItemActividades = new javax.swing.JMenuItem();
        CuotaActividad = new javax.swing.JMenuItem();
        jMenuSalir = new javax.swing.JMenu();
        jMenuItemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MenuItemMonitores.setText("Monitores");
        MenuItemMonitores.setActionCommand("Menu");

        jMenuItemMonitores.setText("Gestión de Monitores");
        jMenuItemMonitores.setActionCommand("GestionDeMonitores");
        MenuItemMonitores.add(jMenuItemMonitores);

        BarraMenuPrincipal.add(MenuItemMonitores);

        MenuItemSocios.setText("Socios");
        MenuItemSocios.setActionCommand("Menu");

        jMenuItemSocios.setText("Gestion de Socios");
        jMenuItemSocios.setActionCommand("GestionDeSocios");
        MenuItemSocios.add(jMenuItemSocios);

        BarraMenuPrincipal.add(MenuItemSocios);

        MenuItemActividades.setText("Actividades");

        jMenuItemGestionActividades.setText("Gestión de Actividades");
        jMenuItemGestionActividades.setActionCommand("GestionDeActividades");
        MenuItemActividades.add(jMenuItemGestionActividades);

        jMenuItemActividades.setText("Socios por Actividad");
        jMenuItemActividades.setActionCommand("SociosPorActividad");
        MenuItemActividades.add(jMenuItemActividades);

        CuotaActividad.setText("Cuota de Actividad");
        CuotaActividad.setActionCommand("CuotadeActividad");
        MenuItemActividades.add(CuotaActividad);

        BarraMenuPrincipal.add(MenuItemActividades);

        jMenuSalir.setText("Salir");
        jMenuSalir.setActionCommand("Cerrar");

        jMenuItemSalir.setText("Salir de la Aplicación");
        jMenuItemSalir.setActionCommand("CerrarVentanaPrincipal");
        jMenuSalir.add(jMenuItemSalir);

        BarraMenuPrincipal.add(jMenuSalir);

        setJMenuBar(BarraMenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 714, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuBar BarraMenuPrincipal;
    public javax.swing.JMenuItem CuotaActividad;
    private javax.swing.JMenu MenuItemActividades;
    public javax.swing.JMenu MenuItemMonitores;
    public javax.swing.JMenu MenuItemSocios;
    public javax.swing.JMenuItem jMenuItemActividades;
    public javax.swing.JMenuItem jMenuItemGestionActividades;
    public javax.swing.JMenuItem jMenuItemMonitores;
    public javax.swing.JMenuItem jMenuItemSalir;
    public javax.swing.JMenuItem jMenuItemSocios;
    public javax.swing.JMenu jMenuSalir;
    // End of variables declaration//GEN-END:variables
}