package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentGradeDisplay {
    
    // Inner class to represent subject marks and grade
    private static class SubjectGrade {
        String subjectName;
        int mid1Marks;
        int mid2Marks;
        int internalMarks;
        int semesterMarks;
        int totalMarks;
        String grade;
        
        SubjectGrade(String subjectName, int mid1, int mid2, int internal, int semester) {
            this.subjectName = subjectName;
            this.mid1Marks = mid1;
            this.mid2Marks = mid2;
            this.internalMarks = internal;
            this.semesterMarks = semester;
            // Calculate total marks: average of mid1 and mid2, plus internal and semester
            this.totalMarks = (mid1 + mid2) / 2 + internal + semester;
            this.grade = determineGrade(this.totalMarks);
        }
    }

    // Method to verify if student exists
    private boolean isValidStudent(String rollNo) throws Exception {
        try {
            DBConnect.getConnect();
            Connection con = DBConnect.con;
            
            String query = "SELECT COUNT(*) FROM students WHERE student_roll_no = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, rollNo);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error verifying student: " + e.getMessage());
            throw e;
        }
        return false;
    }

    // Method to retrieve all component marks and calculate grades
    public List<SubjectGrade> getSubjectGrades(String rollNo) throws Exception {
        List<SubjectGrade> subjectGrades = new ArrayList<>();
        
        if (!isValidStudent(rollNo)) {
            System.out.println("Invalid student roll number.");
            return subjectGrades;
        }
        
        try {
            DBConnect.getConnect();
            Connection con = DBConnect.con;
            
            String[] tables = {"mid1", "mid2", "internal", "semester_grade"};
            int[][] marks = new int[5][tables.length];
            
            // Fetch marks for each subject from each table
            for (int subject = 0; subject < 5; subject++) {
                for (int tableIndex = 0; tableIndex < tables.length; tableIndex++) {
                    String query = "SELECT subject" + (subject + 1) + 
                                 " FROM " + tables[tableIndex] + 
                                 " WHERE student_roll_no = ?";
                    
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        stmt.setString(1, rollNo);
                        
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                marks[subject][tableIndex] = rs.getInt(1);
                            }
                        }
                    }
                }
            }
            
            // Create SubjectGrade objects with all component marks
            String[] subjectNames = {
                "Subject 1", "Subject 2", "Subject 3", 
                "Subject 4", "Subject 5"
            };
            
            for (int i = 0; i < 5; i++) {
                subjectGrades.add(new SubjectGrade(
                    subjectNames[i],
                    marks[i][0],  // mid1
                    marks[i][1],  // mid2
                    marks[i][2],  // internal
                    marks[i][3]   // semester
                ));
            }
            
        } catch (Exception e) {
            System.out.println("Error retrieving subject grades: " + e.getMessage());
            throw e;
        }
        
        return subjectGrades;
    }

    // Method to display student grades
    public void displayStudentGrade(String rollNo) {
        try {
            String studentName = getStudentName(rollNo);
            List<SubjectGrade> subjectGrades = getSubjectGrades(rollNo);
            
            if (subjectGrades.isEmpty()) {
                System.out.println("No grades found for student with Roll No: " + rollNo);
                return;
            }
            
            // Print simplified report
            System.out.println("\n========================== Student Grade Report ==========================");
            System.out.println("Student Name: " + studentName);
            System.out.println("Roll Number: " + rollNo);
            System.out.println("------------------------------------------------------------------------");
            System.out.printf("%-15s %-8s %-8s %-10s %-8s%n", 
                "Subject", "Mid1", "Mid2", "Internal", "Grade");
            System.out.println("------------------------------------------------------------------------");
            
            for (SubjectGrade sg : subjectGrades) {
                System.out.printf("%-15s %-8d %-8d %-10d %-8s%n",
                    sg.subjectName,
                    sg.mid1Marks,
                    sg.mid2Marks,
                    sg.internalMarks,
                    sg.grade);
            }
            
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Overall Grade: " + determineGrade(calculateOverallGrade(subjectGrades)));
            System.out.println("========================================================================");
            
        } catch (Exception e) {
            System.out.println("Error in displaying student grade: " + e.getMessage());
        }
    }

    // Method to calculate overall grade
    private int calculateOverallGrade(List<SubjectGrade> grades) {
        int sum = 0;
        for (SubjectGrade sg : grades) {
            sum += sg.totalMarks;
        }
        return sum / grades.size();
    }

    // Method to retrieve student name
    private String getStudentName(String rollNo) throws Exception {
        try {
            DBConnect.getConnect();
            Connection con = DBConnect.con;
            
            String query = "SELECT student_name FROM students WHERE student_roll_no = ?";
            
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, rollNo);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("student_name");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving student name: " + e.getMessage());
        }
        
        return "Unknown";
    }

    // Method to determine grade based on marks
    private static String determineGrade(int marks) {
        if (marks >= 90) return "O";
        if (marks >= 80) return "A+";
        if (marks >= 70) return "A";
        if (marks >= 60) return "B+";
        if (marks >= 50) return "B";
        if (marks >= 40) return "C";
        return "F";
    }
}