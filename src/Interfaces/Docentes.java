/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Clases.Foto;
import Clases.Reportes;
import Conexion.Conexion;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author rene
 */
public class Docentes extends javax.swing.JFrame {

    //Atributos para conexion de docente
    Conexion con = new Conexion();
    CallableStatement cadSql;
    ResultSet rs;
    Statement sentencia;
    DefaultTableModel m;
    FileInputStream fis;
    Foto f=new Foto();
    
    
 
    
    
    //variable para cargar las imagenes
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de imagen", "jpg");
    String rutaImagen;

    /**
     * Creates new form Docentes
     */
    public Docentes() {
        initComponents();
        cargarDocentes();
        btnNuevo.setEnabled(false);
        ocultarMensajes();
        rutaImagen = "";
        this.setLocationRelativeTo(null);
         btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
    }

    public void ocultarMensajes() {
        lblErrorCedula.setVisible(false);
        lblErrorTelefono.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelPrincipal = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnCargarImagen = new javax.swing.JButton();
        txtRutaImagen = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        btnGrabar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblFotos = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        lblErrorTelefono = new javax.swing.JLabel();
        lblErrorCedula = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocentes = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnExportar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnHistorial = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setBackground(new java.awt.Color(253, 253, 253));
        panelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(51, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("REGISTRO DE DOCENTES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(376, 376, 376)
                .addComponent(jLabel1)
                .addContainerGap(436, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelPrincipal.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 50));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Apellido:");
        panelPrincipal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nombre:");
        panelPrincipal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Direccion:");
        panelPrincipal.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Telefono:");
        panelPrincipal.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Cedula:");
        panelPrincipal.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyReleased(evt);
            }
        });
        panelPrincipal.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 150, 30));

        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCedulaKeyReleased(evt);
            }
        });
        panelPrincipal.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 150, 30));
        panelPrincipal.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 270, 30));
        panelPrincipal.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 270, 30));
        panelPrincipal.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 270, 30));

        btnCargarImagen.setText("+ Imagen");
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnCargarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, -1, -1));
        panelPrincipal.add(txtRutaImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 350, 160, -1));

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });
        panelPrincipal.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 80, 120, 40));

        btnGrabar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        btnGrabar.setText("GRABAR");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 120, 40));

        btnModificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/editar.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        panelPrincipal.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 120, 40));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        panelPrincipal.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, 40));

        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/limpiar.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
        });
        panelPrincipal.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 140, 40));

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizar.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZA BD");
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
        });
        panelPrincipal.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 80, -1, 40));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFotos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/userimage.png"))); // NOI18N
        lblFotos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblFotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 150));

        panelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, 130, 150));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelPrincipal.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 380, 100, 30));

        jTextField1.setText("jTextField1");
        panelPrincipal.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 380, 210, 30));

        lblErrorTelefono.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblErrorTelefono.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorTelefono.setText("X       Ingrese 10 digitos");
        panelPrincipal.add(lblErrorTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, 140, -1));

        lblErrorCedula.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblErrorCedula.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorCedula.setText("X     Ingrese 10 digitos");
        panelPrincipal.add(lblErrorCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 130, -1));

        tblDocentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDocentesMousePressed(evt);
            }
        });
        tblDocentes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDocentesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocentes);

        panelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 930, 180));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Docente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        btnExportar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        btnExportar.setText("Exportar ");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPrincipal.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 210, 100));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Docente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelPrincipal.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 410, 290));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Docente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        btnHistorial.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/historial.png"))); // NOI18N
        btnHistorial.setText("Ver Histrorial");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelPrincipal.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 270, -1, 100));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevoUsuario.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 60, 40));

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed

        //Creamos un objeto de JfileChooser
        JFileChooser dlg = new JFileChooser();
        //del objeto creado vamoas a llamar al metodo setFileFilter
        dlg.setFileFilter(filter);
        //abriremos la ventana de dialogo para escojer la imagen
        int option = dlg.showOpenDialog(this);
        //Si hacemos click en el boton abrir
        if (option == JFileChooser.APPROVE_OPTION) {
            //obtener el nombre del arechivo
            String fil = dlg.getSelectedFile().getPath();

            /*
             //obtener la dirrecion donde se guardara la imagen
             String file=dlg.getSelectedFile().toString();
             //cargamos la imagen seleccionada al label No es necdesario
             lblFotos.setIcon(new ImageIcon(file));           
             */

            //modificamos la imagen
            ImageIcon icon = new ImageIcon(fil);


            //Extraer la imagen del icono
            Image img = icon.getImage();
            //cambiamos el tamanio
            Image nuevaImagen = img.getScaledInstance(130, 150, java.awt.Image.SCALE_SMOOTH);
            //se genera un image icon con la nueva imagen
            ImageIcon nuevoIcon = new ImageIcon(nuevaImagen);
            lblFotos.setIcon(nuevoIcon);
            lblFotos.setSize(130, 150);
            txtRutaImagen.setText(fil);
            rutaImagen = "";


        }


    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirMouseClicked

    private void validarNumeros(JTextField t, java.awt.event.KeyEvent evt) {
        /*
         * 
         * //if (!t.getText().matches("[0-9--]*"))
         if (!t.getText().matches("[0-9]*")){
         JOptionPane.showMessageDialog(null, "Solo se permiten Numeros","Advertencia",JOptionPane.ERROR_MESSAGE);
           
            
         }
         */
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            JOptionPane.showMessageDialog(null, "Este campo solo acepta numeros", "Advertencia", JOptionPane.ERROR_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
            evt.consume();


        }
    }

    private void validarLongitud(int Longitud, JTextField txt, JLabel lbl, java.awt.event.KeyEvent evt) {


        if (txt.getText().length() < Longitud) {
            lbl.setVisible(true);

        } else {

            lbl.setVisible(false);
        }

    }

    private void detenerEscritura(int Longitud, JTextField txt, java.awt.event.KeyEvent evt) {


        if (txt.getText().length() == Longitud) {
            evt.consume();
        }


    }

    private void cargarDocentes() {
        try {
            String titulos[] = {"Nº", "CEDULA", "APELLIDO", "NOMBRE", "DIRECCION", "TELEFONO"};
            m = new DefaultTableModel(null,titulos);
            //para ordenar
            JTable p=new JTable(m);
            String fila[] = new String[6];
            
            String consulta = "select * from vistadocentes order by apellido_doc";
            ResultSet r;
            r = con.Listar(consulta);
            int c = 1;
            while (r.next()) {
                fila[0] = String.valueOf(c) + "º";
                fila[1] = r.getString(1);
                fila[2] = r.getString(2);
                fila[3] = r.getString(3);
                fila[4] = r.getString(4);
                fila[5] = r.getString(5);
                 m.addRow(fila);
                c++;
       }
            
            TableRowSorter<TableModel> paraOrdenar=new TableRowSorter<TableModel>(m);
            tblDocentes.setRowSorter(paraOrdenar);
            tblDocentes.setModel(m);
            formatearTablaDocentes();
          

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar los Datos");
        }
    }
    
    private void formatearTablaDocentes(){
       tblDocentes.getColumnModel().getColumn(0).setPreferredWidth(1);
       // tblDocentes.getColumnModel().getColumn(0).setPreferredWidth(10);
        
    }
    
    private void limpiar(){
        txtApellido.setText("");
        txtCedula.setText("");
        txtDireccion.setText("");
        txtNombre.setText("");
        txtRutaImagen.setText("");
        txtTelefono.setText("");
         txtCedula.setEditable(true);
         txtCedula.requestFocus();
         lblFotos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/userimage.png")));
    }    
    private void tblDocentesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocentesMousePressed
     
        mostrarDatos();
    }//GEN-LAST:event_tblDocentesMousePressed

    private void tblDocentesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDocentesKeyReleased
        mostrarDatos();
    }//GEN-LAST:event_tblDocentesKeyReleased

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void txtTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyReleased

        validarLongitud(10, txtTelefono, lblErrorTelefono, evt);
    }//GEN-LAST:event_txtTelefonoKeyReleased

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        detenerEscritura(10, txtTelefono, evt);
        validarNumeros(txtTelefono, evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyReleased

        validarLongitud(10, txtCedula, lblErrorCedula, evt);

    }//GEN-LAST:event_txtCedulaKeyReleased

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        detenerEscritura(10, txtCedula, evt);
        validarNumeros(txtCedula, evt);
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        int fila=tblDocentes.getSelectedRow();
        if(fila== -1){
            JOptionPane.showMessageDialog(null,"Debe selecionar un docente","ERROR",JOptionPane.ERROR_MESSAGE);
            
        }else{
            try{
                String cedula=tblDocentes.getValueAt(fila, 1).toString();
               
                String rutaInforme="C:\\Users\\rene\\Documents\\NetBeansProjects\\Notas2\\src\\Reportes\\Indivudual.jasper";
                Map parametros = new HashMap();
                parametros.put("ced",cedula);
               
                JasperPrint informe=JasperFillManager.fillReport(rutaInforme,parametros,con.getConexion());
                
                JasperViewer ventanaVisor=new JasperViewer(informe,false);
                ventanaVisor.setTitle("INFORME");
                ventanaVisor.setVisible(true);
                
            
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error al cargar el reporte", "ERROR",JOptionPane.ERROR_MESSAGE);
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_btnHistorialActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        Reportes re = new Reportes();
        String ruta = "C:\\Users\\rene\\Documents\\NetBeansProjects\\Notas2\\src\\Reportes\\report1.jasper";
        //ABRIR CUADRO DE DIALOGO PARA GUARDAR EL ARCHIVO         
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos *.PDF", "pdf", "PDF"));//filtro para ver solo archivos .pdf
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.pdf","pdf","PDF"));
        int seleccion = fileChooser.showSaveDialog(null);
        try {
            if (seleccion == JFileChooser.APPROVE_OPTION) {//comprueba si ha presionado el boton de aceptar
                File JFC = fileChooser.getSelectedFile();
                String PATH = JFC.getAbsolutePath();//obtenemos la direccion del archivo + el nombre a guardar
                try (PrintWriter printwriter = new PrintWriter(JFC)) {
                    printwriter.print(ruta);
                }
                re.reportesPDF(ruta, PATH);//mandamos como parametros la ruta del archivo a compilar y el nombre y ruta donde se guardaran    
                //comprobamos si a la hora de guardar obtuvo la extension y si no se la asignamos
                if (!(PATH.endsWith(".pdf"))) {
                    File temp = new File(PATH + ".pdf");
                    JFC.renameTo(temp);//renombramos el archivo
                }
                JOptionPane.showMessageDialog(null, "Esto puede tardar unos segundos,espere porfavor", "Estamos Generando el Reporte", JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(null, "Documento Exportado Exitosamente!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (FileNotFoundException | HeadlessException e) {//por alguna excepcion salta un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al Exportar el archivo!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
      cargarDocentes();
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        
        try {
            PreparedStatement pstm = con.getConexion().prepareCall("call insertarDocente(?, ?, ?, ?,?,?);");
            pstm.setString(1, txtCedula.getText());
            pstm.setString(2, txtApellido.getText());
            pstm.setString(3, txtNombre.getText());
            pstm.setString(4, txtDireccion.getText());
            pstm.setString(5, txtTelefono.getText());
            pstm.setString(6, txtRutaImagen.getText());
            ResultSet r = pstm.executeQuery();
            String respuesta = "";
            while (r.next()) {
                respuesta = r.getString(1).toString();
            }
            JOptionPane.showMessageDialog(null, respuesta, "CONFIRMACION", JOptionPane.WARNING_MESSAGE);
            pstm.close();
            if (respuesta.equals("Docente registrado exitosamente"))
            {
            limpiar();
            cargarDocentes();
            }else
            {
                txtCedula.requestFocus();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        btnGrabar.setEnabled(true);
        btnLimpiar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void mostrarDatos(){
           // TODO add your handling code here:
        
        
//Evento MousePresset del JTable
        int fila = this.tblDocentes.getSelectedRow();
//        desactivagenerador();
        
      /* btnGrabar.
       btnEliminar.setEnabled(true);
       btnModificar.setEnabled(true);
       txtCedula.setEditable(false);*/
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        
        btnGrabar.setEnabled(false);
        btnLimpiar.setEnabled(false);
        btnNuevo.setEnabled(true);
        try {
            String cedula = tblDocentes.getValueAt(fila, 1).toString();
            this.txtCedula.setText(tblDocentes.getValueAt(fila, 1).toString());
            this.txtApellido.setText(tblDocentes.getValueAt(fila, 2).toString());
            this.txtNombre.setText(tblDocentes.getValueAt(fila, 3).toString());
            this.txtDireccion.setText(tblDocentes.getValueAt(fila, 4).toString());
            this.txtTelefono.setText(tblDocentes.getValueAt(fila, 5).toString());
           // String consulta = "call fotoExiste ('"+cedula+"')";
           String consulta="SELECT IF( EXISTS(SELECT FOTO_DOC FROM docente WHERE CEDULA_DOC='"+cedula+"' AND FOTO_DOC<>''), 1, 0);";
           
            
            ResultSet r = con.Listar(consulta);
            String RESPUES = "";
            while (r.next()) {
                RESPUES = r.getString(1).toString();
               
            }
            if (RESPUES.equals("0")) {
                lblFotos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/userimage.png")));
            } else {
               cargarfoto(cedula);//llama al metodo para cargar la foto en el table y le invia el parametro DNI
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "OCURRIO UN ERROR AL MOMENTO DE SELECCIONAR EL DOCENTE", "ADVERTENCIA", JOptionPane.QUESTION_MESSAGE);
            
        }
    }
    //Crear este metodo tal y como lo explico en el video
 //METODO PARA CARGAR LA FOTO DEL DOCENTE
    public void cargarfoto(String cedula) {
        Image dtCat = f.recuperarfotos(cedula);
        ImageIcon icon = new ImageIcon(dtCat);
        //Se extrae la imagen del icono
        Image img = icon.getImage();
        //Se modifica su tamaño
        Image newimg = img.getScaledInstance(130, 150, java.awt.Image.SCALE_SMOOTH);
        //SE GENERA EL IMAGE ICON CON LA NUEVA IMAGEN
        ImageIcon newIcon = new ImageIcon(newimg);
        //Se coloca el nuevo icono modificado
        if (newIcon == null) {
            JOptionPane.showMessageDialog(null, "no tiene imagen","ADVERTENCIA",JOptionPane.ERROR);
        } else {
            lblFotos.setIcon(newIcon);//Seteamos la foto el el label llamado jLFoto del frame HISTORIALASISITENCIA
            lblFotos.setSize(130, 150);//Seteamos el tamaño para la foto  
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Docentes().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGrabar;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblErrorCedula;
    private javax.swing.JLabel lblErrorTelefono;
    private javax.swing.JLabel lblFotos;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tblDocentes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRutaImagen;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
