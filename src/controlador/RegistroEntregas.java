package controlador;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConexionSQL;
import java.util.Date;
import java.util.List;

public class RegistroEntregas {

    private int ID;
    private int EstudianteID;
    private String NombredeEstudiante;
    private String ProductoCodigo;
    
    private int CantidadEntregada;
    private String FechaEntrega;

    public RegistroEntregas() {
    }

    public RegistroEntregas(int ID, int EstudianteID, String ProductoCodigo, int CantidadEntregada, String FechaEntrega, String NombredeEstudiante) {
        this.ID = ID;
        this.EstudianteID = EstudianteID;
        this.NombredeEstudiante = NombredeEstudiante;
        this.ProductoCodigo = ProductoCodigo;
        this.CantidadEntregada = CantidadEntregada;
        this.FechaEntrega = FechaEntrega;
    }

    public RegistroEntregas(String NombredeEstudiante, String ProductoCodigo, int CantidadEntregada) {
        this.NombredeEstudiante = NombredeEstudiante;
        this.ProductoCodigo = ProductoCodigo;
        this.CantidadEntregada = CantidadEntregada;
    }

    public int getEstudianteID() {
        return EstudianteID;
    }

    public void setEstudianteID(int EstudianteID) {
        this.EstudianteID = EstudianteID;
    }

    public String getProductoCodigo() {
        return ProductoCodigo;
    }

    public void setProductoCodigo(String ProductoCodigo) {
        this.ProductoCodigo = ProductoCodigo;
    }

    public int getCantidadEntregada() {
        return CantidadEntregada;
    }

    public void setCantidadEntregada(int CantidadEntregada) {
        this.CantidadEntregada = CantidadEntregada;
    }

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(String FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }

    public String getNombredeEstudiante() {
        return NombredeEstudiante;
    }

    public void setNombredeEstudiante(String NombredeEstudiante) {
        this.NombredeEstudiante = NombredeEstudiante;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void guardarRegistroEnBD() {
        try {
            Date fechaActual = new Date();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFormateada = formatoFecha.format(fechaActual);

            Connection conexion = ConexionSQL.obtenerConexion();

            // Obtener el EstudianteID a partir del nombre del estudiante
            PreparedStatement consultaEstudiante = conexion.prepareStatement("SELECT ID FROM Estudiantes WHERE Nombres = ?");
            consultaEstudiante.setString(1, NombredeEstudiante);
            ResultSet resultSetEstudiante = consultaEstudiante.executeQuery();

            resultSetEstudiante.next();
            EstudianteID = resultSetEstudiante.getInt("ID");

            // Obtener el ProductoCodigo a partir del nombre del producto
            PreparedStatement consultaProducto = conexion.prepareStatement("SELECT codigo FROM Producto WHERE nombre = ?");
            consultaProducto.setString(1, ProductoCodigo);
            ResultSet resultSetProducto = consultaProducto.executeQuery();

            resultSetProducto.next();
            String productoCodigo = resultSetProducto.getString("codigo");

            // Insertar el registro en la tabla RegistroEntregas
            PreparedStatement consultaRegistro = conexion.prepareStatement("INSERT INTO RegistroEntregas (EstudianteID, ProductoCodigo, CantidadEntregada, FechaEntrega) VALUES (?, ?, ?, ?)");
            consultaRegistro.setInt(1, EstudianteID);
            consultaRegistro.setString(2, productoCodigo);
            consultaRegistro.setInt(3, CantidadEntregada);
            consultaRegistro.setString(4, fechaFormateada);
            consultaRegistro.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro agregado exitosamente!");

            // Llamar al método de resta con los parámetros correctos
            restaDeCantidades(CantidadEntregada, productoCodigo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void restaDeCantidades(int numeroCantidad, String codigo) {
        Connection conexion = ConexionSQL.obtenerConexion();
        try {
            // Obtener la cantidad actual del producto específico
            PreparedStatement consulta1 = conexion.prepareStatement("SELECT cantidad FROM Producto WHERE codigo = ?");
            consulta1.setString(1, codigo);
            ResultSet resultSet1 = consulta1.executeQuery();

            // Obtener la cantidad entregada más reciente para el producto específico
            PreparedStatement consulta2 = conexion.prepareStatement("SELECT TOP 1 CantidadEntregada FROM RegistroEntregas WHERE ProductoCodigo = ? ORDER BY ID DESC");
            consulta2.setString(1, codigo);
            ResultSet resultSet2 = consulta2.executeQuery();

            resultSet1.next();
            resultSet2.next();

            // Calcular la nueva cantidad
            int cantidadProducto = resultSet1.getInt("cantidad");
            int cantidadEntregada = resultSet2.getInt("CantidadEntregada");
            int resultadoResta = cantidadProducto - cantidadEntregada;

            // Actualizar la cantidad en la tabla Producto
            PreparedStatement consulta3 = conexion.prepareStatement("UPDATE Producto SET cantidad = ? WHERE codigo = ?");
            consulta3.setInt(1, resultadoResta);
            consulta3.setString(2, codigo);
            consulta3.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static List<RegistroEntregas> obtenerRegistrosDesdeBD() {
        List<RegistroEntregas> registroEntregas = new ArrayList<>();
        try {
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT r.*, e.Nombres AS NombresEstudiante "
                    + "FROM RegistroEntregas r "
                    + "JOIN Estudiantes e ON r.EstudianteID = e.ID"
            );
            ResultSet resultSet = consulta.executeQuery();
            while (resultSet.next()) {
                RegistroEntregas registro = new RegistroEntregas(
                        resultSet.getInt("ID"),
                        resultSet.getInt("EstudianteID"),
                        resultSet.getString("ProductoCodigo"),
                        resultSet.getInt("CantidadEntregada"),
                        resultSet.getString("FechaEntrega"),
                        resultSet.getString("NombresEstudiante")
                );
                registroEntregas.add(registro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return registroEntregas;
    }


    public static ArrayList<String> obtenerNombresEstudiantes() {
        ArrayList<String> listanombres = new ArrayList<>();
        try {
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT Nombres FROM Estudiantes");
            ResultSet resultSet = consulta.executeQuery();
            while (resultSet.next()) {
                listanombres.add(resultSet.getString("Nombres"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return listanombres;
    }

}
