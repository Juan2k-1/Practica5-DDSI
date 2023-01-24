/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicacion;

import Controlador.ControladorLogin;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author juald
 */
public class App2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (UnsupportedLookAndFeelException e) {
            // If Nimbus is not available, you can set the GUI to another look and feel
            Logger.getLogger(App2.class.getName()).log(Level.SEVERE, null, e);
        }
        ControladorLogin obj = new ControladorLogin();
    }
}
