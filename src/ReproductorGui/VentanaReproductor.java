/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VentanaReproductor.java
 *
 * Created on Mar 1, 2011, 10:34:27 AM
 */

package ReproductorGui;


import ReproductorLogica.ListaCanciones;
import ReproductorModelos.Cancion;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicController; 
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import java.io.*;
import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import kj.dsp.KJDigitalSignalProcessingAudioDataConsumer;

import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v2.*;






/**
 *
 * @author AndresZ
 */
public class VentanaReproductor extends javax.swing.JFrame implements BasicPlayerListener{
    public double bytesLength = 0.0;
    float progressUpdate;
    double progressNow;
    private AlCliquear clic;
    public boolean shuffle = false;
    public int segundos1 = 0;
    public int minutos1 = 0;
    public int horas1 = 0;
    JLabel imagen;    
    ArrayList<Cancion> listaC;
    public ArrayList<String> cola;
    JSlider[] arregloE = new JSlider[10];
    float[] arreglo = new float[10];
    Map audioInfo;
    public KJDigitalSignalProcessingAudioDataConsumer dsp;
    SourceDataLine linea = null;
    /** Creates new form VentanaReproductor */
    public VentanaReproductor() throws IOException, ID3Exception{        
        initComponents();
        /*arregloE[0] = jSlider3;
        arregloE[1] = jSlider4;
        arregloE[2] = jSlider5;
        arregloE[3] = jSlider6;
        arregloE[4] = jSlider9;
        arregloE[5] = jSlider10;
        arregloE[6] = jSlider8;
        arregloE[7] = jSlider7;
        arregloE[8] = jSlider12;
        arregloE[9] = jSlider11;
        jPanel8.setVisible(false);*/
        cola = new ArrayList<String>();       
        seCerroVentana em = new seCerroVentana(this);
        this.addWindowListener(em);
        horas.setText("00:00:00");
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Volume.png")).getImage());
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vol = jSlider1.getValue();
        this.clic = new AlCliquear();
        this.clic.getPlayer().addBasicPlayerListener(this);
        l = new ListaCanciones();        
        listaC = new ArrayList<Cancion>();
        dsp = new KJDigitalSignalProcessingAudioDataConsumer(2048, 50);
        for (int i =0;i < l.largo();i++) {
            Cancion c = l.getCancion(i);
            String aux = c.getNombre().replace('?', '\'');
            File movido = new File(aux);
            if (movido.isFile()) {                
                String s = "";
                String as = "";
                AudioFileFormat baseFileFormat = null;               
                try {
                    baseFileFormat = AudioSystem.getAudioFileFormat(movido);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                }                
                if (baseFileFormat instanceof TAudioFileFormat)
                {
                    Map properties = ((TAudioFileFormat)baseFileFormat).properties();
                    String key = "author";
                    s = (String) properties.get(key);
                    key = "title";
                    as = (String) properties.get(key);                    
                }
                if ((as == null || as.equals("")) && (s == null || s.equals(""))) as = movido.getName();
                /*if (as == null || as.equals("")) as = "TituloDesconocido";
                if (s == null || s.equals("")) s = "IntérpreteDesconocido";*/
                
                Object o = new Object();
                String aux1 = String.valueOf(c.getId());
                o =  aux1 +" . "+ as + " - " + s;
                listaSoloT.add(as + " - " + s);
                lista.add((String) o);
                modelo.addElement(o);
                listaC.add(c);
            }else {
                modelo.addElement(c.getId() +" . Elemento_movido_o_dañado");
                lista.add(c.getId() +" . Elemento_movido_o_dañado");
                listaSoloT.add("Elemento_movido_o_dañado");
                listaC.add(c);
            }

        }
        l.eliminarCanciones();
        MoveMouseListener mml = new MoveMouseListener(jPanel4);
        jPanel4.addMouseListener(mml);
        jPanel4.addMouseMotionListener(mml);
        this.getRootPane().setBorder((Border) BorderFactory.createLineBorder( new Color(45,137,205),4));
        imagen = new JLabel();
        imagen.setIcon(new ImageIcon(getClass().getResource("/Imagenes/fondo.jpg")));
        imagen.setBounds(0,0,590,421);        
        this.add(imagen);
    }    


    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
       
        progressUpdate = (float) (bytesread * 1.0f / bytesLength * 1.0f);
        progressNow = bytesLength * progressUpdate;
        jSlider2.setValue((int) progressNow);
        writeDSP(pcmdata);
    }

    public void opened(Object arg0, Map arg1) {
        if (arg1.containsKey("audio.length.bytes")) {
            bytesLength = Double.parseDouble(arg1.get("audio.length.bytes").toString());            
            jSlider2.setMaximum((int) bytesLength);            
        }
        audioInfo = arg1;
    }
    
    public void stateUpdated(BasicPlayerEvent bpe) {
        //throw new UnsupportedOperationException("Not supported yet.");
        int state = bpe.getCode();
        if (state == BasicPlayerEvent.PLAYING)
        if (audioInfo.containsKey("basicplayer.sourcedataline")){
            linea = (SourceDataLine) audioInfo.get("basicplayer.sourcedataline");
            
        }

    }
    public void setupDSP(SourceDataLine line)
    {

        if (dsp != null)
        {
            int channels = line.getFormat().getChannels();
            if (channels == 1) dsp.setChannelMode(KJDigitalSignalProcessingAudioDataConsumer.CHANNEL_MODE_MONO);
            else dsp.setChannelMode(KJDigitalSignalProcessingAudioDataConsumer.CHANNEL_MODE_STEREO);
            int bits = line.getFormat().getSampleSizeInBits();
            if (bits == 8) dsp.setSampleType(KJDigitalSignalProcessingAudioDataConsumer.SAMPLE_TYPE_EIGHT_BIT);
            else dsp.setSampleType(KJDigitalSignalProcessingAudioDataConsumer.SAMPLE_TYPE_SIXTEEN_BIT);
        }
    }
    public void writeDSP(byte[] pcmdata)
    {
       dsp.writeAudioData(pcmdata);
    }


    public void setController(BasicController bc) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    
    public class MiTabla extends DefaultListModel {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
    }
    public boolean menor(int i){
        return (i < 10);
    }

    public void reloj(){
        String h;
        String m;
        String s;
        if (menor(segundos1)) s = "0"+segundos1;
        else s = "" + segundos1;
        if (menor(minutos1)) m = "0"+minutos1;
        else m = "" + minutos1;
        if (menor(horas1)) h = "0"+horas1;
        else h = "" + horas1;
        if (estado.equals("Play")) {
            if (segundos1 < 60) {                
                horas.setText(h+":"+m+":" +s);
            }else{
                segundos1 = 0;
                s="00";
                horas.setText(h+":"+m+":" +"00");
                minutos1++;
                if (menor(minutos1)) m = "0"+minutos1;
                else m = "" + minutos1;
                if (minutos1 < 60) {                    
                    horas.setText(h+":"+ m+":"+s);
                }else{
                    minutos1 = 0;
                    horas.setText(h+":"+ "00"+":" + s);
                    horas1++;
                    if (menor(horas1)) h = "0"+horas1;
                    else h = "" + horas1;
                    horas.setText(h+":"+m+":" +s);   
                }
            }
            segundos1++;
        }
   }
   public void rotarTexto(String s){
        char[] cadena = s.toCharArray();        
        char tmp = cadena[0];
        char tmp2;
        cadena[0] = cadena[s.length() -1];
        for (int i = 1; i < s.length();i++){
            tmp2 = cadena[i];
            cadena[i] = tmp;
            tmp = tmp2;
        }
        String aux = "";        
        for (int i = 0; i < s.length();i++){
            aux = aux.concat(String.valueOf(cadena[i]));
            
        }        
        info.setText(aux);
        ponerTitulo(aux);
   }
   public void ponerTitulo(String s){
       this.setTitle(s);
   }

   public class automatico extends Observable{
         public automatico(){
             Timer timer = new Timer();
             timer.scheduleAtFixedRate(timerTask, 0,1000);
         }
         TimerTask timerTask = new TimerTask(){
             @SuppressWarnings({ "deprecation", "static-access" })
                    public void run() {
                    if (listaC.size()>0){
                        reloj();                        
                        if (moverInfo){
                            rotarTexto(info.getText());
                        }                        
                        if (jSlider2.getValue() >= bytesLength){
                            if(cola.isEmpty()) {
                                if (shuffle) {
                                    jSlider2.setValue(0);
                                    segundos1 = 0;
                                    minutos1 = 0;
                                    horas1 = 0;

                                    horas.setText("00:00:00");

                                    Random r = new Random();
                                    int valorDado = r.nextInt(listaC.size())+1;
                                    int gainValue = jSlider1.getValue();
                                    int maxGain = jSlider1.getMaximum();
                                    jList1.setSelectedIndex(valorDado -1);
                                    try {
                                        String movido = lista.get(valorDado -1);
                                        if (!movido.equals(valorDado+" . Elemento_movido_o_dañado")) {                                            
                                            String s = clic.Reproducir(valorDado, gainValue, maxGain,listaC);
                                            String album = clic.getAlbum();
                                            int birate = clic.getBirate();
                                            long duracion = clic.getDuracion();
                                            String duracion1 = pasarAMinutos(duracion);
                                            setDatos(album,birate,duracion1);
                                            estaSonando = valorDado;
                                            estado = "Play";
                                            moverInfo = true;
                                            int largo = s.length();
                                            int resto = 25 - largo;
                                            s = ".:: "+duracion1+" :: "+ s + " :: "+labelBirate.getText()+" ::.";
                                            if (resto > 0) {
                                                for(int i = 0;i < (resto*2);i++){
                                                    s = s.concat(" ");
                                                }
                                            }else s = s.concat("   ");
                                            info.setText(s);
                                            ponerTitulo(s);
                                        }
                                    } catch (BasicPlayerException ex) {
                                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }else{
                                    jSlider2.setValue(0);
                                    segundos1 = 0;
                                    minutos1 = 0;
                                    horas1 = 0;
                                    horas.setText("00:00:00");
                                    int id = estaSonando;
                                    if (id == l.largo()){
                                        id = 1;
                                        jList1.setSelectedIndex(0);
                                    }
                                    else {
                                        id++;
                                        jList1.setSelectedIndex(id-1);
                                    }
                                    int gainValue = jSlider1.getValue();
                                    int maxGain = jSlider1.getMaximum();
                                    try {
                                        String movido = lista.get(id-1);
                                        if (!movido.equals(id+" . Elemento_movido_o_dañado")) {                                            
                                            String s = clic.Reproducir(id, gainValue, maxGain,listaC);
                                            String album = clic.getAlbum();
                                            int birate = clic.getBirate();
                                            long duracion = clic.getDuracion();
                                            String duracion1 = pasarAMinutos(duracion);
                                            setDatos(album,birate,duracion1);
                                            estaSonando = id;
                                            estado = "Play";
                                            moverInfo = true;
                                            int largo = s.length();
                                            int resto = 25 - largo;
                                            s = ".:: "+duracion1+" :: "+ s + " :: "+labelBirate.getText()+" ::.";
                                            if (resto > 0) {
                                                for(int i = 0;i < (resto*2);i++){
                                                    s = s.concat(" ");
                                                }
                                            }else s = s.concat("   ");
                                            info.setText(s);
                                            ponerTitulo(s);
                                        }
                                    } catch (BasicPlayerException ex) {
                                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }else {
                                String s = cola.get(0);
                                cola.remove(0); 
                                jComboBox1.removeItemAt(0);
                                if (cola.isEmpty()) jComboBox1.addItem("No hay canciones");
                                StringTokenizer st = new StringTokenizer(s);
                                int id = Integer.parseInt(st.nextToken());
                                jSlider2.setValue(0);
                                segundos1 = 0;
                                minutos1 = 0;
                                horas1 = 0;
                                horas.setText("00:00:00");
                                int gainValue = jSlider1.getValue();
                                int maxGain = jSlider1.getMaximum();
                                try {
                                    s = clic.Reproducir(id, gainValue, maxGain, listaC);
                                } catch (BasicPlayerException ex) {
                                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String album = clic.getAlbum();
                                int birate = clic.getBirate();
                                long duracion = clic.getDuracion();
                                String duracion1 = pasarAMinutos(duracion);
                                setDatos(album,birate,duracion1);
                                estaSonando = id;
                                estado = "Play";
                                moverInfo = true;
                                int largo = s.length();
                                int resto = 25 - largo;
                                s = ".:: "+duracion1+" :: "+ s + " :: "+labelBirate.getText()+" ::.";
                                if (resto > 0) {
                                    for(int i = 0;i < (resto*2);i++){
                                        s = s.concat(" ");
                                    }
                                }else s = s.concat("   ");
                                info.setText(s);
                                ponerTitulo(s);
                            }
                        }
                     }
             }
         };
    }
         

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        horas = new javax.swing.JTextField();
        info = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelBirate = new javax.swing.JLabel();
        labelTiempo = new javax.swing.JLabel();
        labelAlbum = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jSlider13 = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        jSlider2 = new javax.swing.JSlider();
        jPanel3 = new javax.swing.JPanel();
        play = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        back = new javax.swing.JButton();
        next = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        addFile = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        elimUno = new javax.swing.JButton();
        elimAll = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        modelo = new MiTabla();
        jList1 = new javax.swing.JList(modelo);
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AndresAmp v1.0");
        setBackground(new java.awt.Color(45, 137, 205));
        setUndecorated(true);
        setResizable(false);

        jPanel4.setForeground(new java.awt.Color(45, 137, 205));
        jPanel4.setOpaque(false);

        jPanel6.setOpaque(false);

        jPanel2.setOpaque(false);

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setOpaque(false);

        horas.setBackground(new java.awt.Color(0, 0, 0));
        horas.setEditable(false);
        horas.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        horas.setForeground(new java.awt.Color(207, 142, 76));
        horas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        horas.setText("00:00:00");
        horas.setBorder(null);
        horas.setOpaque(false);
        horas.setBounds(10, 0, 50, 20);
        jDesktopPane1.add(horas, javax.swing.JLayeredPane.DEFAULT_LAYER);

        info.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        info.setForeground(new java.awt.Color(207, 142, 76));
        info.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoMouseExited(evt);
            }
        });
        info.setBounds(0, 30, 150, 20);
        jDesktopPane1.add(info, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel5.setOpaque(false);

        jSlider1.setBackground(new java.awt.Color(26, 91, 134));
        jSlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jSlider1.setValue(25);
        jSlider1.setOpaque(false);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vol3.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel5.setBounds(210, 20, 40, 140);
        jDesktopPane1.add(jPanel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aleatorio.png"))); // NOI18N
        jLabel2.setToolTipText("Aleatorio On/Off");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jLabel2.setBounds(100, 90, 40, 0);
        jDesktopPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/listaA.png"))); // NOI18N
        jLabel4.setToolTipText("Mostrar/Ocultar PlayList");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jLabel4.setBounds(150, 90, 40, 50);
        jDesktopPane1.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        labelBirate.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        labelBirate.setForeground(new java.awt.Color(207, 142, 76));
        labelBirate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBirate.setText("kbps");
        labelBirate.setBounds(80, 0, 50, 20);
        jDesktopPane1.add(labelBirate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        labelTiempo.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        labelTiempo.setForeground(new java.awt.Color(207, 142, 76));
        labelTiempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTiempo.setText("   00:00:00");
        labelTiempo.setBounds(30, 84, 60, 20);
        jDesktopPane1.add(labelTiempo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        labelAlbum.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        labelAlbum.setForeground(new java.awt.Color(207, 142, 76));
        labelAlbum.setText("Album : ");
        labelAlbum.setBounds(0, 54, 150, 20);
        jDesktopPane1.add(labelAlbum, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jLabel6.setBounds(160, 0, 33, 18);
        jDesktopPane1.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jLabel7.setBounds(200, 0, 33, 18);
        jDesktopPane1.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel9.setOpaque(false);

        jSlider13.setMinimum(-100);
        jSlider13.setValue(0);
        jSlider13.setOpaque(false);
        jSlider13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSlider13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jSlider13, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSlider13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSlider13.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jPanel9.setBounds(30, 110, 70, 0);
        jDesktopPane1.add(jPanel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel8.setForeground(new java.awt.Color(207, 142, 76));
        jLabel8.setText("Center");
        jLabel8.setBounds(0, 114, 34, 20);
        jDesktopPane1.add(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jSlider2.setValue(0);
        jSlider2.setOpaque(false);
        jSlider2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSlider2MouseClicked(evt);
            }
        });
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setOpaque(false);

        play.setBackground(new java.awt.Color(0, 0, 0));
        play.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        play.setForeground(new java.awt.Color(255, 255, 255));
        play.setText(">");
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });

        stop.setBackground(new java.awt.Color(0, 0, 0));
        stop.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stop.setForeground(new java.awt.Color(255, 255, 255));
        stop.setText("◘");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        back.setBackground(new java.awt.Color(0, 0, 0));
        back.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setText("<<");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        next.setBackground(new java.awt.Color(0, 0, 0));
        next.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        next.setForeground(new java.awt.Color(255, 255, 255));
        next.setText(">>");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(play, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(back)
                .addGap(7, 7, 7)
                .addComponent(next)
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(play)
            .addComponent(stop)
            .addComponent(back)
            .addComponent(next)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setOpaque(false);

        addFile.setBackground(new java.awt.Color(0, 0, 0));
        addFile.setForeground(new java.awt.Color(255, 255, 255));
        addFile.setText("+");
        addFile.setToolTipText("Abrir archivos");
        addFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(45, 137, 205));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Buscar :");

        elimUno.setBackground(new java.awt.Color(0, 0, 0));
        elimUno.setForeground(new java.awt.Color(255, 255, 255));
        elimUno.setMnemonic('R');
        elimUno.setText("R");
        elimUno.setToolTipText("Eliminar la canción seleccionada");
        elimUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elimUnoActionPerformed(evt);
            }
        });

        elimAll.setBackground(new java.awt.Color(0, 0, 0));
        elimAll.setForeground(new java.awt.Color(255, 255, 255));
        elimAll.setText("Ra");
        elimAll.setToolTipText("Eliminar todas las canciones");
        elimAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elimAllActionPerformed(evt);
            }
        });

        edit.setBackground(new java.awt.Color(0, 0, 0));
        edit.setForeground(new java.awt.Color(255, 255, 255));
        edit.setText("Edit");
        edit.setToolTipText("Editar Canción seleccionada");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(69, 108, 140), java.awt.Color.white));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jList1.setBackground(new java.awt.Color(0, 0, 0));
        jList1.setForeground(new java.awt.Color(255, 255, 255));
        jList1.setOpaque(false);
        jList1.setSelectionBackground(new java.awt.Color(45, 137, 205));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jList1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Lista de Reproducción", jPanel7);

        jComboBox1.setForeground(new java.awt.Color(207, 142, 76));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"No hay canciones"}));
        jComboBox1.setToolTipText("Agregue canciones con la tecla Q");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cola :");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("x");
        jButton1.setToolTipText("Quitar de la Cola");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addFile, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(elimUno, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(elimAll, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addFile)
                    .addComponent(edit)
                    .addComponent(elimAll)
                    .addComponent(elimUno))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public class musicFilter implements FileFilter{
          private final String[] okFileExtensions = new String[] {"mp3"};
          public boolean accept(File file)
          {
            for (String extension : okFileExtensions)
            {
              if (file.getName().toLowerCase().endsWith(extension) || file.isDirectory())
              {
                return true;
              }
            }
            return false;
          }
    }
    public void listarRec(File f1,int c){
        musicFilter filter = new musicFilter();
        File[] f = f1.listFiles(filter);
        cont = c;
        String s = "";
        String as = "";
        for (int x=0;x<f.length;x++){
                //System.out.println(separador + ficheros[x].getName());
                if (f[x].isDirectory()){                        
                        listarRec(f[x],cont);
                }else if(f[x].isFile()){
                    
                    File ff = new File(f[x].getAbsolutePath());
                    AudioFileFormat baseFileFormat = null;
                    AudioFormat baseFormat = null;
                    try {
                        baseFileFormat = AudioSystem.getAudioFileFormat(ff);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    baseFormat = baseFileFormat.getFormat();
                    if (baseFileFormat instanceof TAudioFileFormat)
                    {
                        Map properties = ((TAudioFileFormat)baseFileFormat).properties();
                        String key = "author";
                        s = (String) properties.get(key);
                        key = "title";
                        as = (String) properties.get(key);                        
                    }
                    if ((as == null || as.equals("")) && (s == null || s.equals(""))) as = ff.getName();
                    /*if (s == null || s.equals("")) s = "TituloDesconocido";
                    if (as == null || as.equals("")) as = "IntérpreteDesconocido";*/
                    Cancion c1 = new Cancion();
                    c1.setNombre(f[x].getAbsolutePath());
                    int id = 0;
                    if (cont == 0) {
                        id = 1;
                    } else {
                        id = cont + 1;
                    }
                    c1.setId(id);
                    //l.guardarCancion(c1);
                    Object o = new Object();
                    String aux = String.valueOf(id);
                    o = aux + " . " + as + " - " + s;
                    listaSoloT.add(as + " - " + s);
                    listaC.add(c1);
                    modelo.addElement(o);
                    lista.add((String) o);
                    cont++;
                }
        }
    }
    private void addFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileActionPerformed
        // TODO add your handling code here:
        fc1 = new JFileChooser();
        fc1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos *MP3", "mp3");
        fc1.setFileFilter(filter);
        fc1.setMultiSelectionEnabled(true);        
        int returnVal = fc1.showOpenDialog(null);        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            int largo = fc1.getSelectedFiles().length;
            File[] f = new File[largo];
            f = fc1.getSelectedFiles();            
            cont = listaC.size();
            for (int i = 0;i < largo;i++){
                String s = "";
                String as = "";               
                if (f[i].isFile()){
                    String ruta = f[i].getAbsolutePath().replace('\'', '?');
                    File ff = new File(ruta);
                    AudioFileFormat baseFileFormat = null;
                    AudioFormat baseFormat = null;
                    try {
                        baseFileFormat = AudioSystem.getAudioFileFormat(ff);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    baseFormat = baseFileFormat.getFormat();
                    if (baseFileFormat instanceof TAudioFileFormat)
                    {
                        Map properties = ((TAudioFileFormat)baseFileFormat).properties();
                        String key = "author";
                        s = (String) properties.get(key);
                        key = "title";
                        as = (String) properties.get(key);                                                
                    }
                    if ((as == null || as.equals("")) && (s == null || s.equals(""))) as = ff.getName();
                    /*if (as == null || as.equals("")) as = "TituloDesconocido";
                    if (s == null || s.equals("")) s = "IntérpreteDesconocido";*/
                    Cancion c = new Cancion();
                    c.setNombre(ruta);
                    int id = 0;
                    if (cont == 0) {
                        id = 1;
                    } else {
                        id = cont + 1;
                    }
                    c.setId(id);                    
                    Object o = new Object();
                    String aux = String.valueOf(id);
                    o = aux + " . " + as + " - " + s;
                    listaSoloT.add(as + " - " + s);
                    modelo.addElement(o);
                    lista.add((String) o);
                    listaC.add(c);                    
                    cont++;
                }
                else {
                    listarRec(f[i],cont);
                }                                                                                                                  
            }
            
        }        
    }//GEN-LAST:event_addFileActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2){            
            reproducir();            
        }else{
            int fila = jList1.getSelectedIndex();
            if (fila > -1) {
                idSel = fila;
                String movido = (String)modelo.getElementAt(fila);
                StringTokenizer st = new StringTokenizer(movido);
                int id = Integer.parseInt(st.nextToken());
                
                    File f = new File(listaC.get(id-1).getNombre());
                    if (!f.isFile()) {
                        modelo.setElementAt(id+" . Elemento_movido_o_dañado", fila);
                    }
                
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        if (lista.size()>0){            
            if (estado.equals("Play") || estado.equals("Pause")){
                try {
                    clic.stop();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
                int valorDado;
                int gainValue = jSlider1.getValue();
                int maxGain = jSlider1.getMaximum();
                if(cola.isEmpty()){
                    if (shuffle){
                        jSlider2.setValue(0);
                        Random r = new Random();
                        valorDado = r.nextInt(lista.size())+1;
                        jList1.setSelectedIndex(valorDado -1);
                    }else{
                        valorDado = estaSonando;

                        if (valorDado == 1){
                            valorDado = lista.size();
                            jList1.setSelectedIndex(lista.size()-1);

                        }
                        else {
                            valorDado--;
                            jList1.setSelectedIndex(valorDado-1);
                        }
                    }
                }else{
                    String s = cola.get(0);
                    cola.remove(0);
                    jComboBox1.removeItemAt(0);
                    if (cola.isEmpty()) jComboBox1.addItem("No hay canciones");
                    StringTokenizer st = new StringTokenizer(s);
                    valorDado = Integer.parseInt(st.nextToken());
                }
                String movido = (String)lista.get(valorDado-1);
                if (!movido.equals((valorDado)+" . Elemento_movido_o_dañado")) {
                    try {
                        next(valorDado, gainValue, maxGain);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else JOptionPane.showMessageDialog(this,"Archivo no disponible para reproducir.","Error",JOptionPane.ERROR_MESSAGE);

            }else if (estado.equals("Stop")){
                int valorDado;
                int gainValue = jSlider1.getValue();
                int maxGain = jSlider1.getMaximum();
                if(cola.isEmpty()){
                    if (shuffle){
                        jSlider2.setValue(0);
                        Random r = new Random();
                        valorDado = r.nextInt(lista.size())+1;
                        jList1.setSelectedIndex(valorDado -1);
                    }else{
                        valorDado = jList1.getSelectedIndex();
                        if(valorDado == -1) valorDado = 0;
                        StringTokenizer st = new StringTokenizer((String)modelo.getElementAt(valorDado));
                        valorDado = Integer.parseInt(st.nextToken());
                        if (valorDado == 1){
                            valorDado = lista.size();
                            jList1.setSelectedIndex(lista.size() -1);
                        }
                        else {
                            valorDado--;
                            jList1.setSelectedIndex(valorDado-1);
                        }
                    }
                }else{
                    String s = cola.get(0);
                    cola.remove(0);
                    jComboBox1.removeItemAt(0);
                    if (cola.isEmpty()) jComboBox1.addItem("No hay canciones");
                    StringTokenizer st = new StringTokenizer(s);
                    valorDado = Integer.parseInt(st.nextToken());
                }

                String movido = lista.get(valorDado-1);
                if (!movido.equals((valorDado)+" . Elemento_movido_o_dañado")) {
                    try {
                        next(valorDado, gainValue, maxGain);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else JOptionPane.showMessageDialog(this,"Archivo no disponible para reproducir.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_backActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        // TODO add your handling code here:
        //if (suena) {
        Object scr = evt.getSource();
        if (scr == jSlider1){
            int gainValue = jSlider1.getValue();
            
            int maxGain = jSlider1.getMaximum();
            try {
                if (gainValue == 0)
                {
                    clic.setVolumen(0);
                    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vol4.png")));
                }
                else
                {
                    clic.setVolumen((float) ((double) gainValue / (double) maxGain));
                    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vol3.png")));
                    //config.setVolume(gainValue);
                    vol = gainValue;
                }
                
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else  if(scr == jSlider13){
            double valor = jSlider13.getValue();
            if (valor == 0) jLabel8.setText("Center");
            else{
                jLabel8.setText(String.valueOf(valor));
            }
            clic.setPan(valor/100);

        }
    }//GEN-LAST:event_jSlider1StateChanged
    public String pasarAMinutos(long d){                
        int mili = (int) (d / 1000);
        int seg = (mili / 1000) % 60;
        int min = (mili / 1000) / 60;
        String se = ""+seg;        
        String minu = ""+min;
        if (seg < 10)  se = "0"+seg;
        if (min < 10)  minu = "0"+min;        

        return "00:"+minu+":"+se;



    }
    public void setDatos(String unA,int unBi,String unHo){
        labelTiempo.setText("   "+unHo);
        labelBirate.setText(String.valueOf(unBi/1000)+"kbps");
        labelAlbum.setText("Album : "+unA);
        labelAlbum.setToolTipText(unA);
    }
    public void reproducir(){
        if (lista.size()>0){
            int fila = jList1.getSelectedIndex();
            if (fila > -1) {
                idSel = fila;
                String movido = (String)modelo.getElementAt(fila);
                StringTokenizer st = new StringTokenizer(movido);
                final int id = Integer.parseInt(st.nextToken());
                String auxi = st.nextToken(); // el .                
                auxi = st.nextToken(); // el resto                
                if (!auxi.equals("Elemento_movido_o_dañado")) {
                    int gainValue = jSlider1.getValue();
                    int maxGain = jSlider1.getMaximum();
                    try {                        
                        String s = clic.Reproducir(id, gainValue, maxGain,listaC);
                        //setupDSP(linea);
/*
                        try {
                           AudioFormat format = new AudioFormat(44100, 16, 2, true, false) ;
                           SourceDataLine line = null ;
                            try {
                                line = AudioSystem.getSourceDataLine(format);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                           line. open() ;
                           line. start() ;
                           // now you may write data to the line
                           int size = line. getBufferSize() ;
                            byte[] data = new byte[ size] ;
                            java.util.Random random = new java.util.Random() ;
                             
                            
                            for (int i=0;i<10; i++) {
                               random.nextBytes(data) ;
                               line.write(data, 0, size) ;
                            }
                            line. drain() ;
                           // insert code here to generate audio data
                           line. drain() ;   // send out any data still in the buffer
                           // when you are done, drain and close the line
                           //line. close() ;   // close the line
                        } catch (LineUnavailableException e) {
                           e. printStackTrace( ) ;
                        }
*/

                        jList1.requestFocus();
                        String album = clic.getAlbum();
                        int birate = clic.getBirate();
                        long duracion = clic.getDuracion();
                        String duracion1 = pasarAMinutos(duracion);
                        setDatos(album,birate,duracion1);
                        estaSonando = id;                        
                        int largo = s.length();
                        int resto = 25 - largo;
                        s = ".:: "+duracion1+" :: "+ s + " :: "+labelBirate.getText()+" ::.";
                        if (resto > 0) {
                            for(int i = 0;i < (resto*2);i++){
                                s = s.concat(" ");
                            }
                        }else s = s.concat("   ");
                        info.setText(s);
                        ponerTitulo(s);
                        segundos1 = 0;
                        minutos1 = 0;
                        horas1 = 0;
                        horas.setText("00:00:00");
                        reloj();
                        if (estado.equals("Stop"))
                            a = new automatico();
                        estado = "Play";
                        play.setText("||");
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else JOptionPane.showMessageDialog(this,"Archivo no disponible para reproduccion.","Error",JOptionPane.ERROR_MESSAGE);
            }else estado = "Stop";
        }
    }


    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        // TODO add your handling code here:
        if (lista.size()>0){
            if (estado.equals("Pause")){
                try {
                    clic.resume();
                    estado = "Play";
                    play.setText("||");
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if (estado.equals("Stop")) {
                estado = "Play";
                reproducir();                
                play.setText(">");
            }else if (estado.equals("Play")){
                try {
                    clic.pausa();
                    play.setText(">");
                    estado = "Pause";
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
        }

    }//GEN-LAST:event_playActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        if (!silencio) {
            
            try {
                clic.setVolumen((float) 0);
                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vol4.png")));
                silencio = true;
                jSlider1.setValue(0);
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{            
            int maxGain = jSlider1.getMaximum();
            try {
                clic.setVolumen((float)vol/maxGain);
                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vol3.png")));
                silencio = false;
            jSlider1.setValue(vol);
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void infoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoMouseEntered
        // TODO add your handling code here:
        moverInfo = false;
    }//GEN-LAST:event_infoMouseEntered

    private void infoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoMouseExited
        // TODO add your handling code here:
        moverInfo = true;
    }//GEN-LAST:event_infoMouseExited

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        // TODO add your handling code here:        
        if (estado.equals("Play") || estado.equals("Pause")){
            try {
                play.setText(">");
                clic.stop();
                a.timerTask.cancel();
                info.setText("Stop");
                ponerTitulo("Reproductor v1.0");
                segundos1 = 0;
                minutos1 = 0;
                horas1 = 0;                
                horas.setText("00:00:00");
                estado = "Stop";
                jSlider2.setValue(0);
                estaSonando = -1;
                labelTiempo.setText("   "+"00:00:00");
                labelBirate.setText("    kbps");
                labelAlbum.setText("Album : ");
                
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_stopActionPerformed
    public void next(int id,int gainValue,int maxGain) throws BasicPlayerException{
        String s = clic.Reproducir(id, gainValue, maxGain,listaC);
        String album = clic.getAlbum();
        int birate = clic.getBirate();
        long duracion = clic.getDuracion();
        String duracion1 = pasarAMinutos(duracion);
        setDatos(album,birate,duracion1);
        estaSonando = id;        
        int largo = s.length();
        int resto = 25 - largo;
        s = ".:: "+duracion1+" :: "+ s + " :: "+labelBirate.getText()+" ::.";
        if (resto > 0) {
            for(int i = 0;i < (resto*2);i++){
                s = s.concat(" ");
            }
        }else s = s.concat("   ");
        info.setText(s);
        ponerTitulo(s);
        segundos1 = 0;
        minutos1 = 0;
        horas1 = 0;
        horas.setText("00:00:00");
        reloj();
        if (estado.equals("Stop"))
            a = new automatico();
        estado = "Play";
    }
    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        // TODO add your handling code here:
        if (lista.size()>0){
            if (estado.equals("Play") || estado.equals("Pause")){
                try {
                    clic.stop();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
                int valorDado;
                int gainValue = jSlider1.getValue();
                int maxGain = jSlider1.getMaximum();
                if(cola.isEmpty()){
                    if (shuffle){
                        jSlider2.setValue(0);
                        Random r = new Random();
                        valorDado = r.nextInt(lista.size())+1;
                        jList1.setSelectedIndex(valorDado -1);
                    }else{
                        valorDado = estaSonando;
                        if (valorDado == lista.size()){
                            valorDado = 1;
                            jList1.setSelectedIndex(0);
                        }
                        else {
                            valorDado++;
                            jList1.setSelectedIndex(valorDado-1);
                        }
                    }
                }else{
                    String s = cola.get(0);
                    cola.remove(0);
                    jComboBox1.removeItemAt(0);
                    if (cola.isEmpty()) jComboBox1.addItem("No hay canciones");
                    StringTokenizer st = new StringTokenizer(s);
                    valorDado = Integer.parseInt(st.nextToken());
                }
                String movido = lista.get(valorDado-1);
                if (!movido.equals((valorDado)+" . Elemento_movido_o_dañado")) {
                    try {
                        next(valorDado, gainValue, maxGain);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else JOptionPane.showMessageDialog(this,"Archivo no disponible para reproducir.","Error",JOptionPane.ERROR_MESSAGE);

            }else if (estado.equals("Stop")){
                int valorDado;
                int gainValue = jSlider1.getValue();
                int maxGain = jSlider1.getMaximum();
                if(cola.isEmpty()){
                    if (shuffle){
                        jSlider2.setValue(0);
                        Random r = new Random();
                        valorDado = r.nextInt(lista.size())+1;
                        jList1.setSelectedIndex(valorDado -1);
                    }else{
                        valorDado = jList1.getSelectedIndex();
                        int indice;
                        if(valorDado == -1)
                            indice = 0;
                        else indice = valorDado;
                        StringTokenizer st = new StringTokenizer((String)modelo.getElementAt(indice));
                        valorDado = Integer.parseInt(st.nextToken());
                        if (valorDado == 1 || valorDado == lista.size()){
                            valorDado = 1;
                            jList1.setSelectedIndex(0);
                        }
                        else {
                            valorDado++;
                            jList1.setSelectedIndex(valorDado-1);
                        }
                    }
                }else{
                    String s = cola.get(0);
                    cola.remove(0);
                    jComboBox1.removeItemAt(0);
                    if (cola.isEmpty()) jComboBox1.addItem("No hay canciones");
                    StringTokenizer st = new StringTokenizer(s);
                    valorDado = Integer.parseInt(st.nextToken());
                }
                String movido = lista.get(valorDado-1);
                if (!movido.equals((valorDado)+" . Elemento_movido_o_dañado")) {
                    try {
                        next(valorDado, gainValue, maxGain);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else JOptionPane.showMessageDialog(this,"Archivo no disponible para reproducir.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_nextActionPerformed

    private void elimUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elimUnoActionPerformed
        // TODO add your handling code here:
        if (lista.size() > 0) {
            int fila = jList1.getSelectedIndex();
            if ((fila+1) == estaSonando) estaSonando = -1;
            if (fila > -1) {                
                String texto;
                String nom = (String)modelo.getElementAt(fila);
                StringTokenizer st = new StringTokenizer(nom);
                int id = Integer.parseInt(st.nextToken());
                //System.out.println("id primera  " + id);
                int largo = lista.size();
                modelo.remove(fila);
                lista.remove(id-1);
                listaC.remove(id-1);
                listaSoloT.remove(id-1);
                int la = cola.size();
                ArrayList<String> auxiliar = new ArrayList<String>();
                for (int n = 0;n<la;n++){
                    if (!nom.equals(cola.get(n))){
                        auxiliar.add(cola.get(n));    
                    }
                }
                cola = auxiliar;
                if (cola.isEmpty()) jComboBox1.addItem("No hay canciones");
                else{
                    jComboBox1.removeAllItems();
                    for (int n = 0; n<cola.size();n++){
                        jComboBox1.addItem(cola.get(n));
                    }
                }
                if (id < largo){
                    largo = lista.size();
                    if(jTextField1.getText().equals("")){
                        for(int i = (id); i <= largo;i++ ){
                            texto = i+" . "+listaSoloT.get(i-1);
                            listaC.get(i-1).setId(listaC.get(i-1).getId()-1); 
                            modelo.set(i-1, texto);
                            lista.set(i-1, texto);
                        }
                    }else{                        
                        for(int i = id; i <= largo;i++ ){
                            texto = (i)+" . "+listaSoloT.get(i-1);
                            listaC.get(i-1).setId(listaC.get(i-1).getId()-1);
                            lista.set(i-1, texto);
                        }
                        nom = jTextField1.getText();
                        jTextField1.setText("");
                        jTextField1.setText(nom);
                    }
                }
            }
        }
    }//GEN-LAST:event_elimUnoActionPerformed

    private void elimAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elimAllActionPerformed
        // TODO add your handling code here:
        if (lista.size() > 0){
             modelo.removeAllElements();
             listaC.removeAll(listaC);
             lista.removeAll(lista);            
             estaSonando = -1;
             cola.removeAll(cola);
             jComboBox1.removeAllItems();
             jComboBox1.addItem("No hay canciones");
        }
       
    }//GEN-LAST:event_elimAllActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        // TODO add your handling code here:

        if (lista.size() > 0){
            final int fila = jList1.getSelectedIndex();
            if (fila > -1) {
                final String movido = (String)modelo.getElementAt(fila);
                StringTokenizer st = new StringTokenizer(movido);
                final int id = Integer.parseInt(st.nextToken());
                String auxi = st.nextToken(); // el .
                auxi = st.nextToken(); // el resto
                if (!auxi.equals("Elemento_movido_o_dañado")) {
                    int i = 0;
                    String ruta = "";
                    boolean noEncontre = false;
                    while(i < listaC.size() && !noEncontre){
                        Cancion c = listaC.get(i);
                        if (c.getId() == id){
                            ruta = c.getNombre();
                            noEncontre = true;
                        }
                        i++;
                    }
                    ruta = ruta.replace('?', '\'');
                    File archivo = new File(ruta);
                    String nombre = "";
                    String artista = "";
                    String album = "";
                    String año = "";
                    String comentario = "";
                    AudioFileFormat baseFileFormat = null;                    
                    try {
                        baseFileFormat = AudioSystem.getAudioFileFormat(archivo);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                    if (baseFileFormat instanceof TAudioFileFormat)
                    {
                        Map properties = ((TAudioFileFormat)baseFileFormat).properties();
                        String key = "author";
                        artista = (String) properties.get(key);
                        key = "title";
                        nombre = (String) properties.get(key);
                        key = "album";
                        album = (String) properties.get(key);
                        key = "date";
                        año = (String) properties.get(key);
                        key = "comment";
                        comentario = (String) properties.get(key);
                        
                    }                    
                    if (nombre == null || nombre.equals("")) nombre = "";
                    if (artista == null || artista.equals("")) artista = "";
                    if (album == null || album.equals("")) album = "";
                    if (año== null || año.equals("")) año = "";
                    if (comentario== null || comentario.equals("")) comentario = "";

                    final MediaFile mediaFile = new MP3File(archivo) ;
                    final EditarCancion v = new EditarCancion();
                    v.setLocationRelativeTo(this);
                    v.setVisible(true);
                    this.setEnabled(false);
                    escucharVentanas em = new escucharVentanas(v);
                    v.addWindowListener(em);
                    v.jTextField1.setText(ruta);
                    v.jTextField2.setText(nombre);
                    v.jTextField3.setText(artista);
                    v.jTextField4.setText(album);
                    v.jTextField6.setText(año);
                    v.jTextArea1.setText(comentario);
                    v.cancelar.addActionListener(new java.awt.event.ActionListener(){
                        public void actionPerformed(java.awt.event.ActionEvent  evt) {
                            v.dispose();
                        }
                    });
                    v.aceptar.addActionListener(new java.awt.event.ActionListener(){
                        public void actionPerformed(java.awt.event.ActionEvent  evt) {
                            //System.out.println("id : "+id);
                            //System.out.println("suena : "+estaSonando);
                            if ((estaSonando) == id && estaSonando != -1){
                                if (estado.equals("Play") || estado.equals("Pause")){
                                    double prog = progressNow;
                                    try {
                                        clic.stop();
                                    } catch (BasicPlayerException ex) {
                                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    ID3V2_3_0Tag tag2 = new ID3V2_3_0Tag();
                                    try {
                                    tag2.setTitle(v.jTextField2.getText());
                                    tag2.setArtist(v.jTextField3.getText());
                                    tag2.setAlbum(v.jTextField4.getText());
                                    if (!v.jTextField6.getText().equals(""))
                                        tag2.setYear(Integer.parseInt(v.jTextField6.getText()));
                                    tag2.setComment(v.jTextArea1.getText());
                                    } catch (ID3Exception ex) {
                                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    mediaFile.setID3Tag(tag2);
                                    try {
                                        mediaFile.sync();
                                    } catch (ID3Exception ex) {
                                        Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    clic.despDeEditar(estaSonando,listaC);
                                    clic.adelantar(prog);
                                    modelo.setElementAt(estaSonando +" . "+ v.jTextField3.getText()  +" - "+v.jTextField2.getText() ,fila);
                                    String s = v.jTextField2.getText()  +" :: "+v.jTextField3.getText();
                                    int largo = s.length();
                                    int resto = 25 - largo;
                                    s = ".:: "+labelTiempo.getText()+" :: "+ s + " :: "+labelBirate.getText()+" ::.";
                                    if (resto > 0) {
                                        for(int i = 0;i < (resto*2);i++){
                                            s = s.concat(" ");
                                        }
                                    }else s = s.concat("   ");
                                    info.setText(s);
                                    ponerTitulo(s);
                                }
                            }else if ((estaSonando) != id){

                                ID3V2_3_0Tag tag2 = new ID3V2_3_0Tag();
                                try {
                                tag2.setTitle(v.jTextField2.getText());
                                tag2.setArtist(v.jTextField3.getText());
                                tag2.setAlbum(v.jTextField4.getText());
                                if (!v.jTextField6.getText().equals(""))
                                    tag2.setYear(Integer.parseInt(v.jTextField6.getText()));
                                tag2.setComment(v.jTextArea1.getText());
                                } catch (ID3Exception ex) {
                                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                mediaFile.setID3Tag(tag2);
                                try {
                                    mediaFile.sync();
                                } catch (ID3Exception ex) {
                                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                    modelo.setElementAt((id) +" . "+ v.jTextField2.getText()  +" - "+v.jTextField3.getText() ,fila);
                             }
                             int la = cola.size();
                             jComboBox1.removeAllItems();
                             for (int n = 0;n<la;n++){
                                 if(cola.get(n).equals(movido)){
                                     cola.set(n,(id) +" . "+ v.jTextField2.getText()  +" - "+v.jTextField3.getText());
                                 }
                                 jComboBox1.addItem(cola.get(n)); 
                             }

                             lista.set(id-1,(id) +" . "+ v.jTextField2.getText()  +" - "+v.jTextField3.getText());
                             listaSoloT.set(id-1, v.jTextField2.getText()  +" - "+v.jTextField3.getText());
                             v.dispose();
                         }
                    });
                     
                }else{
                    JOptionPane.showMessageDialog(this,"Archivo no disponible para edición.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_editActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        if (lista.size() > 0){
            modelo.removeAllElements();
            if(!jTextField1.getText().equals("")){
                String text = jTextField1.getText().toUpperCase();
                for (int i = 0; i < lista.size();i++){
                    String s = lista.get(i).toUpperCase();
                    String dato = lista.get(i);
                    if (s.indexOf(text)!= -1)  {
                        modelo.addElement(dato);
                    }
                }
            }else{
                modelo.removeAllElements();
                for (int i = 0; i < lista.size();i++){
                    String s = lista.get(i);
                    modelo.addElement(s);
                }
            }
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        if (!shuffle) {
            shuffle = true;
            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aleatorio1.png")));
        }else{
            shuffle = false;
            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aleatorio.png")));

        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        if (!playlist) {
            playlist = true;
            jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/listaA.png")));
            jPanel1.setVisible(true);            
            this.setSize(595, 418);
            
            imagen.setBounds(0,0,590,418);
        }else{
            playlist = false;            
            jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/listaN.png")));
            jPanel1.setVisible(false);            
            this.setSize(304, 418);
            imagen.setBounds(-298,0,590,418);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jSlider2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider2MouseClicked
        // TODO add your handling code here:
        if (estado.equals("Play")|| estado.equals("Pause")){
            int v = (int) ((evt.getX() * bytesLength) / 267);
            clic.adelantar(v);
            if (estado.equals("Play") || estado.equals("Pause")){
                long segundoAAdelantar = (long) ((((evt.getX() * bytesLength) / 267)* clic.getDuracion()) / bytesLength);
                int mili = (int) (segundoAAdelantar/1000);
                segundos1 = (mili/1000)%60;
                minutos1 = (mili/1000)/60;
            }
        }
    }//GEN-LAST:event_jSlider2MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        setExtendedState(JFrame.CROSSHAIR_CURSOR);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        if (estado.equals("Play") || estado.equals("Pause"))
            try {
            clic.stop();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < listaC.size();i++){
            l.guardarCancion(listaC.get(i));
        }
        System.exit(0);
    }//GEN-LAST:event_jLabel7MouseClicked
    
    private void jList1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyTyped
        // TODO add your handling code here:        
        switch (evt.getKeyChar()){
            case 'q': if (jList1.getSelectedIndex() >= 0){
                          String nom = (String)modelo.getElementAt(jList1.getSelectedIndex());
                          StringTokenizer st = new StringTokenizer(nom);
                          st.nextToken();
                          st.nextToken();
                          if (!st.nextToken().equals("Elemento_movido_o_dañado")){
                            if (cola.isEmpty())
                                jComboBox1.removeAllItems();
                            cola.add(nom);
                            jComboBox1.addItem(nom);
                          }
                      }
                      break;
            case 'x': play.doClick();
                      break;
            case 'c': stop.doClick();
                      break;
            case 'v': back.doClick();
                      break;
            case 'b': next.doClick();
                      break;
            case 's': if (!shuffle) {
                            shuffle = true;
                            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aleatorio1.png")));
                      }else{
                            shuffle = false;
                            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aleatorio.png")));

                      }break;
            case 'l': if (!playlist) {
                            playlist = true;
                            jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/listaA.png")));
                            jPanel1.setVisible(true);
                            this.setSize(595, 418);
                            imagen.setBounds(0,0,590,418);
                      }else{
                            playlist = false;
                            jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/listaN.png")));
                            jPanel1.setVisible(false);
                            this.setSize(305, 418);
                            imagen.setBounds(-294,0,590,418);
                      }break;
            default:                       
        }
    }//GEN-LAST:event_jList1KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!cola.isEmpty()){
            String s = (String) jComboBox1.getSelectedItem();
            jComboBox1.removeItemAt(jComboBox1.getSelectedIndex());
            int i = 0;           
            boolean bander = false;
            while (!bander || i == cola.size()){
                if (s.equals(cola.get(i))){
                    cola.remove(i);
                    bander = true;
                }
                i++;
            }
            if (cola.isEmpty()){
                jComboBox1.addItem("No hay canciones");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10){
            if (estado.equals("Play") || estado.equals("Pause")){
                try {
                    clic.stop();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            reproducir();
        }
    }//GEN-LAST:event_jList1KeyPressed

    private void jSlider13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider13MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2){
            clic.setPan(0);
            jSlider13.setValue(0);
        }
    }//GEN-LAST:event_jSlider13MouseClicked
    
    private void setVi(boolean b) {
        this.setEnabled(b);
        this.toFront();
    }
    private void noVi(){
        this.setVisible(false);
    }
    public class escucharVentanas implements WindowListener {
	    JFrame gen;
	    public escucharVentanas(JFrame origen) {
	        gen = origen;
	    }

	    public void windowClosed(WindowEvent e) {
	    	if (gen.getTitle().equals("Editar")){
                    setVi(true);
                }
	    }
	    public void windowActivated(WindowEvent e) {}public void windowClosing(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
        public void windowOpened(WindowEvent e) {

        }
    }
    public class seCerroVentana implements WindowListener {
	    JFrame gen;
	    public seCerroVentana(JFrame origen) {
	        gen = origen;
	    }
	    public void windowClosed(WindowEvent e) {}
	    public void windowActivated(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {
                noVi();
                if (estado.equals("Play") || estado.equals("Pause"))
                    try {
                    clic.stop();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (int i = 0; i < listaC.size();i++){
                    l.guardarCancion(listaC.get(i));
                }
            }
            public void windowDeactivated(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowOpened(WindowEvent e) {
        }
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFile;
    private javax.swing.JButton back;
    private javax.swing.JButton edit;
    private javax.swing.JButton elimAll;
    private javax.swing.JButton elimUno;
    private javax.swing.JTextField horas;
    private javax.swing.JLabel info;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider13;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelAlbum;
    private javax.swing.JLabel labelBirate;
    private javax.swing.JLabel labelTiempo;
    private javax.swing.JButton next;
    private javax.swing.JButton play;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables
    public DefaultListModel modelo;
    public int idSel = -1;
    public ListaCanciones l;
    JFileChooser fc1;        
    automatico a;
    public int u = 0;
    boolean silencio = false;
    int vol;
    boolean moverInfo = true;
    public String estado = "Stop";
    int estaSonando = -1;
    ArrayList<String> lista = new ArrayList<String>();
    ArrayList<String> listaSoloT = new ArrayList<String>();
    int cont;
    boolean playlist = true;
    
}
