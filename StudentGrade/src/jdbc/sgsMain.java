package jdbc;

import java.util.Scanner;

public class sgsMain {

    public static void main(String[] args) {
        try {
            // Establish initial database connection
            DBConnect.getConnect();
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        StudentUpdater updater = new StudentUpdater();
        MarksManager marksManager = new MarksManager();
        MarksUpdater marksUpdater = new MarksUpdater();
        ReportCardGenerator reportCardGenerator = new ReportCardGenerator();
        StudentGradeDisplay gradeDisplay = new StudentGradeDisplay();

        while (true) {
            System.out.println("\n===== Student Grade System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int userType = safeReadInt(scanner);

            if (userType == 1) { 
                // Admin Login Section
                System.out.print("Enter Admin Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Admin Password: ");
                String password = scanner.nextLine();

                // Validate admin credentials (you might want to implement a proper authentication method)
                if (!username.equals("admin") || !password.equals("admin123")) {
                    System.out.println("Invalid admin credentials!");
                    continue;
                }

                while (true) {
                    System.out.println("\n===== Admin Menu =====");
                    System.out.println("1. Add Student");
                    System.out.println("2. Update Student");
                    System.out.println("3. Delete Student");
                    System.out.println("4. Insert Marks");
                    System.out.println("5. Update Marks");
                    System.out.println("6. Generate Report Card");
                    System.out.println("7. Logout");
                    System.out.print("Enter your choice: ");
                    
                    int adminChoice = safeReadInt(scanner);

                    if (adminChoice == 1) {
                        // Add Student
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student roll number: ");
                        String rollNo = scanner.nextLine();
                        System.out.print("Enter student email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter student phone number: ");
                        String phoneNo = scanner.nextLine();
                        try {
                            manager.addStudent(name, rollNo, email, phoneNo);
                            System.out.println("Student added successfully!");
                        } catch (Exception e) {
                            System.out.println("Error adding student: " + e.getMessage());
                        }
                    } else if (adminChoice == 2) {
                        // Update Student
                        System.out.print("Enter student roll number to update: ");
                        String rollNo = scanner.nextLine();
                        try {
                            updater.updateStudent(rollNo, scanner); 
                        } catch (Exception e) {
                            System.out.println("Error updating student: " + e.getMessage());
                        }
                    } else if (adminChoice == 3) {
                        // Delete Student
                        System.out.print("Enter student roll number to delete: ");
                        String rollNo = scanner.nextLine();
                        try {
                            updater.deleteStudent(rollNo);
                            System.out.println("Student deleted successfully!");
                        } catch (Exception e) {
                            System.out.println("Error deleting student: " + e.getMessage());
                        }
                    } else if (adminChoice == 4) {
                        // Insert Marks
                        System.out.println("\nInsert Marks:");
                        try {
                            marksManager.insertMarks();
                        } catch (Exception e) {
                            System.out.println("Error inserting marks: " + e.getMessage());
                        }
                    } else if (adminChoice == 5) {
                        // Update Marks
                        System.out.println("\nUpdate Marks:");
                        try {
                            marksUpdater.updateMarks();
                        } catch (Exception e) {
                            System.out.println("Error updating marks: " + e.getMessage());
                        }
                    } else if (adminChoice == 6) {
                        // Generate Report Card
                        System.out.print("Enter student roll number to generate report card: ");
                        String rollNo = scanner.nextLine();
                        try {
                            reportCardGenerator.generateReportCard(rollNo);
                        } catch (Exception e) {
                            System.out.println("Error generating report card: " + e.getMessage());
                        }
                    } else if (adminChoice == 7) {
                        System.out.println("Admin logged out.");
                        break;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else if (userType == 2) {
                // Student Login Section
                System.out.print("Enter your roll number: ");
                String studentRollNo = scanner.nextLine();
                
                while (true) {
                    System.out.println("\n===== Student Menu =====");
                    System.out.println("1. View Grade");
                    System.out.println("2. Logout");
                    System.out.print("Enter your choice: ");
                    
                    int studentChoice = safeReadInt(scanner);
                    
                    if (studentChoice == 1) {
                        // Display student grade
                        gradeDisplay.displayStudentGrade(studentRollNo);
                    } else if (studentChoice == 2) {
                        System.out.println("Student logged out.");
                        break;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else if (userType == 3) {
                System.out.println("Exiting the Student Grade System. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Safe integer reading method to handle input errors
    private static int safeReadInt(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}