/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Menu.Principal;
import ReproductorMain.Main;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author rene
 */
public class Inicial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        try {            
            UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
            //TinyLookAndFeel.setCurrentTheme(TinyLookAndFeel.getCurrentTheme());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Principal p=new Principal();
        p.setVisible(true);
        
    }
}
