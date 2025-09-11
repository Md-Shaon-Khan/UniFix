/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package issuetracker;
import java.sql.Connection;
/**
 *
 * @author Md Khakan
 */
public class IssueTracker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        Home home = new Home();
//        home.setVisible(true);
//        home.pack();
//        home.setLocationRelativeTo(null);
          Connection con = DatabaseConnection.connect();
        if (con != null) {
            System.out.println("🎉 Connection successful!");
        } else {
            System.out.println("⚠️ Connection failed.");
        }
    }
    
}
