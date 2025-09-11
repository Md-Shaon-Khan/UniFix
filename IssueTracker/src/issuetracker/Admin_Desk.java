/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package issuetracker;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement; 
import javax.swing.table.TableColumn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class Admin_Desk extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    
    public Admin_Desk() {
        initComponents();
        connect();
        setupRowSelectionListener();
        setupComplaintSelectionListener();
    }

          public void connect() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/bv27gvoysnt3yplaamzq", // Localhost URL
            "root", // Default XAMPP username
            ""      // Default XAMPP password (empty)
        );
        System.out.println("✅ Database connected successfully (Localhost)!");
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(AdminSignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        System.out.println("❌ Database connection failed: " + ex.getMessage());
    }
}
         
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        student_info_admin = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        view_info_admin = new javax.swing.JButton();
        search_student_admin = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        problem_view_admin = new javax.swing.JTable();
        view_problem_admin = new javax.swing.JButton();
        txtRegNo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIssueDetails = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        update_issue = new javax.swing.JTextField();
        submit_update_status_admin1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        update_button_update_status_admin1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        complaint_table = new javax.swing.JTable();
        complaintTxt = new javax.swing.JTextField();
        complaint_response = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        view_update_status_admin = new javax.swing.JButton();
        update_button_update_status_admin = new javax.swing.JButton();
        submit_update_status_admin = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(171, 68, 89));

        jPanel2.setBackground(new java.awt.Color(242, 159, 88));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(102, 0, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 23, 82));
        jLabel1.setText("Admin Desk");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(396, 396, 396))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jPanel3.setBackground(new java.awt.Color(68, 23, 82));

        jTabbedPane1.setBackground(new java.awt.Color(242, 159, 88));
        jTabbedPane1.setForeground(new java.awt.Color(68, 23, 82));
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(169, 68, 89));

        student_info_admin.setBackground(new java.awt.Color(27, 24, 51));
        student_info_admin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        student_info_admin.setForeground(new java.awt.Color(255, 255, 255));
        student_info_admin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Reg. No", "Department", "Email", "Phone Number", "Batch", "Hall", "Gender"
            }
        ));
        jScrollPane2.setViewportView(student_info_admin);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Student Information");

        view_info_admin.setBackground(new java.awt.Color(68, 23, 82));
        view_info_admin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        view_info_admin.setForeground(new java.awt.Color(255, 255, 255));
        view_info_admin.setText("View");
        view_info_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_info_adminActionPerformed(evt);
            }
        });

        search_student_admin.setBackground(new java.awt.Color(68, 23, 82));
        search_student_admin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        search_student_admin.setForeground(new java.awt.Color(255, 255, 255));
        search_student_admin.setText("Search");
        search_student_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_student_adminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 500, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(425, 425, 425))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search_student_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(view_info_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_student_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(view_info_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Student Info", jPanel4);

        jPanel5.setBackground(new java.awt.Color(169, 68, 89));

        problem_view_admin.setBackground(new java.awt.Color(27, 24, 51));
        problem_view_admin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        problem_view_admin.setForeground(new java.awt.Color(255, 255, 255));
        problem_view_admin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sl", "Issue Id", "Reg. No", "Issue", "Status", "Date Reported", "Update", "Update Time", "Votes"
            }
        ));
        jScrollPane1.setViewportView(problem_view_admin);
        if (problem_view_admin.getColumnModel().getColumnCount() > 0) {
            problem_view_admin.getColumnModel().getColumn(1).setHeaderValue("Issue Id");
        }

        view_problem_admin.setBackground(new java.awt.Color(68, 23, 82));
        view_problem_admin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        view_problem_admin.setForeground(new java.awt.Color(255, 255, 255));
        view_problem_admin.setText("View");
        view_problem_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_problem_adminActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Reg. No");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Issue");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Update");

        submit_update_status_admin1.setBackground(new java.awt.Color(68, 23, 82));
        submit_update_status_admin1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        submit_update_status_admin1.setForeground(new java.awt.Color(255, 255, 255));
        submit_update_status_admin1.setText("Submit");
        submit_update_status_admin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_update_status_admin1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(68, 23, 82));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        update_button_update_status_admin1.setBackground(new java.awt.Color(68, 23, 82));
        update_button_update_status_admin1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        update_button_update_status_admin1.setForeground(new java.awt.Color(255, 255, 255));
        update_button_update_status_admin1.setText("Update");
        update_button_update_status_admin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_button_update_status_admin1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIssueDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(473, 473, 473))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(update_issue, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(135, 135, 135)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(update_button_update_status_admin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(submit_update_status_admin1)
                                        .addGap(220, 220, 220)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_problem_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update_button_update_status_admin1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_problem_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(submit_update_status_admin1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtIssueDetails)
                                .addComponent(update_issue, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Update Issue", jPanel5);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jPanel8.setBackground(new java.awt.Color(169, 68, 89));

        complaint_table.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        complaint_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sl", "Complaint Id", "Complaint", "Date Complained", "Related Issue", "Issue Date", "Your Update", "Time Updated ", "Votes"
            }
        ));
        jScrollPane3.setViewportView(complaint_table);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Complaint");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Update");

        view_update_status_admin.setBackground(new java.awt.Color(68, 23, 82));
        view_update_status_admin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        view_update_status_admin.setForeground(new java.awt.Color(255, 255, 255));
        view_update_status_admin.setText("View");
        view_update_status_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_update_status_adminActionPerformed(evt);
            }
        });

        update_button_update_status_admin.setBackground(new java.awt.Color(68, 23, 82));
        update_button_update_status_admin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        update_button_update_status_admin.setForeground(new java.awt.Color(255, 255, 255));
        update_button_update_status_admin.setText("Update");
        update_button_update_status_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_button_update_status_adminActionPerformed(evt);
            }
        });

        submit_update_status_admin.setBackground(new java.awt.Color(68, 23, 82));
        submit_update_status_admin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        submit_update_status_admin.setForeground(new java.awt.Color(255, 255, 255));
        submit_update_status_admin.setText("Submit");
        submit_update_status_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_update_status_adminActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(68, 23, 82));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(complaintTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(complaint_response, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(submit_update_status_admin)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(601, 601, 601)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(update_button_update_status_admin, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(view_update_status_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(submit_update_status_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_update_status_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update_button_update_status_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(complaint_response, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addComponent(complaintTxt, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 511, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Complaints", jPanel6);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setupRowSelectionListener() {
    problem_view_admin.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && problem_view_admin.getSelectedRow() != -1) {
            int selectedRow = problem_view_admin.getSelectedRow();
            txtRegNo.setText(problem_view_admin.getValueAt(selectedRow, 2).toString()); // Reg No (Non-editable)
            txtIssueDetails.setText(problem_view_admin.getValueAt(selectedRow, 3).toString()); // Issue Details (Non-editable)
            }
        });
    }

    private void view_info_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_info_adminActionPerformed

    try {
        

        // Create a prepared statement
        PreparedStatement pst = con.prepareStatement("SELECT * FROM student_info");

        // Execute the query
        ResultSet rs = pst.executeQuery();

        // Get metadata about the result set
        ResultSetMetaData rsmd = rs.getMetaData();
        int cnt = rsmd.getColumnCount(); // Number of columns in the result set

        // Get the table model
        DefaultTableModel dtm = (DefaultTableModel) student_info_admin.getModel();
        dtm.setRowCount(0); // Clear the table before populating

        // Populate the table model with data from the result set
        while (rs.next()) {
            Vector<String> v = new Vector<>();

            // Add each column's data to the vector
            for (int i = 1; i <= cnt; i++) {
                v.add(rs.getString(i)); // Add each column's value to the vector
            }

            // Add the vector as a row to the table model
            dtm.addRow(v);
        }

        // Notify the table that the data has changed
        dtm.fireTableDataChanged();

//        // Close the result set, prepared statement, and connection
//        rs.close();
//        pst.close();
//        con.close();
        student_info_admin.setDefaultRenderer(String.class, new TextAreaRenderer());
        student_info_admin.setDefaultEditor(String.class, new TextAreaEditor());

        // Adjust row heights dynamically
        adjustRowHeights();

        // Ensure the table is inside a scroll pane
        if (student_info_admin.getParent() instanceof JViewport) {
            JScrollPane scrollPane = (JScrollPane) student_info_admin.getParent().getParent();
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    
    }//GEN-LAST:event_view_info_adminActionPerformed

    private void search_student_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_student_adminActionPerformed
        String searchId = JOptionPane.showInputDialog(this, "Enter Registration Number to search:", "Search Record", JOptionPane.INFORMATION_MESSAGE);
    
    if (searchId == null || searchId.isEmpty()) {
        return;
    }
    
    
    
    String query = "SELECT * FROM student_info WHERE reg_num = ?";
    
    try {
         PreparedStatement pst = con.prepareStatement(query) ;
        pst.setString(1, searchId);
        ResultSet rs = pst.executeQuery();
        
        JPanel printPanel = new JPanel(new BorderLayout());
        JTable resultTable = new JTable();
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        printPanel.add(resultScrollPane, BorderLayout.CENTER);
        
        DefaultTableModel resultTableModel = new DefaultTableModel();
        resultTableModel.addColumn("Name");
        resultTableModel.addColumn("Reg Num");
        resultTableModel.addColumn("Dept");
        resultTableModel.addColumn("Email");
        resultTableModel.addColumn("Phone Number");
        resultTableModel.addColumn("Batch");
        resultTableModel.addColumn("Hall");
        resultTableModel.addColumn("Gender");
        
        resultTable.setModel(resultTableModel);
        
        if (rs.next()) {
            Object[] row = {
                rs.getString("name"),
                rs.getString("reg_num"),
                rs.getString("dept"),
                rs.getString("email"),
                rs.getString("phone_num"),
                rs.getString("batch"),
                rs.getString("hall"),
                rs.getString("gender")
            };
            resultTableModel.addRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Record not found for Reg Num: " + searchId, "No Record", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JFrame printFrame = new JFrame("Student Information");
        printFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        printFrame.setSize(800, 200);
        printFrame.add(printPanel);
        printFrame.setVisible(true);
        
        rs.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error searching for record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_search_student_adminActionPerformed

    private void loadIssuesForUpdate(){
        String sql = "SELECT ir.issue_id, ir.reg_no, ir.issue_details, ir.status, ir.date_reported, ir.progress_details, ir.last_updated, ir.votes FROM issue_report ir";

    try {
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) problem_view_admin.getModel();
        model.setRowCount(0); // Clear previous data

        int serial = 1;
        while (rs.next()) {
            int issueId = rs.getInt("issue_id");
            String regNo = rs.getString("reg_no");
            String issueDetails = rs.getString("issue_details");
            String status = rs.getString("status");
            String dateReported = rs.getString("date_reported");
            String progressDetails = rs.getString("progress_details");
            String lastUpdated = rs.getString("last_updated");
            int votes = rs.getInt("votes");

            Object[] row = {
                serial++,        // 0: Serial No
                issueId,         // 1: Issue ID (Hidden)
                regNo,           // 2: Reg. No
                issueDetails,    // 3: Issue Details
                status,          // 4: Status
                dateReported,    // 5: Date Reported
                progressDetails, // 7: Progress Details
                lastUpdated,     // 6: Last Updated
                votes,           // 8: Votes Count
            };
            model.addRow(row);
        }
            if (problem_view_admin.getColumnCount() == 10) {


            // Hide issue_id column (index 1)
            TableColumn hiddenColumn = problem_view_admin.getColumnModel().getColumn(1);
            hiddenColumn.setMinWidth(0);
            hiddenColumn.setMaxWidth(0);
            hiddenColumn.setPreferredWidth(0);
        }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void view_problem_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_problem_adminActionPerformed

        
         loadIssuesForUpdate();
    
    }//GEN-LAST:event_view_problem_adminActionPerformed
    private void updateIssueProgress() {
    int selectedRow = problem_view_admin.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Select an issue to update!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int issueId = (int) problem_view_admin.getValueAt(selectedRow, 1); // Issue ID (Hidden)
    String status = "In progress";
    String newProgressDetails = update_issue.getText().trim(); // Get admin's input

    if (newProgressDetails.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an update!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String sql = "UPDATE issue_report SET progress_details = ?, status=?, last_updated = NOW() WHERE issue_id = ?";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, newProgressDetails);
        pstmt.setString(2, status);
        pstmt.setInt(3, issueId);
        int rowsUpdated = pstmt.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Issue updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadIssuesForUpdate(); // Refresh the JTable
            update_issue.setText(""); // Clear the update text field
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    private void loadComplaintsForAdmin() {
    String sql = "SELECT c.complaint_id, c.complaint_text, c.complaint_date, i.issue_details, i.date_reported, i.progress_details, i.last_updated, c.votes " +
                 "FROM complaints c " +
                 "JOIN issue_report i ON c.issue_id = i.issue_id " +
                 "WHERE c.status = 'Pending'"; // Only show unresolved complaints

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = (DefaultTableModel) complaint_table.getModel();
        model.setRowCount(0); // Clear previous data

        int serial = 1;
        while (rs.next()) {
            int complaintId = rs.getInt("complaint_id");
            String complaintText = rs.getString("complaint_text");
            String complaintDate = rs.getString("complaint_date");
            String issueDetails = rs.getString("issue_details");
            String issueDate = rs.getString("date_reported");
            String issueUpdate = rs.getString("progress_details");           
            String updateTime = rs.getString("last_updated");
            int complaintVotes = rs.getInt("votes"); // Ensure this column exists

            Object[] row = {serial++, complaintId, complaintText, complaintDate, issueDetails, issueDate, issueUpdate, updateTime, complaintVotes};
            model.addRow(row);
        }

        if (complaint_table.getColumnCount() == 9) {
            // Hide complaint_id column (index 1)
            TableColumn hiddenColumn = complaint_table.getColumnModel().getColumn(1);
            hiddenColumn.setMinWidth(0);
            hiddenColumn.setMaxWidth(0);
            hiddenColumn.setPreferredWidth(0);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private void setupComplaintSelectionListener() {
    complaint_table.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && complaint_table.getSelectedRow() != -1) {
            int selectedRow = complaint_table.getSelectedRow();
            complaintTxt.setText(complaint_table.getValueAt(selectedRow, 2).toString()); // Complaint Text (Non-editable)
        }
    });
}

    private void submitComplaintResponse() {
    int selectedRow = complaint_table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Select a complaint to respond to!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int complaintId = (int) complaint_table.getValueAt(selectedRow, 1); // Complaint ID
    String adminResponse = complaint_response.getText().trim(); // Admin response

    if (adminResponse.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a response!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 🔹 Fetch `issue_id` using `complaint_id`
    int issueId = getIssueIdFromComplaint(complaintId);
    if (issueId == -1) {
        JOptionPane.showMessageDialog(this, "Failed to retrieve Issue ID!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String studentRegNo = getStudentRegNoFromComplaint(complaintId); // Fetch the student who filed the complaint
    // Determine new issue status
    String newIssueStatus = "Under Investigation"; // Default status
    if (adminResponse.toLowerCase().contains("resolved")) {
        newIssueStatus = "Resolved";
    }

    String sqlComplaint = "UPDATE complaints SET status = 'Reviewed' WHERE complaint_id = ?";
    String sqlIssue = "UPDATE issue_report SET progress_details = ?, status = ?, last_updated = NOW() WHERE issue_id = ?";
    String sqlNotification = "INSERT INTO notifications (student_reg_no, message) VALUES (?, ?)";
    
    try {
        con.setAutoCommit(false); // Begin transaction

        // Update complaint status
        PreparedStatement pstmtComplaint = con.prepareStatement(sqlComplaint);
        pstmtComplaint.setInt(1, complaintId);
        pstmtComplaint.executeUpdate();

        // Update issue progress and status
        PreparedStatement pstmtIssue = con.prepareStatement(sqlIssue);
        pstmtIssue.setString(1, adminResponse);
        pstmtIssue.setString(2, newIssueStatus);
        pstmtIssue.setInt(3, issueId);
        pstmtIssue.executeUpdate();
        
        // Insert notification for the student
        PreparedStatement pstmtNotification = con.prepareStatement(sqlNotification);
        pstmtNotification.setString(1, studentRegNo);
        pstmtNotification.setString(2, "Your complaint has been reviewed. Check for admin response.");
        pstmtNotification.executeUpdate();

        con.commit(); // Commit transaction
        JOptionPane.showMessageDialog(this, "Complaint reviewed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Refresh the table and clear input fields
        loadComplaintsForAdmin();
        complaint_response.setText("");
    } catch (SQLException e) {
        try {
            con.rollback(); // Rollback transaction on failure
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    } finally {
        try {
            con.setAutoCommit(true); // Restore auto-commit mode
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


    private int getIssueIdFromComplaint(int complaintId) {
    String sql = "SELECT issue_id FROM complaints WHERE complaint_id = ?";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, complaintId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("issue_id"); // Return issue_id
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Return -1 if issue_id is not found
}
    private String getStudentRegNoFromComplaint(int complaintId) {
    String sql = "SELECT student_reg_no FROM complaints WHERE complaint_id = ?";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, complaintId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("student_reg_no");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    private void adjustRowHeights() {
    for (int row = 0; row < problem_view_admin.getRowCount(); row++) {
        int maxHeight = 40; // Minimum row height
        for (int column = 0; column < problem_view_admin.getColumnCount(); column++) {
            TableCellRenderer cellRenderer = problem_view_admin.getCellRenderer(row, column);
            Component comp = problem_view_admin.prepareRenderer(cellRenderer, row, column);
            maxHeight = Math.max(comp.getPreferredSize().height, maxHeight);
        }
        problem_view_admin.setRowHeight(row, maxHeight);
    }
}
private void adjustRowHeights(JTable table) {
    for (int row = 0; row < table.getRowCount(); row++) {
        int maxHeight = 40; // Minimum row height
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(cellRenderer, row, column);
            maxHeight = Math.max(comp.getPreferredSize().height, maxHeight);
        }
        table.setRowHeight(row, maxHeight);
    }
}


// Custom renderer to wrap text inside JTable cells
class TextAreaRenderer extends JTextArea implements TableCellRenderer {
    public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        return this;
    }
}
    class TextAreaEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextArea textArea;
    private final JScrollPane scrollPane;

    public TextAreaEditor() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
     @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textArea.setText(value == null ? "" : value.toString());
        return scrollPane;
    }

    @Override
    public Object getCellEditorValue() {
        return textArea.getText();
    }
}
    
    
    private void submit_update_status_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_update_status_adminActionPerformed
        submitComplaintResponse();
        loadComplaintsForAdmin();
        complaintTxt.setText("");
    
    }//GEN-LAST:event_submit_update_status_adminActionPerformed

    private void update_button_update_status_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_button_update_status_adminActionPerformed
     
    }//GEN-LAST:event_update_button_update_status_adminActionPerformed

    private void view_update_status_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_update_status_adminActionPerformed
    loadComplaintsForAdmin();
    
    
    }//GEN-LAST:event_view_update_status_adminActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void submit_update_status_admin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_update_status_admin1ActionPerformed
        updateIssueProgress();
        loadIssuesForUpdate();
        
    }//GEN-LAST:event_submit_update_status_admin1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void update_button_update_status_admin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_button_update_status_admin1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_button_update_status_admin1ActionPerformed

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
            java.util.logging.Logger.getLogger(Admin_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Desk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField complaintTxt;
    private javax.swing.JTextField complaint_response;
    private javax.swing.JTable complaint_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable problem_view_admin;
    private javax.swing.JButton search_student_admin;
    private javax.swing.JTable student_info_admin;
    private javax.swing.JButton submit_update_status_admin;
    private javax.swing.JButton submit_update_status_admin1;
    private javax.swing.JTextField txtIssueDetails;
    private javax.swing.JTextField txtRegNo;
    private javax.swing.JButton update_button_update_status_admin;
    private javax.swing.JButton update_button_update_status_admin1;
    private javax.swing.JTextField update_issue;
    private javax.swing.JButton view_info_admin;
    private javax.swing.JButton view_problem_admin;
    private javax.swing.JButton view_update_status_admin;
    // End of variables declaration//GEN-END:variables
}
