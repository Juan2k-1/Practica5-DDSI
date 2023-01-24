/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

/**
 *
 * @author juald
 */
public class VistaSocio extends javax.swing.JPanel {

    /**
     * Creates new form VistaSocio
     */
    public VistaSocio() {
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

        jButtonUpdateSocio = new javax.swing.JButton();
        jLabelGestionDeMonitores = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaSocio = new javax.swing.JTable();
        jButtonAddSocio = new javax.swing.JButton();
        jButtonDeleteSocio = new javax.swing.JButton();
        jLabelBusqueda = new javax.swing.JLabel();
        jComboBoxBusqueda = new javax.swing.JComboBox();
        jTextFieldBusqueda = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jDateChooserBusqueda = new com.toedter.calendar.JDateChooser();

        setPreferredSize(new java.awt.Dimension(700, 335));

        jButtonUpdateSocio.setText("Actualización de Socio");
        jButtonUpdateSocio.setActionCommand("ActualizacionSocio");

        jLabelGestionDeMonitores.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelGestionDeMonitores.setText("Gestión de Socios");

        TablaSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaSocio);

        jButtonAddSocio.setText("Nuevo Socio");
        jButtonAddSocio.setActionCommand("NuevoSocio");

        jButtonDeleteSocio.setText("Baja de Socio");
        jButtonDeleteSocio.setActionCommand("BajaSocio");

        jLabelBusqueda.setText("Seleccione el criterio de búsqueda:");

        jComboBoxBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NumeroSocio", "Nombre", "DNI", "FechaNacimiento", "FechaEntrada", "Teléfono", "Correo", "Categoría" }));

        jLabel1.setText("Indique la búsqueda:");

        jButtonBuscar.setText("Buscar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelGestionDeMonitores, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(229, 229, 229))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAddSocio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDeleteSocio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonUpdateSocio)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelBusqueda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonBuscar)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldBusqueda)
                            .addComponent(jComboBoxBusqueda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBusqueda)
                    .addComponent(jComboBoxBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelGestionDeMonitores)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jDateChooserBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddSocio)
                    .addComponent(jButtonDeleteSocio)
                    .addComponent(jButtonUpdateSocio))
                .addContainerGap(43, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable TablaSocio;
    public javax.swing.JButton jButtonAddSocio;
    public javax.swing.JButton jButtonBuscar;
    public javax.swing.JButton jButtonDeleteSocio;
    public javax.swing.JButton jButtonUpdateSocio;
    public javax.swing.JComboBox jComboBoxBusqueda;
    public com.toedter.calendar.JDateChooser jDateChooserBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelBusqueda;
    public javax.swing.JLabel jLabelGestionDeMonitores;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField jTextFieldBusqueda;
    // End of variables declaration//GEN-END:variables
}