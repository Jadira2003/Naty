
package carlo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import carlo.Conexion;


public class ProductosDao {

    public void insertarProducto(int idProducto, String nombre, int precio, int idCategoria) {
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "INSERT INTO productos(id_producto, nombre, precio, id_categoria) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idProducto);
            ps.setString(2, nombre);
            ps.setInt(3, precio);
            ps.setInt(4, idCategoria);
            ps.executeUpdate();

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    public String ingresarProducto(String idProductoTexto, 
            String nombre, String precioTexto, String idCategoriaTexto) {

        if (idProductoTexto.trim().isEmpty() || nombre.trim().isEmpty()
                || precioTexto.trim().isEmpty() || idCategoriaTexto.trim().isEmpty()) {
            return "Complete todos los datos de la categoria";
        }

        try {
            int idProducto = Integer.parseInt(idProductoTexto);
            int precio = Integer.parseInt(precioTexto);
            int idCategoria = Integer.parseInt(idCategoriaTexto);

            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            if (!existeCategoria(cn, idCategoria)) {
                cn.close();
                return "El id de la categoria no existe";
            }

            String sql = "INSERT INTO productos(id_producto, nombre, precio, id_categoria) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idProducto);
            ps.setString(2, nombre);
            ps.setInt(3, precio);
            ps.setInt(4, idCategoria);

            ps.executeUpdate();

            cn.close();

            return "Producto ingresado correctamente";

        } catch (NumberFormatException e) {
            return "ID producto, precio e ID categoria deben ser numeros";
        } catch (Exception e) {
            return "Error al ingresar producto: " + e.getMessage();
        }
    }

    private boolean existeCategoria(Connection cn, int idCategoria) throws SQLException {

        String sql = "SELECT id_categoria FROM categoria WHERE id_categoria = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, idCategoria);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public DefaultTableModel mostrarProductos() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Producto");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Categoria");

        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "SELECT l.id_producto, l.nombre, l.precio, a.nombre "
                    + "FROM productos l "
                    + "INNER JOIN categoria a ON l.id_categoria = a.id_categoria";

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getInt("precio"),
                    rs.getString("nombre")
                });
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al mostrar producto: " + e.getMessage());
        }

        return modelo;
    }

    public DefaultTableModel mostrarProductosPorCategoria(int idCategoria) {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Producto");
        modelo.addColumn("Nombre");
        modelo.addColumn("precio");
        modelo.addColumn("Categoria");

        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();

            String sql = "SELECT l.id_producto, l.nombre, l.precio, a.nombre "
                    + "FROM productos l "
                    + "INNER JOIN categoria a ON l.id_categoria = a.id_categoria "
                    + "WHERE a.id_categoria = ?";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idCategoria);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getInt("precio"),
                    rs.getString("nombre")
                });
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al mostrar productos por categoria: " + e.getMessage());
        }

        return modelo;
    }
}