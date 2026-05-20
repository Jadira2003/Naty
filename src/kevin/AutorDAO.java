package kevin;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AutorDAO {

    public String ingresarAutor(String idAutorTexto, String nombre, String nacionalidad) {

        if (idAutorTexto.trim().isEmpty() || nombre.trim().isEmpty() 
                || nacionalidad.trim().isEmpty()) {
            return "Complete todos los datos del autor";
        }

        try {
            int idAutor = Integer.parseInt(idAutorTexto);

            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "INSERT INTO autores(id_autor, nombre, nacionalidad) VALUES (?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idAutor);
            ps.setString(2, nombre);
            ps.setString(3, nacionalidad);

            ps.executeUpdate();

            cn.close();

            return "Autor ingresado correctamente";

        } catch (NumberFormatException e) {
            return "El id del autor debe ser un numero";
        } catch (Exception e) {
            return "Error al ingresar autor: " + e.getMessage();
        }
    }

    public DefaultTableModel mostrarAutores() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Autor");
        modelo.addColumn("Nombre");
        modelo.addColumn("Nacionalidad");

        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "SELECT * FROM autores";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_autor"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad")
                });
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al mostrar autores: " + e.getMessage());
        }

        return modelo;
    }

    public DefaultTableModel aceptarAutor(String idAutor, String nombre, String nacionalidad) {

        String mensaje = ingresarAutor(idAutor, nombre, nacionalidad);

        JOptionPane.showMessageDialog(null, mensaje);

        return mostrarAutores();
    }
}