package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminManager {
    public void addAdmin(String username, String password) {
        try {
            DBConnect.getConnect();
            
            // First check if admin already exists
            String checkQuery = "SELECT COUNT(*) FROM admin WHERE username = ?";
            PreparedStatement checkStmt = DBConnect.con.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("Error: An admin with this username already exists.");
                return;
            }
            
            // If admin doesn't exist, proceed with insertion
            String query = "INSERT INTO admin (username, password) VALUES (?, ?)";
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Admin user added successfully!");
            } else {
                System.out.println("Failed to add admin user.");
            }
            
            rs.close();
            checkStmt.close();
            stmt.close();
            DBConnect.con.close();
        } catch (SQLException e) {
            System.out.println("Error while adding admin: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}