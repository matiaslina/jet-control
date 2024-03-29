/*
 * StyleAboutBox.java
 */

package style;
import style.Bases.*;

import org.jdesktop.application.Action;

public class CargaCobrador extends javax.swing.JDialog {

    public CargaCobrador(java.awt.Frame parent) {
        super(parent);
        initComponents();
        getRootPane().setDefaultButton(closeButton);
    }

    @Action public void closeAboutBox() {
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        botonAceptar = new javax.swing.JButton();
        eNombre = new javax.swing.JLabel();
        cNombre = new javax.swing.JTextField();
        cApellido = new javax.swing.JTextField();
        eApellido = new javax.swing.JLabel();
        eCelular = new javax.swing.JLabel();
        cCel = new javax.swing.JTextField();
        cTel = new javax.swing.JTextField();
        eTel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(style.StyleApp.class).getContext().getResourceMap(CargaCobrador.class);
        setTitle(resourceMap.getString("title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(300, 79));
        setModal(true);
        setName("aboutBox"); // NOI18N
        setResizable(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(style.StyleApp.class).getContext().getActionMap(CargaCobrador.class, this);
        closeButton.setAction(actionMap.get("closeAboutBox")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        botonAceptar.setText(resourceMap.getString("botonAceptar.text")); // NOI18N
        botonAceptar.setName("botonAceptar"); // NOI18N
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        eNombre.setText(resourceMap.getString("eNombre.text")); // NOI18N
        eNombre.setName("eNombre"); // NOI18N

        cNombre.setText(resourceMap.getString("cNombre.text")); // NOI18N
        cNombre.setName("cNombre"); // NOI18N

        cApellido.setName("cApellido"); // NOI18N

        eApellido.setText(resourceMap.getString("eApellido.text")); // NOI18N
        eApellido.setName("eApellido"); // NOI18N

        eCelular.setText(resourceMap.getString("eCelular.text")); // NOI18N
        eCelular.setName("eCelular"); // NOI18N

        cCel.setName("cCel"); // NOI18N

        cTel.setName("cTel"); // NOI18N

        eTel.setText(resourceMap.getString("eTel.text")); // NOI18N
        eTel.setName("eTel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(eNombre)
                        .addGap(10, 10, 10)
                        .addComponent(cNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(eApellido)
                        .addGap(10, 10, 10)
                        .addComponent(cApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(eCelular)
                        .addGap(10, 10, 10)
                        .addComponent(cCel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonAceptar)
                                .addGap(18, 18, 18)
                                .addComponent(closeButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(eTel)
                                .addGap(10, 10, 10)
                                .addComponent(cTel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(eNombre))
                    .addComponent(cNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(eApellido))
                    .addComponent(cApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(eCelular))
                    .addComponent(cCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(eTel))
                    .addComponent(cTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(closeButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
    BCobradores cobradores = new BCobradores ();
    cobradores.ConectarSQL();
    cobradores.insertarCobrador(cNombre.getText(), cApellido.getText(), cCel.getText(), cTel.getText(), null, null);
    cobradores.CerrarSQL();



    cNombre.setText("");
    cApellido.setText("");
    cCel.setText("");
    cTel.setText("");

    dispose();
    }//GEN-LAST:event_botonAceptarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JTextField cApellido;
    private javax.swing.JTextField cCel;
    private javax.swing.JTextField cNombre;
    private javax.swing.JTextField cTel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel eApellido;
    private javax.swing.JLabel eCelular;
    private javax.swing.JLabel eNombre;
    private javax.swing.JLabel eTel;
    // End of variables declaration//GEN-END:variables
    
}
