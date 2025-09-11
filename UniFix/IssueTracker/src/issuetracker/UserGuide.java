/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package issuetracker;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Md Khakan
 */
public class UserGuide {
    public static void studentUserGuide() {
        try {
            File file = new File("src/student_user_guide.pdf"); // Change path if needed
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(null, "User guide not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening user guide.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     public static void adminGuide() {
        try {
            File file = new File("src/admin_guide.pdf"); // Change path if needed
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(null, "User guide not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening user guide.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
