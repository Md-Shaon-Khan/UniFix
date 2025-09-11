/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package issuetracker;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Path2D;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import javax.swing.JScrollBar;
import java.awt.Color;
import java.awt.Dimension;
import issuetracker.helpingclass.ModernScrollBarUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.miginfocom.swing.MigLayout;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;



/**
 *
 * @author Md Khakan
 */
public class Notifications extends javax.swing.JPanel {

   PreparedStatement pst;
    private String regNo;
     private Connection con;
        public Notifications(String regNo, Connection con) {
             this.con = con; // Store the database connection
            this.regNo = regNo;
            
            initComponents();
            setOpaque(false);
            JScrollBar sb = scroll.getVerticalScrollBar();
            sb.setOpaque(false);
            sb.setForeground(new Color(33, 140, 206));
            sb.setPreferredSize(new Dimension(8, 8));
            sb.setUI(new ModernScrollBarUI());
            scroll.getViewport().setOpaque(false);
            scroll.setViewportBorder(null);
            panel.setLayout(new MigLayout("inset 0, fillx, wrap", "[fill]"));
            loadNotification();
        }

        private void loadNotification(){
           panel.removeAll();
        String sql = "SELECT notification_type, message, issue_id, created_at FROM notifications WHERE student_reg_no = ? ORDER BY created_at DESC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, regNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("notification_type");
                String message = rs.getString("message");
                int issueId = rs.getInt("issue_id");
                String timeAgo = getTimeAgo(rs.getTimestamp("created_at"));

                Item notificationItem = new Item(message, "For the issue: " + getIssueDetails(issueId), timeAgo);
                panel.add(notificationItem);
            }
            panel.revalidate();
            panel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        
            private String getTimeAgo(Timestamp timestamp) {
         if (timestamp == null) return "Unknown time";

         LocalDateTime notificationTime = timestamp.toLocalDateTime();
         LocalDateTime now = LocalDateTime.now();

         long daysBetween = ChronoUnit.DAYS.between(notificationTime, now);

         if (daysBetween == 0) {
             return "Today";
         } else if (daysBetween == 1) {
             return "1 day ago";
         } else {
             return daysBetween + " days ago";
         }
     }
       
            private String getIssueDetails(int issueId) {
            String sql = "SELECT issue_details FROM issue_report WHERE issue_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, issueId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("issue_details");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Issue details unavailable";
        }

    
    @Override
    protected void paintComponent (Graphics grphcs){
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        int header = 10;
        AffineTransform tran = new AffineTransform();
        tran.translate(getWidth()-70, 50);
        tran.rotate(Math.toRadians(45));
        Path2D p = new Path2D.Double(new RoundRectangle2D.Double(0, 0, 20, 20, 5, 5), tran);
        Area area = new Area(p);
        area.add(new Area(new RoundRectangle2D.Double(0, header, getWidth(), getHeight(), 10, 10)));
        g2.fill(area);
        
        g2.dispose();
        super.paintComponent(grphcs);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        scroll = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 10, 10, 10));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel1.setText("Notifications");

        scroll.setBackground(new java.awt.Color(204, 204, 255));
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setOpaque(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 241, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );

        scroll.setViewportView(panel);

        jButton1.setForeground(new java.awt.Color(0, 51, 255));
        jButton1.setText("Show All");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
