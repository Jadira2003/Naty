/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carlo;

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
                "jdbc:mysql://localhost/kira",
                "root",
                ""
            );

            JOptionPane.showMessageDialog(null, "Conectado a kira");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       return conectar; 
    }
}
