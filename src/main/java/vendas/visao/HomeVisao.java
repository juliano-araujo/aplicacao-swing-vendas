/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vendas.visao;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author gabri
 */
public class HomeVisao extends javax.swing.JFrame {

    /**
     * Creates new form VisaoPadrao
     */
    public HomeVisao() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlGeral = new javax.swing.JPanel();
        PnlColorido = new javax.swing.JPanel();
        btnVendas = new javax.swing.JButton();
        imgLogo = new javax.swing.JLabel();
        btnProdutos = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnFuncionarios = new javax.swing.JButton();
        pnlPrincipal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        PnlGeral.setPreferredSize(new java.awt.Dimension(652, 536));

        PnlColorido.setBackground(new java.awt.Color(0, 153, 153));
        PnlColorido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnVendas.setBackground(new java.awt.Color(204, 204, 204));
        btnVendas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnVendas.setText("Vendas");

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/HOME SWEET HOME (1).png"))); // NOI18N

        btnProdutos.setBackground(new java.awt.Color(204, 204, 204));
        btnProdutos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnProdutos.setText("Produtos");

        btnClientes.setBackground(new java.awt.Color(204, 204, 204));
        btnClientes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnFuncionarios.setBackground(new java.awt.Color(204, 204, 204));
        btnFuncionarios.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnFuncionarios.setText("Funcionários");

        javax.swing.GroupLayout PnlColoridoLayout = new javax.swing.GroupLayout(PnlColorido);
        PnlColorido.setLayout(PnlColoridoLayout);
        PnlColoridoLayout.setHorizontalGroup(
            PnlColoridoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlColoridoLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(imgLogo)
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(PnlColoridoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PnlColoridoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnFuncionarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVendas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlColoridoLayout.setVerticalGroup(
            PnlColoridoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlColoridoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(imgLogo)
                .addGap(42, 42, 42)
                .addComponent(btnVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnFuncionarios, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        pnlPrincipal.setPreferredSize(new java.awt.Dimension(437, 483));

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 437, Short.MAX_VALUE)
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PnlGeralLayout = new javax.swing.GroupLayout(PnlGeral);
        PnlGeral.setLayout(PnlGeralLayout);
        PnlGeralLayout.setHorizontalGroup(
            PnlGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlGeralLayout.createSequentialGroup()
                .addComponent(PnlColorido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PnlGeralLayout.setVerticalGroup(
            PnlGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlColorido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PnlGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PnlGeral, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PnlGeral, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClientesActionPerformed

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
            java.util.logging.Logger.getLogger(HomeVisao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeVisao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeVisao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeVisao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeVisao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlColorido;
    private javax.swing.JPanel PnlGeral;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnFuncionarios;
    private javax.swing.JButton btnProdutos;
    private javax.swing.JButton btnVendas;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JPanel pnlPrincipal;
    // End of variables declaration//GEN-END:variables

    
    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnFuncionarios() {
        return btnFuncionarios;
    }

    public JButton getBtnProdutos() {
        return btnProdutos;
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public JButton getBtnVendas() {
        return btnVendas;
    }
}
