package kevin;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class LibroDAO {

    public void insertarLibro(int idLibro, String titulo, int anio, int idAutor) {
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "INSERT INTO libros(id_libro, titulo, anio, id_autor) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idLibro);
            ps.setString(2, titulo);
            ps.setInt(3, anio);
            ps.setInt(4, idAutor);
            ps.executeUpdate();

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al insertar libro: " + e.getMessage());
        }
    }

    public String ingresarLibro(String idLibroTexto, String titulo, String anioTexto, String idAutorTexto) {

        if (idLibroTexto.trim().isEmpty() || titulo.trim().isEmpty()
                || anioTexto.trim().isEmpty() || idAutorTexto.trim().isEmpty()) {
            return "Complete todos los datos del libro";
        }

        try {
            int idLibro = Integer.parseInt(idLibroTexto);
            int anio = Integer.parseInt(anioTexto);
            int idAutor = Integer.parseInt(idAutorTexto);

            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            if (!existeAutor(cn, idAutor)) {
                cn.close();
                return "El id del autor no existe";
            }

            String sql = "INSERT INTO libros(id_libro, titulo, anio, id_autor) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idLibro);
            ps.setString(2, titulo);
            ps.setInt(3, anio);
            ps.setInt(4, idAutor);

            ps.executeUpdate();

            cn.close();

            return "Libro ingresado correctamente";

        } catch (NumberFormatException e) {
            return "ID libro, anio e ID autor deben ser numeros";
        } catch (Exception e) {
            return "Error al ingresar libro: " + e.getMessage();
        }
    }

    private boolean existeAutor(Connection cn, int idAutor) throws SQLException {

        String sql = "SELECT id_autor FROM autores WHERE id_autor = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, idAutor);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public DefaultTableModel mostrarLibros() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Libro");
        modelo.addColumn("Titulo");
        modelo.addColumn("Anio");
        modelo.addColumn("Autor");

        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "SELECT l.id_libro, l.titulo, l.anio, a.nombre "
                    + "FROM libros l "
                    + "INNER JOIN autores a ON l.id_autor = a.id_autor";

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getInt("anio"),
                    rs.getString("nombre")
                });
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al mostrar libros: " + e.getMessage());
        }

        return modelo;
    }

    public DefaultTableModel mostrarLibrosPorAutor(int idAutor) {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Libro");
        modelo.addColumn("Titulo");
        modelo.addColumn("Anio");
        modelo.addColumn("Autor");

        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "SELECT l.id_libro, l.titulo, l.anio, a.nombre "
                    + "FROM libros l "
                    + "INNER JOIN autores a ON l.id_autor = a.id_autor "
                    + "WHERE a.id_autor = ?";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idAutor);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getInt("anio"),
                    rs.getString("nombre")
                });
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al mostrar libros por autor: " + e.getMessage());
        }

        return modelo;
    }
}