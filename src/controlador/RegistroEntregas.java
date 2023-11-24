
package controlador;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConexionSQL;
import java.util.Date;


public class RegistroEntregas {
    private int EstudianteID;
    private String ProductoCodigo;
    private int CantidadEntregada;
    private String FechaEntrega;

    public RegistroEntregas() {
    }

    public RegistroEntregas( int EstudianteID, String ProductoCodigo, int CantidadEntregada, String FechaEntrega) {
        this.EstudianteID = EstudianteID;
        this.ProductoCodigo = ProductoCodigo;
        this.CantidadEntregada = CantidadEntregada;
        this.FechaEntrega = FechaEntrega;
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
    
    public void guardarRegistroEnBD() {
        try{
            Date fechaActual = new Date();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFormateada = formatoFecha.format(fechaActual);
            
            Connection conexion = ConexionSQL.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("INSERT INTO RegistroEntregas (EstudianteID, ProductoCodigo, CantidadEntregada, FechaEntrega) VALUES (?, ?, ?, ?)");
            consulta.setInt(1, EstudianteID);
            consulta.setString(2, ProductoCodigo);
            consulta.setInt(3, CantidadEntregada);
            consulta.setString(2, fechaFormateada);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro agregado exitosamente!");
            restaDeCantidades(CantidadEntregada, ProductoCodigo);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public int restaDeCantidades(int numeroCantidad, String codigo){
        Connection conexion = ConexionSQL.obtenerConexion();
        try {
            PreparedStatement consulta1 = conexion.prepareStatement("SELECT cantidad FROM Producto");
            ResultSet resultSet1 = consulta1.executeQuery();
            PreparedStatement consulta2 = conexion.prepareStatement("SELECT CantidadEntregada FROM RegistroEntregas");
            ResultSet resultSet2 = consulta2.executeQuery();
            int resultadoResta = resultSet1.getInt("cantidad") - resultSet2.getInt("CantidadEntregada");
            PreparedStatement consulta3 = conexion.prepareStatement("UPDATE Producto SET cantidad = ? WHERE codigo = ?");
            consulta3.setInt(1, resultadoResta);
            consulta3.setString(2, codigo);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 1;
    }
     
    public static ArrayList<String> obtenerNombresEstudiantes() {
        ArrayList<String> listanombres = new ArrayList<>();
        try{
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
