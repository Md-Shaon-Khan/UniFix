/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package issuetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://bv27gvoysnt3yplaamzq-mysql.services.clever-cloud.com:3306/bv27gvoysnt3yplaamzq?useSSL=true&requireSSL=false";
    private static final String USER = "ua33ay63lfmlmsmy";
    private static final String PASSWORD = "jJSBLq8DkaeCyypwYjIc";  // Keep this safe!

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL Driver
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database Connected Successfully!");
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver Not Found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed: " + e.getMessage());
        }
        return null;
    }
}

