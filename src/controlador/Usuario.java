package controlador;
import java.sql.*;
import modelo.ConexionSQL;
import at.favre.lib.crypto.bcrypt.BCrypt;
import javax.swing.JOptionPane;
public class Usuario {
    //atributos
    private String usuario;
    private String contraseña;
    private String nombre;
    private String apellido;
    
    public Usuario(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }
    
    public Usuario(String nombre, String apellido, String usuario, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return obtenerDato("usuario");
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return obtenerDato("nombre");
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
       return obtenerDato("apellido");
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    //metodo para obtener dato del usuario desde la base de datos
    public String obtenerDato(String dato){
        // Inicializa la variable para almacenar el nombre de usuario
        try {
            Connection conexion = ConexionSQL.obtenerConexion();
            // Crea una consulta SQL parametrizada para obtener el usuario correspondiente
            PreparedStatement consulta = conexion.prepareStatement("SELECT "+dato+ " FROM Usuarios WHERE Usuario = ?");
            // Configura el valor del parámetro en la consulta
            consulta.setString(1, usuario);
            ResultSet resultSet = consulta.executeQuery();
            // Verifica si la consulta devuelve resultados
            if (resultSet.next()) {
                // Obtiene el valor de usuario de la primera fila coincidente en el conjunto de resultados
                dato = resultSet.getString(dato);
            }
        } catch (SQLException e) {
            // Captura y maneja posibles excepciones de SQL
            e.printStackTrace();
        }
        // Devuelve el nombre de usuario encontrado (o cadena vacía si no se encuentra)
        return dato;
    }
    //metodo de tipo boleano(true, false) que valida el usuario y contraseña que se colocan para iniciar sesion
    public boolean validarCredenciales() {
        // Establece la conexión a la base de datos "UCV"
        try {
            Connection conexion = ConexionSQL.obtenerConexion();
            // Consulta la tabla "usuarios" para obtener la contraseña cifrada
            String consulta = "SELECT contraseña FROM usuarios WHERE usuario = ?";
            PreparedStatement consultaPreparada = conexion.prepareStatement(consulta);
            consultaPreparada.setString(1, usuario); // Configura el valor del parámetro con el nombre de usuario
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();
            if (resultadoConsulta.next()) { // Comprueba si se encontró un registro
                // Crea un objeto verificador de BCrypt
                BCrypt.Verifyer verificador = BCrypt.verifyer(BCrypt.Version.VERSION_2A);
                // Crea una variable donde se almacena la contraseña cifrada que esta en la base de datos 
                String contraseñaAlmacenada = resultadoConsulta.getString("contraseña");
                // Verifica si la contraseña proporcionada coincide con la contraseña cifrada
                BCrypt.Result resultadoVerificacion = verificador.verify(contraseña.toCharArray(), contraseñaAlmacenada.toCharArray());  // Verificamos si la contraseña coincide con el hash almacenado
                if (resultadoVerificacion.verified) {
                    return true; // Las contraseñas coinciden
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; // En caso de error o contraseñas no coincidentes
    } 
   
}
