/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ReproductorModelos;

/**
 *
 * @author AndresZ
 */
public class Cancion {
    private String nombre;
    
    private int id;
    public Cancion(){
        nombre = "";
        id = 0;
    }
    public void setNombre(String n){
        nombre = n;
    }
    public void setId(int iddd){
        id = iddd;
    }
    public int getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    
    
}
