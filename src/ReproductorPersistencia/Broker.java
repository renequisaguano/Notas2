/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ReproductorPersistencia;

import ReproductorModelos.Cancion;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AndresZ
 */
public class Broker {
    public ArrayList<Cancion> getCanciones(){
        ResultSet rs = null;
        DBManager db = new DBManager();
        db.conectar();
        ArrayList <Cancion> res = new ArrayList <Cancion>();
        rs = db.getRS("Select * from Cancion order by id");
        try{
            while(rs.next()){
                Cancion tmp = new Cancion();
                tmp.setNombre(rs.getString("Ruta"));
                tmp.setId(rs.getInt("Id"));
                res.add(tmp);
            }
        }catch(Exception ex){ex.printStackTrace();}
        db.desconectar();
        return res;
    }
    public boolean guardarCanciones(Cancion unJugador){
        DBManager db = new DBManager();
        return db.ejecutar("Insert into Cancion (Ruta,Id)values ('"+unJugador.getNombre()+"',"+unJugador.getId()+");");
    }
    public boolean eliminarCancion(int id){
        DBManager db = new DBManager();
        return db.ejecutar("DELETE FROM Cancion where (Id="+ id+ ");");
    }
    public boolean eliminarCanciones(){
        DBManager db = new DBManager();
        return db.ejecutar("DELETE * FROM Cancion;");
    }
    public boolean updateId(int idV,int idN){
        DBManager db = new DBManager();
        return db.ejecutar("UPDATE Cancion SET Id ="+idN+" where (Id ="+idV+");");
    }
}
