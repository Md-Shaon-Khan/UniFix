/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package issuetracker;
import issuetracker.Notifications;
import issuetracker.helpingclass.*;

/**
 *
 * @author HP
 */

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class Student_Desk extends javax.swing.JFrame  {

    /**
     * Creates new form Student_Desk
     */
 
    //private JTable table_inform;
    //Connection con;
    PreparedStatement pst;
    private int serialNumber = 1;
    private String regNo;
    private String fullName;
     private Connection con;
     private boolean isHandlingEvent = false; // Flag to prevent recursive event handling

    public Student_Desk(String regNo, String fullName, Connection con) {
        this.con = con; // Store the database connection
        this.regNo = regNo;
        if (this.con == null) {
            System.out.println("❌ Student_Desk: Connection is NULL!");
        } else {
            updateUnresolvedIssues();
            System.out.println("✅ Student_Desk: Connection Established.");
        }
        initComponents();
        updateNotificationBadge();
        GlassPanePopup.install(this);
        table_input(); // Call method to retrieve data
        //view_inform_issue_studentActionPerformed(null);//2/2/2025 change for row.
        reg_no_inform_issue.setText(regNo);
         reg_no_inform_issue.setEditable(false);
         stu_reg_num.setText(regNo);
         stu_reg_num.setEditable(false);
         stu_name.setText(fullName);
         
         show_inform_issue_table();
         loadIssuesForComplaint();
         
    table_solved.getModel().addTableModelListener(new TableModelListener() {
    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() != TableModelEvent.UPDATE) return;

        int row = e.getFirstRow();
        int column = e.getColumn();

        if (column == 9) { // Like/Unlike checkbox column
            DefaultTableModel model = (DefaultTableModel) table_solved.getModel();
            boolean isChecked = (Boolean) model.getValueAt(row, column); // Get checkbox state
            int issueId = (int) model.getValueAt(row, 1); // Get Issue ID
            int currentVotes = (int) model.getValueAt(row, 8); // Get current votes
            String studentRegNo = regNo; // Get logged-in student's reg_no

            boolean hasVotedBefore = studentHasVoted(issueId, studentRegNo);

            if (!hasVotedBefore && isChecked) {
                // If the student hasn't voted before and is checking the box
                saveStudentVote(issueId, studentRegNo, true, currentVotes + 1);
                model.setValueAt(currentVotes + 1, row, 8); // Update votes count in table
            } else if (hasVotedBefore && !isChecked) {
                // If the student had voted before but is unchecking the box
                saveStudentVote(issueId, studentRegNo, false, currentVotes - 1);
                model.setValueAt(currentVotes - 1, row, 8); // Update votes count in table
            }
        }
    }
});
   
    
    
    vote_complaint_table.getModel().addTableModelListener(new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            if (e.getType() != TableModelEvent.UPDATE) return;

            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 8) { // Like checkbox column
                DefaultTableModel model = (DefaultTableModel) vote_complaint_table.getModel();
                boolean isChecked = (Boolean) model.getValueAt(row, column); // Get checkbox state
                int complaintId = (int) model.getValueAt(row, 1); // Get complaint ID
                int currentVotes = (int) model.getValueAt(row, 7); // Get current votes
                String studentRegNo = regNo; // Get logged-in student's reg_no

                boolean hasVotedBefore = hasVotedComplaint(complaintId, studentRegNo);

                if (!hasVotedBefore && isChecked) {
                    // If the student hasn't voted before and is checking the box
                    saveComplaintVote(complaintId, studentRegNo, true, currentVotes + 1);
                    model.setValueAt(currentVotes + 1, row, 7); // Update votes count in table
                } else if (hasVotedBefore && !isChecked) {
                    // If the student had voted before but is unchecking the box
                    saveComplaintVote(complaintId, studentRegNo, false, currentVotes - 1);
                    model.setValueAt(currentVotes - 1, row, 7); // Update votes count in table
                }
            }
        }
    });
 
}   
    private void updateUnresolvedIssues() {
    String sql = "UPDATE issue_report " +
                 "SET status = 'Unresolved', " +
                 "progress_details = 'No Updates from Admin' " +
                 "WHERE (status = 'Submitted' AND DATEDIFF(NOW(), last_updated) > 3) " +
                 "OR status = 'Unresolved'";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println(rowsUpdated + " issue(s) marked as 'Unresolved' with 'No Updates from Admin'.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
    
    private boolean studentHasVoted(int issueId, String studentRegNo) {
    String sql = "SELECT liked FROM issue_votes WHERE issue_id = ? AND student_reg_no = ?";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, issueId);
        pstmt.setString(2, studentRegNo);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getBoolean("liked"); // Correctly return the boolean value
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

private void saveStudentVote(int issueId, String studentRegNo, boolean liked, int newVotes) {
    String sqlCheck = "SELECT * FROM issue_votes WHERE issue_id = ? AND student_reg_no = ?";
    String sqlInsert = "INSERT INTO issue_votes (issue_id, student_reg_no, liked) VALUES (?, ?, ?)";
    String sqlUpdate = "UPDATE issue_votes SET liked = ? WHERE issue_id = ? AND student_reg_no = ?";
    String sqlUpdateVotes = "UPDATE issue_report SET votes = ? WHERE issue_id = ?";

    try {
        PreparedStatement pstmtCheck = con.prepareStatement(sqlCheck);
        pstmtCheck.setInt(1, issueId);
        pstmtCheck.setString(2, studentRegNo);
        ResultSet rs = pstmtCheck.executeQuery();

        if (rs.next()) {
            // Update existing vote
            PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);
            pstmtUpdate.setBoolean(1, liked);
            pstmtUpdate.setInt(2, issueId);
            pstmtUpdate.setString(3, studentRegNo);
            pstmtUpdate.executeUpdate();
        } else {
            // Insert new vote
            PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert);
            pstmtInsert.setInt(1, issueId);
            pstmtInsert.setString(2, studentRegNo);
            pstmtInsert.setBoolean(3, liked);
            pstmtInsert.executeUpdate();
        }

        // Update votes count in issue_report table
        PreparedStatement pstmtUpdateVotes = con.prepareStatement(sqlUpdateVotes);
        pstmtUpdateVotes.setInt(1, newVotes);
        pstmtUpdateVotes.setInt(2, issueId);
        pstmtUpdateVotes.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private boolean hasVotedComplaint(int complaintId, String studentRegNo) {
    String sql = "SELECT liked FROM complaint_votes WHERE complaint_id = ? AND student_reg_no = ?";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, complaintId);
        pstmt.setString(2, studentRegNo);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getBoolean("liked"); // Correctly return the boolean value
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

private void saveComplaintVote(int complaintId, String studentRegNo, boolean liked, int newVotes) {
    String sqlCheck = "SELECT * FROM complaint_votes WHERE complaint_id = ? AND student_reg_no = ?";
    String sqlInsert = "INSERT INTO complaint_votes (complaint_id, student_reg_no, liked) VALUES (?, ?, ?)";
    String sqlUpdate = "UPDATE complaint_votes SET liked = ? WHERE complaint_id = ? AND student_reg_no = ?";
    String sqlUpdateVotes = "UPDATE complaints SET votes = ? WHERE complaint_id = ?";

    try {
        PreparedStatement pstmtCheck = con.prepareStatement(sqlCheck);
        pstmtCheck.setInt(1, complaintId);
        pstmtCheck.setString(2, studentRegNo);
        ResultSet rs = pstmtCheck.executeQuery();

        if (rs.next()) {
            // Update existing vote
            PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);
            pstmtUpdate.setBoolean(1, liked);
            pstmtUpdate.setInt(2, complaintId);
            pstmtUpdate.setString(3, studentRegNo);
            pstmtUpdate.executeUpdate();
        } else {
            // Insert new vote
            PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert);
            pstmtInsert.setInt(1, complaintId);
            pstmtInsert.setString(2, studentRegNo);
            pstmtInsert.setBoolean(3, liked);
            pstmtInsert.executeUpdate();
        }

        // Update votes count in issue_report table
        PreparedStatement pstmtUpdateVotes = con.prepareStatement(sqlUpdateVotes);
        pstmtUpdateVotes.setInt(1, newVotes);
        pstmtUpdateVotes.setInt(2, complaintId);
        pstmtUpdateVotes.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    

    
//    public Student_Desk(String regNo, String fullName) {
//        this.regNo=regNo;
//        this.fullName=fullName;
//        initComponents();
//        connect();
//        table_input();
        
//    }
    
    public void table_input() {

        int cnt;

    try {
        pst = con.prepareStatement("SELECT * FROM student_info");
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        cnt = rsmd.getColumnCount();

        DefaultTableModel dtm = (DefaultTableModel) table_inform.getModel();
        dtm.setRowCount(0);

        while (rs.next()) {
            Vector v = new Vector();

            for (int i = 1; i <= cnt; i++) {
                //v.add(rs.getString("Name"));         // Student Name
                v.add(rs.getString("reg_num"));   // Registration Number
               // v.add(rs.getString("Department"));   // Department
               // v.add(rs.getString("Email"));        // Email
               // v.add(rs.getString("Phone_Number")); // Phone Number
               // v.add(rs.getString("Batch"));        // Batch
               // v.add(rs.getString("Gender"));       // Gender
            }

            dtm.addRow(v);
        }

        dtm.fireTableDataChanged();
    } catch (SQLException ex) {
        java.util.logging.Logger.getLogger(Student_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    }
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        stu_phone_num = new javax.swing.JTextField();
        stu_batch = new javax.swing.JTextField();
        stu_dept = new javax.swing.JTextField();
        stu_name = new javax.swing.JTextField();
        stu_reg_num = new javax.swing.JTextField();
        stu_email = new javax.swing.JTextField();
        stu_gender = new javax.swing.JComboBox<>();
        submit_student_profile = new javax.swing.JButton();
        update_student_profile = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        stu_hall = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        reg_no_inform_issue = new javax.swing.JTextField();
        describe_issue = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_inform = new javax.swing.JTable();
        view_inform_issue_student = new javax.swing.JButton();
        submit_inform_issue_student = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_solved = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnNotification = new issuetracker.helpingclass.BadgeButton();
        jButton5 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        vote_complaint_table = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        student_desk_to_home1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        file_complaint_table = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        complaint_txt = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        submitComplaintBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(68, 23, 82));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(111, 24, 115), 4));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Student Desk");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(462, 462, 462)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel3.setBackground(new java.awt.Color(168, 136, 181));

        jTabbedPane1.setBackground(new java.awt.Color(68, 23, 82));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(51, 0, 51));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Student's Name");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Reg. No");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Department");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Email");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Phone Number");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Batch");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Gender");

        stu_phone_num.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_phone_numActionPerformed(evt);
            }
        });

        stu_batch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_batchActionPerformed(evt);
            }
        });

        stu_dept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_deptActionPerformed(evt);
            }
        });

        stu_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_nameActionPerformed(evt);
            }
        });

        stu_reg_num.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_reg_numActionPerformed(evt);
            }
        });

        stu_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_emailActionPerformed(evt);
            }
        });

        stu_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Extra" }));

        submit_student_profile.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        submit_student_profile.setText("Submit");
        submit_student_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_student_profileActionPerformed(evt);
            }
        });

        update_student_profile.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        update_student_profile.setText("Update");
        update_student_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_student_profileActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Hall");

        stu_hall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_hallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stu_name)
                            .addComponent(stu_reg_num)
                            .addComponent(stu_dept)
                            .addComponent(stu_email)
                            .addComponent(stu_phone_num)
                            .addComponent(stu_batch)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(95, 95, 95)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stu_hall)
                            .addComponent(stu_gender, 0, 440, Short.MAX_VALUE))))
                .addGap(273, 273, 273))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(update_student_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(submit_student_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stu_name, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stu_reg_num, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stu_dept, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stu_email, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stu_phone_num, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stu_batch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stu_hall, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stu_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submit_student_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_student_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Profile", jPanel4);

        jPanel8.setBackground(new java.awt.Color(255, 204, 204));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Reg. No");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Describe the issue");

        reg_no_inform_issue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reg_no_inform_issueActionPerformed(evt);
            }
        });

        table_inform.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Sl", "Reg. No", "Issue", "Reporting Time", "Votes", "Status", "Progess details"
            }
        ));
        table_inform.setRowHeight(40);
        jScrollPane1.setViewportView(table_inform);

        view_inform_issue_student.setBackground(new java.awt.Color(68, 23, 82));
        view_inform_issue_student.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        view_inform_issue_student.setForeground(new java.awt.Color(255, 255, 255));
        view_inform_issue_student.setText("View");
        view_inform_issue_student.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_inform_issue_studentActionPerformed(evt);
            }
        });

        submit_inform_issue_student.setBackground(new java.awt.Color(68, 23, 82));
        submit_inform_issue_student.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        submit_inform_issue_student.setForeground(new java.awt.Color(255, 255, 255));
        submit_inform_issue_student.setText("Submit");
        submit_inform_issue_student.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_inform_issue_studentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(149, 149, 149))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(describe_issue, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(208, 208, 208)
                                .addComponent(submit_inform_issue_student, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(view_inform_issue_student, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(reg_no_inform_issue, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reg_no_inform_issue, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_inform_issue_student, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(submit_inform_issue_student, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(describe_issue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inform Issue", jPanel5);

        jPanel6.setBackground(new java.awt.Color(189, 129, 157));

        table_solved.setBackground(new java.awt.Color(168, 136, 181));
        table_solved.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        table_solved.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sl", "Issue Id", "Reg. No", "Issue details", "Status", "Date Reported", "Last Updated", "Progress Details", "Votes", "Like"
            }
        ));
        table_solved.setRowHeight(40);
        jScrollPane2.setViewportView(table_solved);

        jButton1.setBackground(new java.awt.Color(68, 23, 82));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("View");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(68, 23, 82));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnNotification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issuetracker/icon.png"))); // NOI18N
        btnNotification.setText("badgeButton1");
        btnNotification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotificationActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(68, 23, 82));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Rate");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 716, Short.MAX_VALUE)
                        .addComponent(btnNotification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(btnNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );

        jTabbedPane1.addTab("View Issues", jPanel6);

        jPanel7.setBackground(new java.awt.Color(189, 129, 157));

        vote_complaint_table.setBackground(new java.awt.Color(168, 136, 181));
        vote_complaint_table.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        vote_complaint_table.setForeground(new java.awt.Color(255, 255, 255));
        vote_complaint_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sl", "Complaint Id", "Reg. No", "Issue details", "Status", "Given Solution", "Complaint", "Votes", "Like"
            }
        ));
        vote_complaint_table.setRowHeight(40);
        jScrollPane3.setViewportView(vote_complaint_table);

        jButton3.setBackground(new java.awt.Color(68, 23, 82));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("View");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        student_desk_to_home1.setBackground(new java.awt.Color(68, 23, 82));
        student_desk_to_home1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        student_desk_to_home1.setForeground(new java.awt.Color(255, 255, 255));
        student_desk_to_home1.setText("Home");
        student_desk_to_home1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student_desk_to_home1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(68, 23, 82));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Logout");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        file_complaint_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Sl", "Issue Id", "Reg. No", "Issue Resolved/Unsolved", "Status", "Progess details", "Reason for complaining"
            }
        ));
        file_complaint_table.setRowHeight(40);
        jScrollPane4.setViewportView(file_complaint_table);

        complaint_txt.setColumns(20);
        complaint_txt.setRows(5);
        jScrollPane5.setViewportView(complaint_txt);

        jLabel2.setText("Write you complaint:");

        submitComplaintBtn.setBackground(new java.awt.Color(68, 23, 82));
        submitComplaintBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        submitComplaintBtn.setForeground(new java.awt.Color(255, 255, 255));
        submitComplaintBtn.setText("Submit");
        submitComplaintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitComplaintBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(student_desk_to_home1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(submitComplaintBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jScrollPane4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1176, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(submitComplaintBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(student_desk_to_home1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );

        jTabbedPane1.addTab("Complain", jPanel7);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reg_no_inform_issueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reg_no_inform_issueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reg_no_inform_issueActionPerformed

//    private void show_inform_issue_table(){
//        try {
//        if (con == null) {
//            JOptionPane.showMessageDialog(this, "Database connection is not established!", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String sql = "SELECT reg_no, issue_details, date_reported, votes, status, progress_details FROM issue_report";
//        pst = con.prepareStatement(sql);
//        ResultSet rs = pst.executeQuery();
//
//        DefaultTableModel dtm = (DefaultTableModel) table_inform.getModel();
//        dtm.setRowCount(0); 
//
//        int serialNumber = 1; 
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss"); // Format for date-time
//
//        while (rs.next()) {
//            Vector<Object> row = new Vector<>();
//            row.add(serialNumber++);
//            row.add(rs.getString("reg_no"));
//            row.add(rs.getString("issue_details"));
//            
//            // Format the date-time correctly
//            Timestamp timestamp = rs.getTimestamp("date_reported");
//            String formattedDate = (timestamp != null) ? sdf.format(timestamp) : "N/A";
//            row.add(formattedDate);
//            row.add(rs.getInt("votes"));
//            row.add(rs.getString("status"));
//            row.add(rs.getString("progress_details"));
//
//            dtm.addRow(row);
//        }
//
//        if (dtm.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(this, "No issues found!", "Info", JOptionPane.INFORMATION_MESSAGE);
//        }
//
//        dtm.fireTableDataChanged();
//
//    } catch (SQLException ex) {
//        JOptionPane.showMessageDialog(this, "Error retrieving data! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        ex.printStackTrace();
//    }
//}
    
//    
// private void show_inform_issue_table() {
//    try {
//        if (con == null) {
//            JOptionPane.showMessageDialog(this, "Database connection is not established!", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String sql = "SELECT reg_no, issue_details, date_reported, votes, status, progress_details FROM issue_report";
//        pst = con.prepareStatement(sql);
//        ResultSet rs = pst.executeQuery();
//
//        DefaultTableModel dtm = (DefaultTableModel) table_inform.getModel();
//        dtm.setRowCount(0); 
//
//        int serialNumber = 1; 
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
//
//        while (rs.next()) {
//            Vector<Object> row = new Vector<>();
//            row.add(serialNumber++);
//            row.add(rs.getString("reg_no"));
//            
//            // Wrapping issue_details if more than 6 words
//            String issueDetails = rs.getString("issue_details");
//            if (issueDetails != null) {
//                String[] words = issueDetails.split(" ");
//                StringBuilder wrappedText = new StringBuilder();
//                int wordCount = 0;
//
//                for (String word : words) {
//                    wrappedText.append(word).append(" ");
//                    wordCount++;
//                    if (wordCount % 5 == 0) {
//                        wrappedText.append("\n");  // Line break after every 5 words
//                    }
//                }
//                row.add(wrappedText.toString().trim());
//            } else {
//                row.add("N/A");
//            }
//            
//            // Format the date-time correctly
//            Timestamp timestamp = rs.getTimestamp("date_reported");
//            String formattedDate = (timestamp != null) ? sdf.format(timestamp) : "N/A";
//            row.add(formattedDate);
//            row.add(rs.getInt("votes"));
//            row.add(rs.getString("status"));
//            row.add(rs.getString("progress_details"));
//
//            dtm.addRow(row);
//        }
//
//        if (dtm.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(this, "No issues found!", "Info", JOptionPane.INFORMATION_MESSAGE);
//        }
//
//        dtm.fireTableDataChanged();
//
//        // Enable text wrapping in the table cells
//        table_inform.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
//                textArea.setLineWrap(true);
//                textArea.setWrapStyleWord(true);
//                textArea.setOpaque(true);
//                if (isSelected) {
//                    textArea.setBackground(table.getSelectionBackground());
//                    textArea.setForeground(table.getSelectionForeground());
//                } else {
//                    textArea.setBackground(table.getBackground());
//                    textArea.setForeground(table.getForeground());
//                }
//                return textArea;
//            }
//        });
//
//    } catch (SQLException ex) {
//        JOptionPane.showMessageDialog(this, "Error retrieving data! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        ex.printStackTrace();
//    }
//}
//  
//private void show_inform_issue_table() {
//    try {
//        if (con == null) {
//            JOptionPane.showMessageDialog(this, "Database connection is not established!", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String sql = "SELECT reg_no, issue_details, date_reported, votes, status, progress_details FROM issue_report";
//        pst = con.prepareStatement(sql);
//        ResultSet rs = pst.executeQuery();
//
//        DefaultTableModel dtm = (DefaultTableModel) table_inform.getModel();
//        dtm.setRowCount(0); 
//
//        int serialNumber = 1; 
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
//
//        // Temporary storage to track row heights
//        List<Integer> rowHeights = new ArrayList<>();
//
//        while (rs.next()) {
//            Vector<Object> row = new Vector<>();
//            row.add(serialNumber++);
//            row.add(rs.getString("reg_no"));
//            
//            // Wrapping issue_details and calculating row height
//            String issueDetails = rs.getString("issue_details");
//            int lineCount = 1;
//            if (issueDetails != null) {
//                String[] words = issueDetails.split(" ");
//                StringBuilder wrappedText = new StringBuilder();
//                int wordCount = 0;
//
//                for (String word : words) {
//                    wrappedText.append(word).append(" ");
//                    wordCount++;
//                    if (wordCount % 5 == 0) {
//                        wrappedText.append("\n");
//                        lineCount++;
//                    }
//                }
//                row.add(wrappedText.toString().trim());
//            } else {
//                row.add("N/A");
//            }
//            
//            // Calculate row height proportionally by number of lines
//            rowHeights.add(lineCount * 20);  // Each line gets 20px height
//
//            // Format the date-time correctly
//            Timestamp timestamp = rs.getTimestamp("date_reported");
//            String formattedDate = (timestamp != null) ? sdf.format(timestamp) : "N/A";
//            row.add(formattedDate);
//            row.add(rs.getInt("votes"));
//            row.add(rs.getString("status"));
//            row.add(rs.getString("progress_details"));
//
//            dtm.addRow(row);
//        }
//
//        if (dtm.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(this, "No issues found!", "Info", JOptionPane.INFORMATION_MESSAGE);
//        }
//
//        dtm.fireTableDataChanged();
//
//        // Enable text wrapping in the table cells
//        table_inform.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
//                textArea.setLineWrap(true);
//                textArea.setWrapStyleWord(true);
//                textArea.setOpaque(true);
//                if (isSelected) {
//                    textArea.setBackground(table.getSelectionBackground());
//                    textArea.setForeground(table.getSelectionForeground());
//                } else {
//                    textArea.setBackground(table.getBackground());
//                    textArea.setForeground(table.getForeground());
//                }
//                return textArea;
//            }
//        });
//
//        // Adjust row heights based on calculated line counts
//        for (int i = 0; i < rowHeights.size(); i++) {
//            table_inform.setRowHeight(i, rowHeights.get(i));
//        }
//
//    } catch (SQLException ex) {
//        JOptionPane.showMessageDialog(this, "Error retrieving data! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        ex.printStackTrace();
//    }
//}
private void show_inform_issue_table() {
    try {
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT reg_no, issue_details, date_reported, votes, status, progress_details FROM issue_report";
        pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel dtm = (DefaultTableModel) table_inform.getModel();
        dtm.setRowCount(0); 

        int serialNumber = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

        // Store row heights
        List<Integer> rowHeights = new ArrayList<>();

        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(serialNumber++);
            row.add(rs.getString("reg_no"));

            // Calculate line count for issue_details
            String issueDetails = rs.getString("issue_details");
            int issueLines = calculateLineCount(issueDetails);

            // Calculate line count for progress_details
            String progressDetails = rs.getString("progress_details");
            int progressLines = calculateLineCount(progressDetails);

            // Add the details to the row
            row.add(issueDetails != null ? issueDetails : "N/A");
            row.add(rs.getTimestamp("date_reported") != null ? sdf.format(rs.getTimestamp("date_reported")) : "N/A");
            row.add(rs.getInt("votes"));
            row.add(rs.getString("status"));
            row.add(progressDetails != null ? progressDetails : "N/A");

            dtm.addRow(row);

            // Determine the larger line count between issue and progress details
            int maxLines = Math.max(issueLines, progressLines);

            // Set row height proportionally (20px per line)
            rowHeights.add(maxLines * 20);
        }

        if (dtm.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No issues found!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        dtm.fireTableDataChanged();

        // Enable text wrapping in table cells
        table_inform.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(true);

                if (isSelected) {
                    textArea.setBackground(table.getSelectionBackground());
                    textArea.setForeground(table.getSelectionForeground());
                } else {
                    textArea.setBackground(table.getBackground());
                    textArea.setForeground(table.getForeground());
                }

                return textArea;
            }
        });

        // Adjust row heights based on calculated values
        for (int i = 0; i < rowHeights.size(); i++) {
            table_inform.setRowHeight(i, rowHeights.get(i));
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error retrieving data! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

// Method to calculate the number of lines required for the text
private int calculateLineCount(String text) {
    if (text == null || text.isEmpty()) return 1;  // Empty or null gets one line

    String[] words = text.split(" ");
    int lineCount = 1;
    int wordCount = 0;

    for (String word : words) {
        wordCount++;
        if (wordCount % 5 == 0) {
            lineCount++;
        }
    }
    return lineCount;
}

    
    private void view_inform_issue_studentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_inform_issue_studentActionPerformed
    
        show_inform_issue_table();
    }//GEN-LAST:event_view_inform_issue_studentActionPerformed
   

    private void stu_phone_numActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_phone_numActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_phone_numActionPerformed

    private void stu_batchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_batchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_batchActionPerformed

    private void stu_deptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_deptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_deptActionPerformed

    private void stu_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_nameActionPerformed

    private void stu_reg_numActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_reg_numActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_reg_numActionPerformed

    private void stu_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_emailActionPerformed

    private void submit_student_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_student_profileActionPerformed
       String stu_namee, stu_reg_numm,stu_deptt,stu_emaill,stu_phone_numm,stu_batchh,stu_halll,stu_genderr;
       
       stu_namee = stu_name.getText();
       stu_reg_numm = stu_reg_num.getText();
       stu_deptt = stu_dept.getText();
       stu_emaill = stu_email.getText();
       stu_phone_numm = stu_phone_num.getText();
       stu_batchh = stu_batch.getText();
       stu_halll=stu_hall.getText();
       stu_genderr = (String)stu_gender.getSelectedItem();
       
       
       try {
    // Prepare SQL query
    pst = con.prepareStatement("INSERT INTO student_info (name,reg_num,dept,email,phone_num,batch, hall, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

    // Set values to the query
    pst.setString(1, stu_namee);        // Name
    pst.setString(2, stu_reg_numm);     // Registration Number
    pst.setString(3, stu_deptt);        // Department
    pst.setString(4, stu_emaill);       // Email
    pst.setString(5, stu_phone_numm);   // Phone Number
    pst.setString(6, stu_batchh);       // Batch
    pst.setString(7, stu_halll);
    pst.setString(8, stu_genderr);      // Gender

    pst.executeUpdate();

    // Show success message
    JOptionPane.showMessageDialog(this, "Saved");

    // Clear input fields
    stu_name.setText("");
    stu_reg_num.setText("");
    stu_dept.setText("");
    stu_email.setText("");
    stu_phone_num.setText("");
    stu_batch.setText("");
    stu_hall.setText("");
    stu_gender.setSelectedIndex(0);
    stu_name.requestFocus();

    // Refresh tables
    table_input();
   

} catch (SQLException ex) {
    // Show error message
    JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
    java.util.logging.Logger.getLogger(Student_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
}

    }//GEN-LAST:event_submit_student_profileActionPerformed

    private void update_student_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_student_profileActionPerformed
        if (!stu_reg_num.getText().isEmpty()) {
        // Second Click: Perform the update in the database
        try {
            pst = con.prepareStatement("UPDATE student_info SET name=?, dept=?, email=?, phone_num=?, batch=?, hall =?, gender=? WHERE reg_num=?");

            pst.setString(1, stu_name.getText());
            pst.setString(2, stu_dept.getText());
            pst.setString(3, stu_email.getText());
            pst.setString(4, stu_phone_num.getText());
            pst.setString(5, stu_batch.getText());
            pst.setString(6, stu_hall.getText());
            pst.setString(7, stu_gender.getSelectedItem().toString());
            pst.setString(8, stu_reg_num.getText()); // Registration Number (Primary Key)

            int updatedRows = pst.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(this, "Student Record Updated Successfully!");

                // Clear Fields after Update
                stu_reg_num.setText("");
                stu_name.setText("");
                stu_dept.setText("");
                stu_email.setText("");
                stu_phone_num.setText("");
                stu_batch.setText("");
                stu_hall.setText("");
                stu_gender.setSelectedIndex(0); // Reset ComboBox
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed! No changes made.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    } else {
        // First Click: Ask for Registration Number and Fetch Data
        String regNumber = JOptionPane.showInputDialog(this, "Enter Registration Number to Update: ");

        if (regNumber == null || regNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Registration Number cannot be empty!");
            return;
        }

        try {
            pst = con.prepareStatement("SELECT * FROM student_info WHERE reg_num = ?");
            pst.setString(1, regNumber);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Fill text fields with existing data
                stu_reg_num.setText(rs.getString("reg_num"));
                stu_name.setText(rs.getString("name"));
                stu_dept.setText(rs.getString("dept"));
                stu_email.setText(rs.getString("email"));
                stu_phone_num.setText(rs.getString("phone_num"));
                stu_batch.setText(rs.getString("batch"));
                stu_hall.setText(rs.getString("hall"));
                stu_gender.setSelectedItem(rs.getString("gender"));

                // Notify user to edit details
                JOptionPane.showMessageDialog(this, "Update the fields and click 'Update' again to save changes.");
            } else {
                JOptionPane.showMessageDialog(this, "No student found with Registration Number: " + regNumber);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_update_student_profileActionPerformed


    private void submit_inform_issue_studentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_inform_issue_studentActionPerformed


    try {
    // Fetch the registration number from the text field
    String reg_no_inform_issuee = reg_no_inform_issue.getText().trim();
    
    // Check if registration number is empty
    if (reg_no_inform_issuee.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Registration number is missing!", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Get issue description from the text field
    String describe_issuee = describe_issue.getText().trim();
    
    // Check if issue description is empty
    if (describe_issuee.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an issue description.", "Input Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Get current local date and time in 'Asia/Dhaka' timezone
    ZoneId zoneId = ZoneId.of("Asia/Dhaka");  // Specify your local timezone
    LocalDateTime localDateTime = LocalDateTime.now(zoneId);  // Get the current time in your timezone
    String issued_date = localDateTime.toString();  // Convert LocalDateTime to String format
    
    // Prepare SQL Insert Query
    String sqlInsert = "INSERT INTO issue_report (reg_no, issue_details, date_reported, status, last_updated, progress_details, votes) VALUES (?, ?, ?, ?, ?, ?, ?)";

    pst = con.prepareStatement(sqlInsert);
    pst.setString(1, reg_no_inform_issuee);
    pst.setString(2, describe_issuee);
    pst.setString(3, issued_date);   // Insert current local date and time for 'date_reported'
    pst.setString(4, "Submitted");   // Initial status
    pst.setString(5, issued_date);   // Use the same 'issued_date' for 'last_updated'
    pst.setString(6, "Pending Admin Review"); // Initial progress details
    pst.setInt(7, 0);  // Votes initialized to 0

    // Execute the insert statement
    int rowsAffected = pst.executeUpdate();
    if (rowsAffected > 0) {
        JOptionPane.showMessageDialog(this, "Issue submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Failed to submit issue.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    } catch (SQLException ex) {
        java.util.logging.Logger.getLogger(Student_Desk.class.getName())
                .log(java.util.logging.Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Database error! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_submit_inform_issue_studentActionPerformed

    private void stu_hallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_hallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_hallActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

//String sql = "SELECT ir.issue_id, ir.reg_no, ir.issue_details, ir.status, ir.date_reported, ir.last_updated, ir.progress_details, ir.votes, " +
//                 "(SELECT liked FROM issue_votes WHERE issue_id = ir.issue_id AND student_reg_no = ?) AS liked " +
//                 "FROM issue_report ir";
//
//    try {
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setString(1, regNo); // Pass the logged-in student's reg_no
//        ResultSet rs = pstmt.executeQuery();
//
//        DefaultTableModel model = (DefaultTableModel) table_solved.getModel();
//        model.setRowCount(0); // Clear previous data
//
//        int serial = 1;
//
//        // Store row heights
//        List<Integer> rowHeights = new ArrayList<>();
//
//        while (rs.next()) {
//            int issueId = rs.getInt("issue_id");
//            String regNo = rs.getString("reg_no");
//            String issueDetails = rs.getString("issue_details");
//            String status = rs.getString("status");
//            String dateReported = rs.getString("date_reported");
//            String lastUpdated = rs.getString("last_updated");
//            String progressDetails = rs.getString("progress_details");
//            int votes = rs.getInt("votes");
//            boolean liked = rs.getBoolean("liked"); // Check if student has already liked this issue
//
//            // Calculate the number of lines required for issue_details and progress_details
//            int issueLines = calculateLineCount1(issueDetails);
//            int progressLines = calculateLineCount1(progressDetails);
//
//            Object[] row = {
//                serial++,        // 0: Serial No
//                issueId,         // 1: Issue ID (Hidden)
//                regNo,           // 2: Reg. No
//                wrapText(issueDetails),    // 3: Issue Details (wrapped)
//                status,          // 4: Status
//                dateReported,    // 5: Date Reported
//                lastUpdated,     // 6: Last Updated
//                wrapText(progressDetails), // 7: Progress Details (wrapped)
//                votes,           // 8: Votes Count
//                liked            // 9: Like Checkbox (pre-set from DB)
//            };
//            model.addRow(row);
//
//            // Determine the maximum number of lines between issue_details and progress_details
//            int maxLines = Math.max(issueLines, progressLines);
//
//            // Store the calculated row height (each line will have 20px height)
//            rowHeights.add(maxLines * 20);
//        }
//
//        // Adjust the row height based on the largest content
//        for (int i = 0; i < rowHeights.size(); i++) {
//            table_solved.setRowHeight(i, rowHeights.get(i));
//        }
//
//        // Ensure table has exactly 10 columns before setting checkbox
//        if (table_solved.getColumnCount() == 10) {
//            TableColumn likeColumn = table_solved.getColumnModel().getColumn(9);
//            likeColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
//            likeColumn.setCellRenderer(new BooleanCellRenderer());
//
//            // Hide issue_id column (index 1)
//            TableColumn hiddenColumn = table_solved.getColumnModel().getColumn(1);
//            hiddenColumn.setMinWidth(0);
//            hiddenColumn.setMaxWidth(0);
//            hiddenColumn.setPreferredWidth(0);
//        }
//
//    } catch (SQLException e) {
//        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    }


    String sql = "SELECT ir.issue_id, ir.reg_no, ir.issue_details, ir.status, ir.date_reported, ir.last_updated, ir.progress_details, ir.votes, " +
                 "(SELECT liked FROM issue_votes WHERE issue_id = ir.issue_id AND student_reg_no = ?) AS liked " +
                 "FROM issue_report ir";

    try {
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, regNo); // Pass the logged-in student's reg_no
        ResultSet rs = pstmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table_solved.getModel();
        model.setRowCount(0); // Clear previous data

        int serial = 1;

        // Store row heights
        List<Integer> rowHeights = new ArrayList<>();

        while (rs.next()) {
            int issueId = rs.getInt("issue_id");
            String regNo = rs.getString("reg_no");
            String issueDetails = rs.getString("issue_details");
            String status = rs.getString("status");
            String dateReported = rs.getString("date_reported");
            String lastUpdated = rs.getString("last_updated");
            String progressDetails = rs.getString("progress_details");
            int votes = rs.getInt("votes");
            boolean liked = rs.getBoolean("liked"); // Check if student has already liked this issue

            // Calculate the number of lines required for issue_details and progress_details
            int issueLines = calculateLineCount1(issueDetails);
            int progressLines = calculateLineCount1(progressDetails);

            Object[] row = {
                serial++,        // 0: Serial No
                issueId,         // 1: Issue ID (Hidden)
                regNo,           // 2: Reg. No
                wrapText(issueDetails),    // 3: Issue Details (wrapped)
                status,          // 4: Status
                dateReported,    // 5: Date Reported
                lastUpdated,     // 6: Last Updated
                wrapText(progressDetails), // 7: Progress Details (wrapped)
                votes,           // 8: Votes Count
                liked            // 9: Like Checkbox (pre-set from DB)
            };
            model.addRow(row);

            // Determine the maximum number of lines between issue_details and progress_details
            int maxLines = Math.max(issueLines, progressLines);

            // Store the calculated row height (each line will have 20px height)
            rowHeights.add(maxLines * 20);
        }

        // Adjust the row height based on the largest content
        for (int i = 0; i < rowHeights.size(); i++) {
            table_solved.setRowHeight(i, rowHeights.get(i));
        }
          table_solved.revalidate();
        table_solved.repaint();

        // Adjust table's row height based on content (if needed)
        table_solved.setPreferredScrollableViewportSize(new Dimension(
            table_solved.getPreferredSize().width, table_solved.getRowHeight() * model.getRowCount()));

        // Ensure table has exactly 10 columns before setting checkbox
        if (table_solved.getColumnCount() == 10) {
            TableColumn likeColumn = table_solved.getColumnModel().getColumn(9);
            likeColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
            likeColumn.setCellRenderer(new BooleanCellRenderer());

            // Hide issue_id column (index 1)
            TableColumn hiddenColumn = table_solved.getColumnModel().getColumn(1);
            hiddenColumn.setMinWidth(0);
            hiddenColumn.setMaxWidth(0);
            hiddenColumn.setPreferredWidth(0);
        }

        // Set custom cell renderer for wrapped text in the issue_details and progress_details columns
        table_solved.getColumnModel().getColumn(3).setCellRenderer(new WordWrapRenderer());
        table_solved.getColumnModel().getColumn(7).setCellRenderer(new WordWrapRenderer());
        
        
         table_solved.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(200, 200, 200)); // Light gray for even rows
                } else {
                  c.setBackground(new Color(180, 180, 180));// White for odd rows
                }
                return c;
            }
        });
         
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_jButton1ActionPerformed

    private int calculateLineCount1(String text) {
      if (text == null || text.isEmpty()) return 1;  // Empty or null gets one line

    // Split the text into words
    String[] words = text.split(" ");
    int lineCount = 1;  // Start with the first line
    int wordCount = 0;

    // Calculate how many lines are needed (5 words per line)
    for (String word : words) {
        wordCount++;
        if (wordCount % 5 == 0) {
            lineCount++;
        }
    }
    return lineCount;
}
    
    private String wrapText(String text) {
      if (text == null || text.isEmpty()) return ""; // Handle empty or null strings

    StringBuilder wrappedText = new StringBuilder();
    String[] words = text.split(" ");
    int wordCount = 0;

    // Add words to the string builder and insert line breaks after every 5 words
    for (String word : words) {
        if (wordCount > 0 && wordCount % 5 == 0) {
            wrappedText.append("\n");  // Insert a line break
        }
        wrappedText.append(word).append(" ");
        wordCount++;
    }

    return wrappedText.toString().trim(); 
}
    class WordWrapRenderer extends JTextArea implements TableCellRenderer {
    public WordWrapRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true); // Make sure it has a background color
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding inside the cell
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        setFont(table.getFont());
        setPreferredSize(new Dimension(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height));
        return this;
    }
}
    // Method to update votes in the database using issue_id
// Method to update votes in the database using issue_id
private void updateVotesInDatabase(int issueId, int newVotes) {
    String sql = "UPDATE issue_report SET votes = ? WHERE issue_id = ?";
    
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, newVotes);
        pstmt.setInt(2, issueId);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Votes updated successfully for Issue ID: " + issueId);
        } else {
            System.out.println("Failed to update votes for Issue ID: " + issueId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private void loadIssuesForComplaint() {
    String sql = "SELECT i.issue_id, i.reg_no, i.issue_details, i.status, i.progress_details, " +
                 "(SELECT complaint_text FROM complaints WHERE issue_id = i.issue_id LIMIT 1) AS complaint_text " +
                 "FROM issue_report i " +
                 "WHERE i.status IN ('Resolved', 'Unresolved')";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = (DefaultTableModel) file_complaint_table.getModel();
        model.setRowCount(0); // Clear previous data

        int serial = 1;
        while (rs.next()) {
            int issueId = rs.getInt("issue_id");
            String regNo = rs.getString("reg_no");
            String status = rs.getString("status");
            String issue = rs.getString("issue_details");
            String progressDetails = rs.getString("progress_details");
            String complaintText = rs.getString("complaint_text");

            // If no complaint exists yet, set it as an empty string
            if (complaintText == null) {
                complaintText = "";
            }

            Object[] row = {serial++, issueId, regNo, issue, status, progressDetails, complaintText};
            model.addRow(row);
        }
        // Ensure table has exactly 7 columns 
        if (file_complaint_table.getColumnCount() == 7) {
            
            // Hide issue_id column (index 1)
            TableColumn hiddenColumn = file_complaint_table.getColumnModel().getColumn(1);
            hiddenColumn.setMinWidth(0);
            hiddenColumn.setMaxWidth(0);
            hiddenColumn.setPreferredWidth(0);
        }       

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    private void submitComplaint() {
    int row = file_complaint_table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select an issue to file a complaint!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int issueId = (int) file_complaint_table.getValueAt(row, 1);
    String studentRegNo = regNo; // Logged-in student
    String complaintText = complaint_txt.getText().trim();

    if (complaintText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a reason for the complaint!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try (PreparedStatement ps = con.prepareStatement("INSERT INTO complaints (issue_id, student_reg_no, complaint_text) VALUES (?, ?, ?)")) {
        ps.setInt(1, issueId);
        ps.setString(2, studentRegNo);
        ps.setString(3, complaintText);
        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Complaint submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // 🔹 Reload the table to show the new complaint
        loadIssuesForComplaint();

        // 🔹 Clear the complaint text area after submission
        complaint_txt.setText("");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    private void loadComplaintsForVoting() {
    String sql = "SELECT c.complaint_id, i.reg_no, i.issue_details, i.status, i.progress_details, c.complaint_text, c.votes, " +
                 "(SELECT liked FROM complaint_votes WHERE complaint_id = c.complaint_id AND student_reg_no = ?) AS liked " +
                 "FROM complaints c JOIN issue_report i ON c.issue_id = i.issue_id";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, regNo); // Logged-in student's reg_no
        ResultSet rs = pstmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) vote_complaint_table.getModel();
        model.setRowCount(0); // Clear previous data

        int serial = 1;
        while (rs.next()) {
            int complaintId = rs.getInt("complaint_id");
           // int issueId = rs.getInt("issue_id");
            String regNo = rs.getString("reg_no");
            String issueDetails = rs.getString("issue_details");
            String status = rs.getString("status");
            String progressDetails = rs.getString("progress_details");
            String complaintText = rs.getString("complaint_text");
            int votes = rs.getInt("votes");
            boolean liked = rs.getBoolean("liked");

            Object[] row = {serial++, complaintId, regNo, issueDetails, status, progressDetails, complaintText, votes, liked};
            model.addRow(row);
            }
            
        
            // Ensure table has exactly 9 columns before setting checkbox
            if (vote_complaint_table.getColumnCount() == 9) {
                 TableColumn likeColumn = vote_complaint_table.getColumnModel().getColumn(8);
                likeColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
                likeColumn.setCellRenderer(new BooleanCellRenderer());


                // Hide issue_id column (index 1)
                TableColumn hiddenColumn = vote_complaint_table.getColumnModel().getColumn(1);
                hiddenColumn.setMinWidth(0);
                hiddenColumn.setMaxWidth(0);
                hiddenColumn.setPreferredWidth(0);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUnreadNotificationCount() {
    String sql = "SELECT COUNT(*) AS unread_count FROM notifications WHERE student_reg_no = ? AND is_read = FALSE";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, regNo);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("unread_count");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

    private void updateNotificationBadge() {
    int unreadCount = getUnreadNotificationCount();
    btnNotification.setText(unreadCount > 0 ? String.valueOf(unreadCount) : ""); // Hide if 0
}




    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      int a = JOptionPane.showConfirmDialog(this, "Do You want to logout now?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        loadComplaintsForVoting();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void student_desk_to_home1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student_desk_to_home1ActionPerformed
        Home home1 = new Home();
        home1.setVisible(true); // Show the StudentSignup window
        this.dispose();       
    }//GEN-LAST:event_student_desk_to_home1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void submitComplaintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitComplaintBtnActionPerformed
        submitComplaint();
    }//GEN-LAST:event_submitComplaintBtnActionPerformed

    private void btnNotificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificationActionPerformed
        GlassPanePopup.showPopup(new Notifications());
    }//GEN-LAST:event_btnNotificationActionPerformed
    
    

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

String ratingText = JOptionPane.showInputDialog(this, "Please enter a rating between 1 and 5:");

    // Validate the input
    try {
        int rating = Integer.parseInt(ratingText); // Parse the input
        if (rating < 1 || rating > 5) {
            JOptionPane.showMessageDialog(this, "Please enter a rating between 1 and 5.");
            return;
        }

        // Check if the student has already rated
        if (hasRatedBefore(regNo)) {
            // If the student has rated before, update the rating
            updateRatingInDatabase(rating);
        } else {
            // If the student has not rated before, insert the new rating
            saveRatingToDatabase(rating);
        }

        // Show average rating of all students
        showOverallAverageRating();  // Show the average rating of all students

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a number between 1 and 5.");
    }
    }//GEN-LAST:event_jButton5ActionPerformed

private boolean hasRatedBefore(String regNo) {
    try {
        String sql = "SELECT COUNT(*) FROM student_ratings WHERE reg_num = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, regNo);
        ResultSet rs = pst.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            return true; // Student has rated before
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error checking if the student has rated.");
    }
    return false;
}

// Method to insert the rating if the student hasn't rated before
private void saveRatingToDatabase(int rating) {
    try {
        String sql = "INSERT INTO student_ratings (reg_num, rating) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, regNo);  // Student's registration number
        pst.setInt(2, rating);    // Rating value

        pst.executeUpdate();  // Execute the query to insert the rating
        System.out.println("Rating submitted successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving rating.");
    }
}

// Method to update the rating if the student has already rated
private void updateRatingInDatabase(int rating) {
    try {
        String sql = "UPDATE student_ratings SET rating = ? WHERE reg_num = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, rating);    // New rating value
        pst.setString(2, regNo);  // Student's registration number

        pst.executeUpdate();  // Execute the query to update the rating
        System.out.println("Rating updated successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating rating.");
    }
}

// Method to show the overall average rating of all students
private void showOverallAverageRating() {
    try {
        String sql = "SELECT AVG(rating) AS avg_rating FROM student_ratings";
        PreparedStatement pst = con.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            double avgRating = rs.getDouble("avg_rating");
            JOptionPane.showMessageDialog(this, "Overall average rating for all students: " + avgRating);
        } else {
            JOptionPane.showMessageDialog(this, "No ratings available.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching average rating.");
    }
}    
    
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
            java.util.logging.Logger.getLogger(Student_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_Desk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Student_Desk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private issuetracker.helpingclass.BadgeButton btnNotification;
    private javax.swing.JTextArea complaint_txt;
    private javax.swing.JTextField describe_issue;
    private javax.swing.JTable file_complaint_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField reg_no_inform_issue;
    private javax.swing.JTextField stu_batch;
    private javax.swing.JTextField stu_dept;
    private javax.swing.JTextField stu_email;
    private javax.swing.JComboBox<String> stu_gender;
    private javax.swing.JTextField stu_hall;
    private javax.swing.JTextField stu_name;
    private javax.swing.JTextField stu_phone_num;
    private javax.swing.JTextField stu_reg_num;
    private javax.swing.JButton student_desk_to_home1;
    private javax.swing.JButton submitComplaintBtn;
    private javax.swing.JButton submit_inform_issue_student;
    private javax.swing.JButton submit_student_profile;
    private javax.swing.JTable table_inform;
    private javax.swing.JTable table_solved;
    private javax.swing.JButton update_student_profile;
    private javax.swing.JButton view_inform_issue_student;
    private javax.swing.JTable vote_complaint_table;
    // End of variables declaration//GEN-END:variables
}
