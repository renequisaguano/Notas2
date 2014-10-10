/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ReproductorPersistencia;

import java.io.File;

import java.util.Properties;
import java.sql.*;


import javax.swing.JOptionPane;


public class DBManager {
	private Connection cnn = null;
        static String login="";
        static String password="";
        static String url="jdbc:odbc:reproductor";
        String driver="sun.jdbc.odbc.JdbcOdbcDriver";
        //String driver="com.mysql.jdbc.Driver";
	public void conectar(){
             
        try
        {
            Class.forName(driver);
            cnn=DriverManager.getConnection(url,login,password);
            
           //JOptionPane.showMessageDialog(null,"Conexion exitosa");        
            
            
        }catch(ClassNotFoundException e) {
			System.out.println("Error de driver");
		}
		catch(SQLException e2) {
			JOptionPane.showMessageDialog(null,e2);
	}

        
		
                
	}
        
       
   public void desconectar() {
		try {
			cnn.close();
		}
		catch(SQLException e) {
			System.out.println("Error al desconectar");
		}
	}
   public ResultSet getRS(String sql) {
		ResultSet res = null;
		try{
			Statement st = cnn.createStatement();
			res = st.executeQuery(sql);
		}
		catch(SQLException e) {
		}
		return res;
	}
public boolean ejecutar(String sql){
		try{
			this.conectar();
			Statement st = cnn.createStatement();
			st.executeUpdate(sql);
			st.close();
			this.desconectar();
			return true;
		}
		catch(SQLException e) {
			return false;
		}

	}
}

