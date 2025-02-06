package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentPasswordManager {
    public boolean changePassword(String rollNo, String oldPassword, String newPassword) {
        try {
            DBConnect.getConnect();
            
            // First verify the old password
            String verifyQuery = "SELECT COUNT(*) FROM students WHERE student_roll_no = ? AND password = ?";
            PreparedStatement verifyStmt = DBConnect.con.prepareStatement(verifyQuery);
            verifyStmt.setString(1, rollNo);
            verifyStmt.setString(2, oldPassword);
            
            ResultSet rs = verifyStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Current password is incorrect.");
                return false;
            }
            
            // Update to new password
            String updateQuery = "UPDATE students SET password = ? WHERE student_roll_no = ?";
            PreparedStatement updateStmt = DBConnect.con.prepareStatement(updateQuery);
            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, rollNo);
            
            int rowsUpdated = updateStmt.executeUpdate();
            
            rs.close();
            verifyStmt.close();
            updateStmt.close();
            DBConnect.con.close();
            
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error while changing password: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}