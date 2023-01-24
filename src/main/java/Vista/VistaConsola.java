/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juald
 */
public class VistaConsola {

    public void mensajeConsola(String texto) {
        System.out.println("****************************");
        System.out.println(texto);
        System.out.println("****************************");
    }

    public void mensajeConsola(String texto, String error) {
        System.out.println("****************************");
        System.out.println(texto);
        System.out.println("****************************");

        System.out.println("****************************");
        System.out.println(error);
        System.out.println("****************************");
    }

    public void vistaMetadatos(DatabaseMetaData dbmd) {
        System.out.println("Mostrando metadatos de la base de datos");
        System.out.println("---------------------------------------");

        try {
            System.out.println("Nombre de la base datos: " + dbmd.getDatabaseProductName());
            System.out.println("Version: " + dbmd.getDatabaseMajorVersion());
            System.out.println("URL: " + dbmd.getURL());
            System.out.println("Driver Name: " + dbmd.getDriverName());
            System.out.println("Driver Version: " + dbmd.getDriverVersion());
            System.out.println("Usuario: " + dbmd.getUserName());
            System.out.println("Lista de términos SQL que no pertenecen al SQL estandar:  " + dbmd.getSQLKeywords());
        } catch (SQLException ex) {
            Logger.getLogger(VistaConsola.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
