package jdbc;

import java.util.Scanner;

public class sgsMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        StudentUpdater updater = new StudentUpdater();
        MarksManager marksManager = new MarksManager();

        while (true) {
            System.out.println("\nWelcome to Student Grade System!");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            // Read the main menu choice
            int userType = safeReadInt(scanner);

            if (userType == 1) { 
                while (true) {
                    System.out.println("\nAdmin Menu:");
                    System.out.println("1. Add Student");
                    System.out.println("2. Update Student");
                    System.out.println("3. Delete Student");
                    System.out.println("4. Insert Marks");
                    System.out.println("5. Logout");
                    System.out.print("Enter your choice: ");
                    
                    // Read the admin menu choice
                    int adminChoice = safeReadInt(scanner);

                    if (adminChoice == 1) {
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine(); // Read full name input
                        System.out.print("Enter student roll number: ");
                        String rollNo = scanner.nextLine();
                        System.out.print("Enter student email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter student phone number: ");
                        String phoneNo = scanner.nextLine();
                        try {
                            manager.addStudent(name, rollNo, email, phoneNo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (adminChoice == 2) {
                        System.out.print("Enter student roll number to update: ");
                        String rollNo = scanner.nextLine();
                        try {
                            // Pass both rollNo and scanner as arguments
                            updater.updateStudent(rollNo, scanner); 
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (adminChoice == 3) {
                        System.out.print("Enter student roll number to delete: ");
                        String rollNo = scanner.nextLine();
                        try {
                            updater.deleteStudent(rollNo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (adminChoice == 4) {
                        System.out.println("\nInsert Marks:");
                        try {
                            marksManager.insertMarks();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (adminChoice == 5) {
                        System.out.println("Admin logged out.");
                        break;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else if (userType == 2) {
                System.out.println("Student features will be implemented later.");
            } else if (userType == 3) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
    private static int safeReadInt(Scanner scanner) {
        while (true) {
            try {
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    
                   scanner.nextLine();
                    return input;
                } else {
                    scanner.nextLine();
                    System.out.print("Invalid input. Please enter a valid number: ");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Error: Please enter a valid number: ");
            }
        }
    }
}
