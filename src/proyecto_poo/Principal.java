
package proyecto_poo;

import Vista.LoginFrame;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import javax.swing.UIManager;

public class Principal {

    public static void main(String[] args) {
        //new Login().setVisible(true);
        try {
            FlatLaf.registerCustomDefaultsSource( "Icons" );
            FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
        } catch (Exception e) {
            System.out.println(e);
        } 
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
             new LoginFrame().setVisible(true);
            }
        });
        //String contraseña = "contraseña123";
        //String xd = BCrypt.withDefaults().hashToString(12, contraseña.toCharArray());
        //System.out.println(xd);
        
        
    }
    
}
