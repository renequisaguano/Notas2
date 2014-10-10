/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ReproductorMain;

import ReproductorGui.VentanaReproductor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

import javax.swing.UnsupportedLookAndFeelException;


import org.blinkenlights.jid3.ID3Exception;



/**
 *
 * @author AndresZ
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ID3Exception {
        try {            
            UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
            //TinyLookAndFeel.setCurrentTheme(TinyLookAndFeel.getCurrentTheme());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        VentanaReproductor v = null;
        v = new VentanaReproductor();
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
}
