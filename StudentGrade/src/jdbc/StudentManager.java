package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentManager {
    public void addStudent(String name, String rollNo, String email, String phone, String password) {
        try {
            DBConnect.getConnect();
            String query = "INSERT INTO students (student_name, student_roll_no, student_email, student_ph_no, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);
            
            // Set parameters
            stmt.setString(1, name);
            stmt.setString(2, rollNo);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, password);
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Failed to add student.");
            }
            
            stmt.close();
            DBConnect.con.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Error: A student with this roll number or email already exists.");
            } else {
                System.out.println("Error while adding student: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}