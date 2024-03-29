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

import DateTime.DateTimePatientInfo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author tug
 */
public class dlgUpdate extends javax.swing.JDialog {

    /** Creates new form DetailPatient */
    public Patient patientDlg = new Patient();
    int age = 0;
    String gender = "";
    Connection conn = null;
    String date = "";
    String doctor = "";
    int room = 0;
    int bed = 0;

    public dlgUpdate(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PI", "sa", "123456");
        } catch (Exception e) {
        }
        setTitle("Update information");
        DateTimePatientInfo dateTDV = new DateTimePatientInfo();
        cbbYI.setModel(dateTDV.getListYear());
        cbbDoctor.setModel(getListDoctor());
        cbbRoom.setModel(getListRoom());
        cbbBed.setModel(getListBed());
    }

    public DefaultComboBoxModel getListDoctor() {
        DefaultComboBoxModel doctorName = new DefaultComboBoxModel();
        try {

            String sSelect = "Select FullName from tblDoctor";
            PreparedStatement pstmt = conn.prepareStatement(sSelect);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    doctorName.addElement(rs.getObject(i).toString());
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return doctorName;
    }

    public void rsUp() {
        txtID.setText(null);
        txtName.setText(null);
        txtAD.setText(null);
        txtAge.setText(null);
        txtDateIn.setText(null);
        txtDateOut.setText(null);
        txtDoctor.setText(null);
        btgGender.clearSelection();
        txtInHospital.setText(null);
        cbbDepartment.setSelectedIndex(0);
        cbbDoctor.setSelectedIndex(0);
        cbbDI.setSelectedIndex(0);
        cbbYI.setSelectedIndex(0);
        cbbMI.setSelectedIndex(0);
        patientDlg = new Patient();
        date = "";
        doctor = "";
        gender = "";
        if (txtID.getText().isEmpty()) {
            btnDelete.setEnabled(false);
            btnUpdate.setEnabled(false);
        } else {
            btnDelete.setEnabled(true);
            btnUpdate.setEnabled(true);
        }
    }

    dlgUpdate() {
    }

    public void setSTTBed(int bed,String STT) {
        try {
            CallableStatement cs = conn.prepareCall("{call setSTTBed(?,?)}");
            cs.setInt(1, bed);
            cs.setString(2, STT);
            cs.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void Update() {
        if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient's name!");
        } else if (txtAD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient's address!");
        } else if (txtAge.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient's age!");
        } else if (rbtFemale.isSelected() && rbtMale.isSelected()) {
            JOptionPane.showMessageDialog(this, "Choice patient's Gender!");
        } else if (aDes.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Description!");
        } else {
            boolean check = true;
            try {
                age = Integer.parseInt(txtAge.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valid age!!!");
                check = false;
            }
            if (check) {
                if (cbxUpdateDoctor.isSelected()) {
                    doctor = cbbDoctor.getSelectedItem().toString();
                } else {
                    doctor = txtDoctor.getText();
                }
                try {
                    if (cbxUpdateDateIn.isSelected()) {
                        date = cbbMI.getSelectedItem().toString() + "/" + cbbDI.getSelectedItem().toString() + "/" + cbbYI.getSelectedItem().toString();
                    } else {
                        date = txtDateIn.getText();
                    }
                    if (cbxUpdateRoom.isSelected()) {
                        room = Integer.parseInt(cbbRoom.getSelectedItem().toString());
                        bed = Integer.parseInt(cbbBed.getSelectedItem().toString());
                    } else {
                        room = Integer.parseInt(txtRoom.getText());
                        bed = Integer.parseInt(txtBed.getText());
                    }
                    CallableStatement cs = conn.prepareCall("{call updatePatient(?,?,?,?,?,?,?,?,?,?,?)}");
                    cs.setInt(1, Integer.parseInt(txtID.getText()));
                    cs.setString(2, txtName.getText());
                    cs.setString(3, txtAD.getText());
                    cs.setInt(4, age);
                    cs.setString(5, gender);
                    cs.setString(6, aDes.getText());
                    cs.setString(7, cbbDepartment.getSelectedItem().toString());
                    cs.setString(8, doctor);
                    cs.setString(9, date);
                    cs.setInt(10, room);
                    cs.setInt(11, bed);
                    cs.executeUpdate();
                    setSTTBed(bed, "Using");
                    JOptionPane.showMessageDialog(this, "update success!!");
                    cs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public DefaultComboBoxModel getListRoom() {
        DefaultComboBoxModel listRoom = new DefaultComboBoxModel();
        try {

            String sSelect = "Select id from tblRoom";
            PreparedStatement pstmt = conn.prepareStatement(sSelect);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    listRoom.addElement(rs.getObject(i).toString());
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRoom;
    }

    public DefaultComboBoxModel getListBed() {
        DefaultComboBoxModel listbed = new DefaultComboBoxModel();
        try {

            String sSelect = "Select id from tblbed where room = ? and stt = 'empty'";
            PreparedStatement pstmt = conn.prepareStatement(sSelect);
            pstmt.setString(1, cbbRoom.getSelectedItem().toString());
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    listbed.addElement(rs.getObject(i).toString());
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listbed;
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDoctor = new javax.swing.JTextField();
        cbbDoctor = new javax.swing.JComboBox();
        cbxUpdateDoctor = new javax.swing.JCheckBox();
        cbbRoom = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbbBed = new javax.swing.JComboBox();
        cbxUpdateRoom = new javax.swing.JCheckBox();
        txtRoom = new javax.swing.JTextField();
        txtBed = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

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

        cbxUpdateDateIn.setText("Update ");
        cbxUpdateDateIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUpdateDateInActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Update.png"))); // NOI18N
        jLabel1.setText("Update Patient");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(240, 240, 240))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        txtDoctor.setEditable(false);

        cbbDoctor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(empty)" }));

        cbxUpdateDoctor.setText("Update Doctor");
        cbxUpdateDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUpdateDoctorActionPerformed(evt);
            }
        });

        cbbRoom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbRoom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbRoomItemStateChanged(evt);
            }
        });
        cbbRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbRoomActionPerformed(evt);
            }
        });

        jLabel4.setText("Room");

        jLabel5.setText("Bed");

        cbbBed.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbBed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbBedActionPerformed(evt);
            }
        });

        cbxUpdateRoom.setText("Update");
        cbxUpdateRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUpdateRoomActionPerformed(evt);
            }
        });

        txtRoom.setEditable(false);

        txtBed.setEditable(false);

        jLabel7.setText("Room");

        jLabel8.setText("Bed");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(214, 214, 214))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(rbtMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtFemale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel29))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAge, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(txtID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(txtAD, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(cbxUpdateDoctor))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbDoctor, 0, 256, Short.MAX_VALUE)
                                    .addComponent(txtDoctor, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36)
                                    .addComponent(cbxUpdateDateIn))
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDateOut, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                    .addComponent(txtDateIn, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                    .addComponent(cbbDepartment, 0, 255, Short.MAX_VALUE)
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
                                        .addGap(8, 8, 8)
                                        .addComponent(cbbDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel33)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel37)
                                            .addGap(6, 6, 6))))
                                .addGap(33, 33, 33))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxUpdateRoom)
                                .addGap(2, 2, 2)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtInHospital)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                                .addComponent(jLabel8))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbBed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBed, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtInHospital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rbtMale)
                                .addComponent(rbtFemale))
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxUpdateDoctor)
                            .addComponent(jLabel33))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(cbbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(txtDateOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDateIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxUpdateRoom))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbbYI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(cbbMI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(cbxUpdateDateIn)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbBed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbDepartmentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbDepartmentItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_cbbDepartmentItemStateChanged

    private void cbbYIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbYIItemStateChanged
        // TODO add your handling code here:
        DateTimePatientInfo tdv = new DateTimePatientInfo();
        int month = Integer.parseInt(cbbMI.getSelectedItem().toString());
        int year = Integer.parseInt(cbbYI.getSelectedItem().toString());
        cbbDI.setModel(tdv.getDayByMonth(month, year));
}//GEN-LAST:event_cbbYIItemStateChanged

    private void cbbYIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbYIActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cbbYIActionPerformed

    private void cbbMIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbMIItemStateChanged
        // TODO add your handling code here:
        DateTimePatientInfo tdv = new DateTimePatientInfo();
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
        rsUp();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            String sDelete = "Delete from tblPatient where ID = ? ";
            PreparedStatement pstmt = conn.prepareCall(sDelete);
            pstmt.setString(1, txtID.getText());
            pstmt.executeUpdate();
            setSTTBed(Integer.parseInt(txtBed.getText()), "empty");
            JOptionPane.showMessageDialog(this, "Delete Success!!");
            pstmt.close();
            rsUp();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cbxUpdateDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxUpdateDoctorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxUpdateDoctorActionPerformed

    private void cbbRoomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbRoomItemStateChanged
        // TODO add your handling code here:
        cbbBed.setModel(getListBed());
}//GEN-LAST:event_cbbRoomItemStateChanged

    private void cbbBedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbBedActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cbbBedActionPerformed

    private void cbxUpdateRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxUpdateRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxUpdateRoomActionPerformed

    private void cbbRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbRoomActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                dlgUpdate dialog = new dlgUpdate(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox cbbBed;
    private javax.swing.JComboBox cbbDI;
    private javax.swing.JComboBox cbbDepartment;
    private javax.swing.JComboBox cbbDoctor;
    private javax.swing.JComboBox cbbMI;
    private javax.swing.JComboBox cbbRoom;
    private javax.swing.JComboBox cbbYI;
    private javax.swing.JCheckBox cbxUpdateDateIn;
    private javax.swing.JCheckBox cbxUpdateDoctor;
    private javax.swing.JCheckBox cbxUpdateRoom;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbtFemale;
    private javax.swing.JRadioButton rbtMale;
    private javax.swing.JTextField txtAD;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtBed;
    private javax.swing.JTextField txtDateIn;
    private javax.swing.JTextField txtDateOut;
    private javax.swing.JTextField txtDoctor;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtInHospital;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtRoom;
    // End of variables declaration//GEN-END:variables

    public void setaDes(String a) {
        aDes.setText(a);
    }

    public void setaSick(String sick) {
        aSick.setText(sick);
    }

    public void setCbbDepartment(String c) {
        if ("Internal medicine".equals(c)) {
            cbbDepartment.setSelectedIndex(0);
        } else if ("Surgical".equals(c)) {
            cbbDepartment.setSelectedIndex(1);
        } else {
            cbbDepartment.setSelectedIndex(2);
        }
    }

    public void setTxtInHospital(String s) {
        txtInHospital.setText(s);
    }

    public void setRbtMale(String s) {
        if ("Male".equals(s)) {
            rbtMale.setSelected(true);
            rbtFemale.setSelected(false);
        } else {
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

    public void setTxtBed(String s) {
        txtBed.setText(s);
    }

    public void setTxtRoom(String s) {
        txtRoom.setText(s);
    }
}
