
package modelo;

import java.sql.*;

public class ConexionSQL {
    //clase y metodo que establece una conexion con la base de datos
    public static Connection obtenerConexion() {
        Connection conexion = null; //inicializa conexion
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=UCV;user=sa;password=12345678;encrypt=true;trustServerCertificate=true";
            conexion = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }
}
