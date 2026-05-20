/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kevin;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Conexion {
     Connection conectar;
    public Connection conectar(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 

            conectar=DriverManager.getConnection(
                "jdbc:mysql://localhost/pandashina",
                "root",
                ""
            );

            JOptionPane.showMessageDialog(null, "Conectado");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       return conectar; 
    }
}
