package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentUpdater {

    // Update a student's details
    public void updateStudent(String rollNo, Scanner scanner) throws Exception {
        try {
            DBConnect.getConnect();
            System.out.println("Which detail do you want to update?");
            System.out.println("1. Name");
            System.out.println("2. Email");
            System.out.println("3. Phone Number");
            System.out.print("Enter your choice (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String query = "";
            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    query = "UPDATE students SET student_name = ? WHERE student_roll_no = ?";
                    executeUpdateQuery(query, newName, rollNo);
                    break;
                case 2:
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    query = "UPDATE students SET student_email = ? WHERE student_roll_no = ?";
                    executeUpdateQuery(query, newEmail, rollNo);
                    break;
                case 3:
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();
                    query = "UPDATE students SET student_ph_no = ? WHERE student_roll_no = ?";
                    executeUpdateQuery(query, newPhone, rollNo);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a student
    public void deleteStudent(String rollNo) throws Exception {
        try {
            DBConnect.getConnect();
            String query = "DELETE FROM students WHERE student_roll_no = ?";
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);
            stmt.setString(1, rollNo);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student. Roll number not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdateQuery(String query, String newValue, String rollNo) throws SQLException {
        PreparedStatement stmt = DBConnect.con.prepareStatement(query);
        stmt.setString(1, newValue);
        stmt.setString(2, rollNo);

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Student details updated successfully!");
        } else {
            System.out.println("Failed to update student details.");
        }
    }
}
