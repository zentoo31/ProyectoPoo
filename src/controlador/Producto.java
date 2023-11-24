package controlador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConexionSQL;
public class Producto {
    //atributos
    private String codigo;
    private String nombre;
    private int cantidad;
    private String unidad;
    private String vencimiento;
    private String categoria;
    //constructor vaccio
    public Producto() {
    }
    //constructor completo
    public Producto(String codigo, String nombre, int cantidad, String unidad, String vencimiento, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.vencimiento = vencimiento;
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    //metodo para guardar producto en la base de datos
    public void guardarProductoEnBD() {
        try{
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("INSERT INTO Producto (codigo, nombre, cantidad, unidad, vencimiento,categoria) VALUES (?, ?, ?, ?, ?,?)");
            consulta.setString(1, codigo);
            consulta.setString(2, nombre);
            consulta.setInt(3, cantidad);
            consulta.setString(4, unidad);
            consulta.setString(5, vencimiento);
            consulta.setString(6, categoria);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para actualizar producto en la base de datos
     public void actualizarProductoEnBD() {
        try{
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("UPDATE Producto SET nombre = ?, cantidad = ?, unidad = ?, vencimiento = ?, categoria = ? WHERE codigo = ?");
            consulta.setString(1, nombre);
            consulta.setInt(2, cantidad);
            consulta.setString(3, unidad);
            consulta.setString(4, vencimiento);
            consulta.setString(5, categoria);
            consulta.setString(6, codigo); // Utilizamos el código para identificar el producto a actualizar
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
 //metodo de tipo lista producto para obtener los productos en la base de datos
    public static List<Producto> obtenerProductoDesdeBD() {
        List<Producto> listaproducto = new ArrayList<>();
        try{
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM Producto");
            ResultSet resultSet = consulta.executeQuery();
            while (resultSet.next()) {
                Producto producto = new Producto(
                    resultSet.getString("codigo"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("cantidad"),
                    resultSet.getString("unidad"),
                    resultSet.getString("vencimiento"),
                    resultSet.getString("categoria")
                );
                listaproducto.add(producto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return listaproducto;
    }
    
   
 //metodo para eliminar producto en la base de datos
    public void eliminarProductoEnBD() {
        try{
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("DELETE FROM Producto WHERE codigo = ?");
            consulta.setString(1, codigo); // Utilizamos el código para identificar el producto a eliminar
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
 //metodo de tipo producto para consultar un producto por codigo, el metodo devuelve un producto
    public static Producto consultarProductoPorCodigo(String codigo) {
        Producto producto = null;
        try{
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM Producto WHERE codigo = ?");
            consulta.setString(1, codigo); // Establece el código como parámetro en la consulta
            ResultSet resultSet = consulta.executeQuery();
            if (resultSet.next()) {
                producto = new Producto(
                resultSet.getString("codigo"),
                resultSet.getString("nombre"),
                resultSet.getInt("cantidad"),
                resultSet.getString("unidad"),
                resultSet.getString("vencimiento"),
                resultSet.getString("categoria")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return producto;//retorna producto
    }
    
    public static Producto consultarProductoPorNombre(String nombre) {
        Producto producto = null;
        try{
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM Producto WHERE nombre = ?");
            consulta.setString(1, nombre); // Establece el código como parámetro en la consulta
            ResultSet resultSet = consulta.executeQuery();
            if (resultSet.next()) {
                producto = new Producto(
                resultSet.getString("codigo"),
                resultSet.getString("nombre"),
                resultSet.getInt("cantidad"),
                resultSet.getString("unidad"),
                resultSet.getString("vencimiento"),
                resultSet.getString("categoria")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return producto;//retorna producto
    }
    
}
