package jdbc;

import java.util.Scanner;

public class sgsMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Welcome to the Student Grade System");
            System.out.println("Are you a Student or an Admin? (Enter '1' for Student, '2' for Admin): ");
            int userType = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (userType == 1) {
                // Student
                System.out.println("You are logged in as a Student.");
                System.out.println("Student features are coming soon!");
            } else if (userType == 2) {
                // Admin
                System.out.println("You are logged in as an Admin.");
                System.out.println("Admin Options:");
                System.out.println("1. Add Student");
                System.out.println("Enter your choice:");
                int adminChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (adminChoice == 1) {
                    StudentManager studentManager = new StudentManager();
                    System.out.println("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter Student Roll Number: ");
                    String rollNo = scanner.nextLine();
                    System.out.println("Enter Student Email: ");
                    String email = scanner.nextLine();
                    System.out.println("Enter Student Phone Number: ");
                    String phone = scanner.nextLine();

                    // Call the addStudent method from StudentManager
                    studentManager.addStudent(name, rollNo, email, phone);
                } else {
                    System.out.println("Invalid choice. Returning to the main menu.");
                }
            } else {
                System.out.println("Invalid input. Please restart the program and enter '1' or '2'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
