package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentLogin {
    public boolean authenticateStudent(String rollNo, String password) {
        boolean isAuthenticated = false;
        
        try {
            DBConnect.getConnect();
            String query = "SELECT student_roll_no FROM students WHERE student_roll_no = ? AND password = ?";
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);
            stmt.setString(1, rollNo);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isAuthenticated = true;
            }
            
            rs.close();
            stmt.close();
            DBConnect.con.close();
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }
        
        return isAuthenticated;
    }
}