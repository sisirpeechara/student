package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MarksUpdater {
    public void updateMarks() throws Exception {
        Scanner scanner = new Scanner(System.in);

        try {
            // Connect to the database
            DBConnect.getConnect();

            // Select marks table
            System.out.println("Select the table to update marks:");
            System.out.println("1. Mid1");
            System.out.println("2. Mid2");
            System.out.println("3. Internal");
            System.out.println("4. Semester Grade");
            int tableChoice = Integer.parseInt(scanner.nextLine());

            String tableName = "";
            switch (tableChoice) {
                case 1: tableName = "mid1"; break;
                case 2: tableName = "mid2"; break;
                case 3: tableName = "internal"; break;
                case 4: tableName = "semester_grade"; break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            // Get student roll number
            System.out.print("Enter Student Roll Number: ");
            String studentRollNo = scanner.nextLine();

            // Select subject to update
            System.out.println("Select subject to update:");
            System.out.println("1. Subject 1");
            System.out.println("2. Subject 2");
            System.out.println("3. Subject 3");
            System.out.println("4. Subject 4");
            System.out.println("5. Subject 5");
            int subjectChoice = Integer.parseInt(scanner.nextLine());

            // Get new marks
            System.out.print("Enter new marks: ");
            int newMarks = Integer.parseInt(scanner.nextLine());

            // Prepare SQL query
            String query = "UPDATE " + tableName + " SET subject" + subjectChoice + " = ? " +
                           "WHERE student_roll_no = ?";
            
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);
            stmt.setInt(1, newMarks);
            stmt.setString(2, studentRollNo);

            // Execute update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Marks updated successfully!");
            } else {
                System.out.println("No records found for the given roll number.");
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating marks: " + e.getMessage());
            e.printStackTrace();
        }
    }
}