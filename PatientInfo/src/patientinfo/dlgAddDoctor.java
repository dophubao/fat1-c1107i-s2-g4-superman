/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * dlgAddPatient.java
 *
 * Created on Aug 18, 2012, 10:46:52 AM
 */
package patientinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author tug
 */
public class dlgAddDoctor extends javax.swing.JDialog {

    /** Creates new form dlgAddPatient */
    Connection conn = null;
    String gender = "";
    int age = 0;

    public dlgAddDoctor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PI", "sa", "123456");
        } catch (Exception e) {
        }

    }
    public void rs(){
        txtAddAge.setText(null);
        txtAddFName.setText(null);
        age = 0;
        gender = "";
        btgGender.clearSelection();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgGender = new javax.swing.ButtonGroup();
        pnlAdd = new javax.swing.JPanel();
        pnlPatientInfo = new javax.swing.JPanel();
        txtAddFName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtAddAge = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        rbtAddMale = new javax.swing.JRadioButton();
        rbtAddFMale = new javax.swing.JRadioButton();
        btnAddPatient = new javax.swing.JButton();
        btnAddReset = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlAdd.setBorder(javax.swing.BorderFactory.createTitledBorder("Doctor's Information"));
        pnlAdd.setPreferredSize(new java.awt.Dimension(400, 500));

        txtAddFName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddFNameActionPerformed(evt);
            }
        });

        jLabel13.setText("Full Name");

        jLabel16.setText("Age");

        jLabel17.setText("Gender");

        btgGender.add(rbtAddMale);
        rbtAddMale.setText("Male");
        rbtAddMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtAddMaleActionPerformed(evt);
            }
        });

        btgGender.add(rbtAddFMale);
        rbtAddFMale.setText("Female");
        rbtAddFMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtAddFMaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPatientInfoLayout = new javax.swing.GroupLayout(pnlPatientInfo);
        pnlPatientInfo.setLayout(pnlPatientInfoLayout);
        pnlPatientInfoLayout.setHorizontalGroup(
            pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPatientInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPatientInfoLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(rbtAddMale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtAddFMale))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPatientInfoLayout.createSequentialGroup()
                        .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPatientInfoLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel17))
                            .addGroup(pnlPatientInfoLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel13))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAddAge, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(txtAddFName, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))))
                .addContainerGap(33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlPatientInfoLayout.setVerticalGroup(
            pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatientInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(46, 46, 46)
                .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtAddFMale)
                    .addGroup(pnlPatientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtAddMale)
                        .addComponent(jLabel17)))
                .addGap(28, 28, 28))
        );

        btnAddPatient.setText("Add");
        btnAddPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPatientActionPerformed(evt);
            }
        });

        btnAddReset.setText("Reset");
        btnAddReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAddLayout = new javax.swing.GroupLayout(pnlAdd);
        pnlAdd.setLayout(pnlAddLayout);
        pnlAddLayout.setHorizontalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlPatientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnAddPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddReset, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAddLayout.setVerticalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(pnlPatientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPatient)
                    .addComponent(btnAddReset))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        jLabel1.setText("Add Doctor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddFNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddFNameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtAddFNameActionPerformed

    private void rbtAddMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtAddMaleActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_rbtAddMaleActionPerformed

    private void rbtAddFMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtAddFMaleActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_rbtAddFMaleActionPerformed

    private void btnAddPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPatientActionPerformed
        // TODO add your handling code here:
        int check = 0;
        if (rbtAddFMale.isSelected()) {
            gender = "Female";
        } else if (rbtAddMale.isSelected()) {
            gender = "Male";
        }
        if (txtAddFName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter name!!!");
        } else if (txtAddAge.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Age!");
        } else {
            try {
                age = Integer.parseInt(txtAddAge.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Age Valid!!");
                check = 1;
            }
        }
        if (check == 0) {
            if (age < 20) {
                JOptionPane.showMessageDialog(this, "Age lesser the limit!!");
            } else if (gender == "") {
                JOptionPane.showMessageDialog(this, "Choice Gender!");
            } else {
                try {
                    String sUpdate = "INSERT INTO tblDoctor VALUES(?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sUpdate);
                    pstmt.setString(1, txtAddFName.getText());
                    pstmt.setInt(2, age);
                    pstmt.setString(3, gender);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Add success!!");
                    rs();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

}//GEN-LAST:event_btnAddPatientActionPerformed

    private void btnAddResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddResetActionPerformed
        // TODO add your handling code here:
        rs();
}//GEN-LAST:event_btnAddResetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                dlgAddDoctor dialog = new dlgAddDoctor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgGender;
    private javax.swing.JButton btnAddPatient;
    private javax.swing.JButton btnAddReset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlPatientInfo;
    private javax.swing.JRadioButton rbtAddFMale;
    private javax.swing.JRadioButton rbtAddMale;
    private javax.swing.JTextField txtAddAge;
    private javax.swing.JTextField txtAddFName;
    // End of variables declaration//GEN-END:variables
}
