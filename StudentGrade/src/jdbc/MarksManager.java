package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MarksManager {

    // Default constructor
    public MarksManager() {
        // Initialization if needed
    }

    public void insertMarks() throws Exception {
        Scanner scanner = new Scanner(System.in);

        try {
            // Connect to the database
            DBConnect.getConnect();

            // Ask the user which table to insert into
            System.out.println("Select the table to insert marks:");
            System.out.println("1. Mid1");
            System.out.println("2. Mid2");
            System.out.println("3. Internal");
            System.out.println("4. Semester Grade");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            String tableName = "";
            if (choice == 1) {
                tableName = "mid1";
            } else if (choice == 2) {
                tableName = "mid2";
            } else if (choice == 3) {
                tableName = "internal";
            } else if (choice == 4) {
                tableName = "semester_grade";
            } else {
                System.out.println("Invalid choice!");
                return;
            }

            // Get student roll number
            System.out.print("Enter Student Roll Number (VARCHAR): ");
            String studentRollNo = scanner.nextLine();

            // Get marks for all 5 subjects
            System.out.print("Enter marks for Subject 1: ");
            int subject1 = scanner.nextInt();
            System.out.print("Enter marks for Subject 2: ");
            int subject2 = scanner.nextInt();
            System.out.print("Enter marks for Subject 3: ");
            int subject3 = scanner.nextInt();
            System.out.print("Enter marks for Subject 4: ");
            int subject4 = scanner.nextInt();
            System.out.print("Enter marks for Subject 5: ");
            int subject5 = scanner.nextInt();

            // Prepare the SQL query
            String query = "INSERT INTO " + tableName + " (student_roll_no, subject1, subject2, subject3, subject4, subject5) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);

            stmt.setString(1, studentRollNo);
            stmt.setInt(2, subject1);
            stmt.setInt(3, subject2);
            stmt.setInt(4, subject3);
            stmt.setInt(5, subject4);
            stmt.setInt(6, subject5);

            // Execute the query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Marks inserted successfully into " + tableName + " table.");
            } else {
                System.out.println("Failed to insert marks.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
