/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ReproductorLogica;

import ReproductorModelos.Cancion;
import ReproductorPersistencia.Broker;
import java.util.ArrayList;

/**
 *
 * @author AndresZ
 */
public class ListaCanciones {    
    ArrayList<Cancion> v;
    public ListaCanciones(){
        Broker d = new Broker();
        v = new ArrayList<Cancion>();
        v = d.getCanciones();          
    }
    public Cancion getCancion(int i) {
            return v.get(i);
    }
    public int largo(){
            return v.size();
    }
    public boolean guardarCancion(Cancion s){
        Broker d = new Broker();
        if (d.guardarCanciones(s)) {
            v.add(s);
            return true;
        }
        else return false;
    }

    public boolean eliminarCancion(int s){
        Broker b = new Broker();
        if (b.eliminarCancion(s)) {
            for(int i = 0;i<v.size();i++){
                if (v.get(i).getId() == s)
                    v.remove(i);
            }
            return true;
        }else return false;
    }
    public boolean eliminarCanciones(){
        Broker b = new Broker();
        v.removeAll(v);
        return b.eliminarCanciones();
    }
    public boolean updateId(int c,int idN){
        Broker b = new Broker();
        return b.updateId(c,idN);
    }
}
