package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLogin {
    public boolean authenticateAdmin(String username, String password) {
        boolean isAuthenticated = false;
        
        try {
            DBConnect.getConnect();
            String query = "SELECT admin_id FROM admin WHERE username = ? AND password = ?";
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isAuthenticated = true;
            }
            
            rs.close();
            stmt.close();
            DBConnect.con.close();
        } catch (Exception e) {
            System.out.println("Error during admin authentication: " + e.getMessage());
        }
        
        return isAuthenticated;
    }
}