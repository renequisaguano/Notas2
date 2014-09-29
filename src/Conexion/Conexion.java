
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Rene Quisaguano
 */
public class Conexion {
    
    static String login="";
    static String password="";
    static String url="jdbc.odbc:netbeans";
    Connection con=null;
    String driver="sun.jdbc.odbc.JdbcOdbcDriver";

    public Conexion() {
        
        try
        {
            Class.forName(driver);
            con=DriverManager.getConnection(url,login,password);
            if (con!=null){
           JOptionPane.showMessageDialog(null,"Conexion exitosa");        
            }
            
        }catch(SQLException | ClassNotFoundException e){
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }
    
    
    public  Connection getConexion(){
        return con;
    }
    
    public void desconectar(){
        con=null;
         if (con!=null){
             JOptionPane.showMessageDialog(null, "No se pudo cerrar la conexion");
         }
    }
    
    public ResultSet Listar(String cad){
        
        try{
            Class.forName(driver).newInstance();
            Connection cn=DriverManager.getConnection(url,login,password);
            
            PreparedStatement da=cn.prepareStatement(cad);
            ResultSet tbl=da.executeQuery();
            return tbl;
                    
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |SQLException e){
            
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
         
        }
    }
    
    
    
     public String Ejecutar (String cad){
        
        try{
            Class.forName(driver).newInstance();
            Connection cn=DriverManager.getConnection(url,login,password);
            
            PreparedStatement da=cn.prepareStatement(cad);
            int r=da.executeUpdate();
            return "Registros grabados con exito";
                    
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |SQLException e){
            
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
            return "Error"+e.getMessage();
         
        }
    }
    
    
}
