/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author rene
 */
public class Docentes extends javax.swing.JFrame {
    
    
    //variable para cargar las imagenes
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de imagen","jpg","png");
    String rutaImagen;
    

    /**
     * Creates new form Docentes
     */
    public Docentes() {
        initComponents();
        ocultarMensajes();
        rutaImagen="";
    }
    
    public void ocultarMensajes(){
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
        btnHistorial = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        lblErrorTelefono = new javax.swing.JLabel();
        lblErrorCedula = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        panelPrincipal.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 150, 30));

        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCedulaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
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
        panelPrincipal.add(btnCargarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, -1, -1));
        panelPrincipal.add(txtRutaImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 360, 160, -1));

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });
        panelPrincipal.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 80, 100, 40));

        btnGrabar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGrabar.setText("GRABAR");
        btnGrabar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGrabarMouseClicked(evt);
            }
        });
        panelPrincipal.add(btnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 100, 40));

        btnModificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificar.setText("MODIFICAR");
        panelPrincipal.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 120, 40));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setText("ELIMINAR");
        panelPrincipal.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, -1, 40));

        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        panelPrincipal.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 110, 40));

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setText("ACTUALIZA BD");
        panelPrincipal.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, 150, 40));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFotos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblFotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 150));

        panelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 130, 150));

        btnHistorial.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnHistorial.setText("Ver Histrorial");
        panelPrincipal.add(btnHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 260, 190, 60));

        btnExportar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnExportar.setText("Exportar ");
        panelPrincipal.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 170, 190, 60));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelPrincipal.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 360, 100, 30));

        jTextField1.setText("jTextField1");
        panelPrincipal.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 360, 170, 30));

        lblErrorTelefono.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblErrorTelefono.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorTelefono.setText("X       Ingrese 10 digitos");
        panelPrincipal.add(lblErrorTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, 140, -1));

        lblErrorCedula.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblErrorCedula.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorCedula.setText("X     Ingrese 10 digitos");
        panelPrincipal.add(lblErrorCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 130, -1));

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        
        //Creamos un objeto de JfileChooser
        JFileChooser dlg=new JFileChooser();
        //del objeto creado vamoas a llamar al metodo setFileFilter
        dlg.setFileFilter(filter);
        //abriremos la ventana de dialogo para escojer la imagen
        int option= dlg.showOpenDialog(this);
        //Si hacemos click en el boton abrir
        if (option==JFileChooser.APPROVE_OPTION){
            //obtener el nombre del arechivo
            String fil=dlg.getSelectedFile().getPath();
            
            /*
            //obtener la dirrecion donde se guardara la imagen
            String file=dlg.getSelectedFile().toString();
            //cargamos la imagen seleccionada al label No es necdesario
            lblFotos.setIcon(new ImageIcon(file));           
            */       
            
            //modificamos la imagen
            ImageIcon icon=new ImageIcon(fil);
            
            
            //Extraer la imagen del icono
            Image img=icon.getImage();
            //cambiamos el tamanio
            Image nuevaImagen=img.getScaledInstance(155, 175,java.awt.Image.SCALE_SMOOTH);
            //se genera un image icon con la nueva imagen
            ImageIcon nuevoIcon = new ImageIcon(nuevaImagen);
            lblFotos.setIcon(nuevoIcon);
            lblFotos.setSize(155,175);
            txtRutaImagen.setText(fil);
            rutaImagen="";
            
             
        }
        
        
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSalirMouseClicked

    private void validarNumeros(JTextField t){
        //if (!t.getText().matches("[0-9--]*"))
         if (!t.getText().matches("[0-9]*")){
            JOptionPane.showMessageDialog(null, "Solo se permiten Numeros","Advertencia",JOptionPane.ERROR_MESSAGE);
            
        }
        
    }
    private void txtCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyReleased
       
     //  validarNumeros(txtCedula);
       if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            JOptionPane.showMessageDialog(null, "Este campo solo acepta numeros", "Advertencia", JOptionPane.ERROR_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaKeyReleased

    private void txtTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyReleased
        validarNumeros(txtTelefono);
    }//GEN-LAST:event_txtTelefonoKeyReleased

    private void validarLongitud(int Longitud, JTextField txt,JLabel lbl,java.awt.event.KeyEvent evt){
         if (!(txt.getText().length()==Longitud)){
           lbl.setVisible(true);
           //txt.setText("");
          // txt.requestFocus();
          //ejecutar el metodo continuamente
                   
                     
       }
        else{
           lbl.setVisible(false);
           evt.consume();
       }
        
    }
    
    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
      
       validarLongitud(9,txtCedula,lblErrorCedula,evt);
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void btnGrabarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGrabarMouseClicked
        
        System.out.println(txtCedula.getText().length());
    }//GEN-LAST:event_btnGrabarMouseClicked

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
       validarLongitud(10,txtTelefono,lblErrorTelefono,evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblErrorCedula;
    private javax.swing.JLabel lblErrorTelefono;
    private javax.swing.JLabel lblFotos;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRutaImagen;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}