
package controlador;

import java.security.AuthProvider;
import java.util.Properties;

public class JavaMail {
    public static void enviarEmail(){
        Properties propiedades  = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        
        String cuentaGmail = "xxxx@gmail.com";
        String contraseña  = "xxxxxx";
        
        
    }
}
