/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DetailPatient.java
 *
 * Created on Aug 17, 2012, 7:55:23 PM
 */
package patientinfo;

import DateTime.DateTimeTDV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author tug
 */
public class DetailPatient extends javax.swing.JDialog{

    /** Creates new form DetailPatient */
    public  Patient patientDlg = new Patient();
    int age=0;
    String gender="";
    Connection conn = null;
    String date="";


    public DetailPatient(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PI", "sa", "123456");
        } catch (Exception e) {
        }
        DateTimeTDV dateTDV = new DateTimeTDV();
        cbbYI.setModel(dateTDV.getListYear());

    }

    DetailPatient() {
    }
    public void Update(){
        if(txtName.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter patient's name!");
        }else if(txtAD.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter patient's address!");
        }else if(txtAge.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter patient's age!");
        }else if(rbtFemale.isSelected() && rbtMale.isSelected()){
            JOptionPane.showMessageDialog(this, "Choice patient's Gender!");
        }else if(txtDoctor.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter Doctor manager!");
        }else if(aDes.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter Description!");
        } else {
            boolean check = true;
            try {
                age = Integer.parseInt(txtAge.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valid age!!!");
                check = false;
            }
            if(check){
                try {
                    if(cbxUpdateDateIn.isSelected()){
                    date = cbbMI.getSelectedItem().toString()+"/"+cbbDI.getSelectedItem().toString()+"/"+cbbYI.getSelectedItem().toString();
                    }else{
                        date = txtDateIn.getText();
                    }
                    CallableStatement cs = conn.prepareCall("{call updatePatient(?,?,?,?,?,?,?,?,?)}");
                    cs.setInt(1,Integer.parseInt(txtID.getText()));
                    cs.setString(2, txtName.getText());
                    cs.setString(3, txtAD.getText());
                    cs.setInt(4, age);
                    cs.setString(5, gender);
                    cs.setString(6, aDes.getText());
                    cs.setString(7, cbbDepartment.getSelectedItem().toString());
                    cs.setString(8, txtDoctor.getText());
                    cs.setString(9, date);
                    cs.executeUpdate();
                    JOptionPane.showMessageDialog(this, "update success!!");
                    cs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
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
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtAD = new javax.swing.JTextField();
        txtAge = new javax.swing.JTextField();
        rbtMale = new javax.swing.JRadioButton();
        rbtFemale = new javax.swing.JRadioButton();
        cbbDepartment = new javax.swing.JComboBox();
        txtDoctor = new javax.swing.JTextField();
        cbbYI = new javax.swing.JComboBox();
        cbbMI = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbbDI = new javax.swing.JComboBox();
        txtDateIn = new javax.swing.JTextField();
        txtDateOut = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        aDes = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        aSick = new javax.swing.JTextArea();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtInHospital = new javax.swing.JTextField();
        cbxUpdateDateIn = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Details Patient");

        jLabel27.setText("Full Name:");

        jLabel28.setText("Address:");

        jLabel29.setText("Age:");

        jLabel30.setText("Gender:");

        jLabel31.setText("Department:");

        jLabel32.setText("Description:");

        jLabel33.setText("Sick:");

        jLabel34.setText("Doctor's Manager:");

        jLabel35.setText("DateIn:");

        jLabel36.setText("DateOut:");

        jLabel37.setText("In Hospital:");

        btgGender.add(rbtMale);
        rbtMale.setText("Male");
        rbtMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtMaleActionPerformed(evt);
            }
        });

        btgGender.add(rbtFemale);
        rbtFemale.setText("Female");
        rbtFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtFemaleActionPerformed(evt);
            }
        });

        cbbDepartment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Internal medicine", "Surgical", "Cardiovascular" }));
        cbbDepartment.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbbDepartment.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbDepartmentItemStateChanged(evt);
            }
        });

        cbbYI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2012" }));
        cbbYI.setEnabled(false);
        cbbYI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbYIItemStateChanged(evt);
            }
        });
        cbbYI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbYIActionPerformed(evt);
            }
        });

        cbbMI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cbbMI.setEnabled(false);
        cbbMI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbMIItemStateChanged(evt);
            }
        });
        cbbMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMIActionPerformed(evt);
            }
        });

        jLabel9.setText("Month");

        jLabel11.setText("Year");

        jLabel6.setText("Day");

        cbbDI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cbbDI.setEnabled(false);

        txtDateIn.setEditable(false);

        txtDateOut.setEditable(false);

        aDes.setColumns(20);
        aDes.setRows(5);
        jScrollPane1.setViewportView(aDes);

        aSick.setColumns(20);
        aSick.setEditable(false);
        aSick.setRows(5);
        jScrollPane2.setViewportView(aSick);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete Patient");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel3.setText("ID:");

        txtID.setEditable(false);

        txtInHospital.setEditable(false);

        cbxUpdateDateIn.setText("Update Date In");
        cbxUpdateDateIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUpdateDateInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36))
                                .addGap(37, 37, 37))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxUpdateDateIn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDateOut)
                            .addComponent(txtDateIn)
                            .addComponent(cbbDepartment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAD, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(txtAge, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbtMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtFemale))
                            .addComponent(txtDoctor, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbYI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbMI, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtID))
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33)
                            .addComponent(jLabel37)))
                    .addComponent(jLabel30)
                    .addComponent(jLabel34)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtInHospital)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addGap(103, 103, 103))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(360, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(331, 331, 331))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(rbtMale)
                                            .addComponent(rbtFemale)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel30)))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel31)
                                    .addComponent(cbbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel34)
                                    .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(txtDateIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbMI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(cbbYI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel11)
                                    .addComponent(cbxUpdateDateIn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDateOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtInHospital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addGap(22, 22, 22)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbDepartmentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbDepartmentItemStateChanged
        // TODO add your handling code here:

}//GEN-LAST:event_cbbDepartmentItemStateChanged

    private void cbbYIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbYIItemStateChanged
        // TODO add your handling code here:
        DateTimeTDV tdv = new DateTimeTDV();
        int month = Integer.parseInt(cbbMI.getSelectedItem().toString());
        int year = Integer.parseInt(cbbYI.getSelectedItem().toString());
        cbbDI.setModel(tdv.getDayByMonth(month, year));
}//GEN-LAST:event_cbbYIItemStateChanged

    private void cbbYIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbYIActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cbbYIActionPerformed

    private void cbbMIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbMIItemStateChanged
        // TODO add your handling code here:
        DateTimeTDV tdv = new DateTimeTDV();
        int month = Integer.parseInt(cbbMI.getSelectedItem().toString());
        int year = Integer.parseInt(cbbYI.getSelectedItem().toString());
        cbbDI.setModel(tdv.getDayByMonth(month, year));
}//GEN-LAST:event_cbbMIItemStateChanged

    private void cbbMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMIActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cbbMIActionPerformed

    private void cbxUpdateDateInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxUpdateDateInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxUpdateDateInActionPerformed

    private void rbtMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtMaleActionPerformed
        // TODO add your handling code here:
        gender = "Male";
    }//GEN-LAST:event_rbtMaleActionPerformed

    private void rbtFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtFemaleActionPerformed
        // TODO add your handling code here:
        gender = "Female";
    }//GEN-LAST:event_rbtFemaleActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            String sDelete = "Delete from tblPatient where ID = ? ";
            PreparedStatement pstmt = conn.prepareCall(sDelete);
            pstmt.setString(1, txtID.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Delete Success!!");
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DetailPatient dialog = new DetailPatient(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea aDes;
    private javax.swing.JTextArea aSick;
    private javax.swing.ButtonGroup btgGender;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cbbDI;
    private javax.swing.JComboBox cbbDepartment;
    private javax.swing.JComboBox cbbMI;
    private javax.swing.JComboBox cbbYI;
    private javax.swing.JCheckBox cbxUpdateDateIn;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlDetails1;
    private javax.swing.JRadioButton rbtFemale;
    private javax.swing.JRadioButton rbtMale;
    private javax.swing.JTextField txtAD;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtDateIn;
    private javax.swing.JTextField txtDateOut;
    private javax.swing.JTextField txtDoctor;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtInHospital;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    public void setaDes(String a) {
        aDes.setText(a);
    }

    public void setaSick(String sick) {
        aSick.setText(sick);
    }

    public void setCbbDepartment(String c) {
        if("Internal medicine".equals(c)){
            cbbDepartment.setSelectedIndex(0);
        }else if("Surgical".equals(c)){
            cbbDepartment.setSelectedIndex(1);
        }else{
            cbbDepartment.setSelectedIndex(2);
        }
    }

    public void setTxtInHospital(String s) {
        txtInHospital.setText(s);
    }

    
    public void setRbtMale(String s) {
        if("Male".equals(s)){
            rbtMale.setSelected(true);
            rbtFemale.setSelected(false);
        }else{
            rbtMale.setSelected(false);
            rbtFemale.setSelected(true);
        }
    }

    public void setTxtAD(String s) {
        txtAD.setText(s);
    }

    public void setTxtAge(String s) {
        txtAge.setText(s);
    }

    public void setTxtDateIn(String s) {
        txtDateIn.setText(s);
    }

    public void setTxtDateOut(String s) {
        txtDateOut.setText(s);
    }

    public void setTxtDoctor(String s) {
        txtDoctor.setText(s);
    }

    public void setTxtName(String s) {
        txtName.setText(s);
    }

    public void setTxtID(String s) {
        txtID.setText(s);
    }

 }
