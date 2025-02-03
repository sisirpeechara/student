package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportCardGenerator {
    public void generateReportCard(String rollNo) throws Exception {
        try {
            // Connect to the database
            DBConnect.getConnect();

            // Prepare SQL queries to fetch marks from different tables
            String[] tables = {"mid1", "mid2", "internal", "semester_grade"};
            int[][] marks = new int[5][tables.length];

            // Fetch marks for each subject from each table
            for (int subject = 0; subject < 5; subject++) {
                for (int tableIndex = 0; tableIndex < tables.length; tableIndex++) {
                    String query = "SELECT subject" + (subject + 1) + 
                        " FROM " + tables[tableIndex] + 
                        " WHERE student_roll_no = ?";
                    
                    PreparedStatement stmt = DBConnect.con.prepareStatement(query);
                    stmt.setString(1, rollNo);
                    
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        marks[subject][tableIndex] = rs.getInt(1);
                    } else {
                        marks[subject][tableIndex] = 0;
                    }
                    
                    rs.close();
                    stmt.close();
                }
            }

            // Calculate final marks and grades
            int[] finalMarks = new int[5];
            String[] grades = new String[5];

            for (int i = 0; i < 5; i++) {
                // Average of Mid1 and Mid2, plus Internal and Semester
                finalMarks[i] = (marks[i][0] + marks[i][1]) / 2 + marks[i][2] + marks[i][3];
                
                // Determine grade
                grades[i] = determineGrade(finalMarks[i]);
            }

            // Calculate total marks and overall grade
            int totalMarks = 0;
            for (int mark : finalMarks) {
                totalMarks += mark;
            }
            double percentage = (double)totalMarks / 5;
            String overallGrade = determineGrade(totalMarks / 5);

            // Update semester_grade table with total marks and grade
            String updateQuery = "UPDATE semester_grade SET total_marks = ?, grade = ? WHERE student_roll_no = ?";
            PreparedStatement updateStmt = DBConnect.con.prepareStatement(updateQuery);
            updateStmt.setInt(1, totalMarks);
            updateStmt.setString(2, overallGrade);
            updateStmt.setString(3, rollNo);
            updateStmt.executeUpdate();
            updateStmt.close();

            // Print Report Card
            System.out.println("\n=================== REPORT CARD ===================");
            System.out.println("Roll Number: " + rollNo);
            System.out.println("------------------------------------------------");
            System.out.println("Subject\t\tMarks\t\tGrade");
            System.out.println("------------------------------------------------");
            String[] subjectNames = {"Subject 1", "Subject 2", "Subject 3", "Subject 4", "Subject 5"};
            
            for (int i = 0; i < 5; i++) {
                System.out.printf("%-15s\t%d\t\t%s\n", 
                    subjectNames[i], finalMarks[i], grades[i]);
            }

            System.out.println("------------------------------------------------");
            System.out.println("Total Marks: " + totalMarks);
            System.out.printf("Percentage: %.2f%%\n", percentage);
            System.out.println("Overall Grade: " + overallGrade);
            System.out.println("=================================================");

        } catch (SQLException e) {
            System.out.println("Error generating report card: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String determineGrade(int marks) {
        if (marks >= 90) return "O";
        if (marks >= 80) return "A+";
        if (marks >= 70) return "A";
        if (marks >= 60) return "B+";
        if (marks >= 50) return "B";
        if (marks >= 40) return "C";
        return "F";
    }
}