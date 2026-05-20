
package carlo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import carlo.Conexion;


public class CategoriaDao {

    public String ingresarCategoria(String idCategoriaTexto, String nombre) {

        if (idCategoriaTexto.trim().isEmpty() || nombre.trim().isEmpty() 
                ) {
            return "Complete todos los datos de la categoria";
        }

        try {
            int idCategoria = Integer.parseInt(idCategoriaTexto);

            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "INSERT INTO categoria(id_categoria, nombre) VALUES (?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idCategoria);
            ps.setString(2, nombre);
            ps.executeUpdate();

            cn.close();

            return "Categoria ingresado correctamente";

        } catch (NumberFormatException e) {
            return "El id de la Categoria debe ser un numero";
        } catch (Exception e) {
            return "Error al ingresar categoria: " + e.getMessage();
        }
    }

    public DefaultTableModel mostrarCategoria() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Categoria");
        modelo.addColumn("Nombre");

        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "SELECT * FROM categoria";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                });
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al mostrar categoria: " + e.getMessage());
        }

        return modelo;
    }

    public DefaultTableModel aceptarCategoria(String idCategoria, String nombre) {

        String mensaje = ingresarCategoria(idCategoria, nombre);

        JOptionPane.showMessageDialog(null, mensaje);

        return mostrarCategoria();
    }
}
