package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MarksManager {
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
            int choice = Integer.parseInt(scanner.nextLine());

            String tableName = "";
            switch (choice) {
                case 1:
                    tableName = "mid1";
                    break;
                case 2:
                    tableName = "mid2";
                    break;
                case 3:
                    tableName = "internal";
                    break;
                case 4:
                    tableName = "semester_grade";
                    break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            // Get student roll number
            System.out.print("Enter Student Roll Number (VARCHAR): ");
            String studentRollNo = scanner.nextLine();

            // Get marks for all 5 subjects
            int[] subjects = new int[5];
            for (int i = 0; i < 5; i++) {
                System.out.printf("Enter marks for Subject %d: ", i + 1);
                subjects[i] = Integer.parseInt(scanner.nextLine());
            }

            // Prepare the SQL query
            String query = "INSERT INTO " + tableName + 
                " (student_roll_no, subject1, subject2, subject3, subject4, subject5) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = DBConnect.con.prepareStatement(query);

            stmt.setString(1, studentRollNo);
            for (int i = 0; i < 5; i++) {
                stmt.setInt(i + 2, subjects[i]);
            }

            // Execute the query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Marks inserted successfully into " + tableName + " table.");
            } else {
                System.out.println("Failed to insert marks.");
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error inserting marks: " + e.getMessage());
            e.printStackTrace();
        }
    }
}