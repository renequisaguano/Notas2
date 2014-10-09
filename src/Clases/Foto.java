/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;


import Conexion.Conexion;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


public class Foto{

    Conexion con;
    private Image data;

    public Foto() {
        con = new Conexion();
    }
    //metodo que dada una cadena de bytes la convierte en una imagen con extension jpeg
    private Image ConvertirImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        return reader.read(0, param);
    }
   
    //METODO PARA RECUPERAR LA IMAGEN DE LA BASE DE DATOS
    public Image recuperarfotos(String usuario) {

        try {
            PreparedStatement pstm = con.getConexion().prepareStatement("SELECT FOTO_DOC FROM DOCENTE where CEDULA_DOC= ? ");
            pstm.setString(1, usuario);
            ResultSet res = pstm.executeQuery();
            int i = 0;
                while (res.next()) {
                    // se lee la cadena de bytes de la base de datos
                    byte[] b = res.getBytes("FOTO_DOC");
                    //esta cadena de bytes sera convertida en una imagen
                    data = ConvertirImagen(b);                   
                    i++;
                }
            res.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Clases.Foto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;

    }
}
