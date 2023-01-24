/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author juald
 */
public class VistaMensaje {

    /**
     *
     * @param texto Texto que queremos mostrar en la ventana en caso del error
     */
    public void MensajeDeError(String texto) {
        //Icon iconoError = new ImageIcon(getClass().getResource("Escritorio/error.png")); //Asi se introducen imagenes de iconos para los mensajes que queramos mostrar
        //JOptionPane.showMessageDialog(null, texto, "", JOptionPane.ERROR_MESSAGE, iconoError);
        JOptionPane.showMessageDialog(null, texto, "", JOptionPane.ERROR_MESSAGE);
        /*ImageIcon icono = new ImageIcon(getClass().getResource("/error.png"));
        JOptionPane.showMessageDialog(null, texto, texto, JOptionPane.ERROR_MESSAGE, icono);*/
    }

    /**
     *
     * @param texto texto que queremos mostrar en la ventana para informar al
     * usuario
     */
    public void MensajeInformacion(String texto) {
        JOptionPane.showMessageDialog(null, texto, "", JOptionPane.INFORMATION_MESSAGE);

    }

    public void MensajeDeError(String texto, String message) {
         JOptionPane.showMessageDialog(null, texto + "\n" + message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
