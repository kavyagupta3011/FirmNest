package dao;

import db.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class EmployeeTrainingDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void enrollEmployeeInTraining() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            // Check if employee exists
            String empCheck = "SELECT ssn FROM employee WHERE ssn = ?";
            try (PreparedStatement empStmt = conn.prepareStatement(empCheck)) {
                empStmt.setString(1, ssn);
                ResultSet empRs = empStmt.executeQuery();
                if (!empRs.next()) {
                    System.out.println("Employee with SSN " + ssn + " does not exist.");
                    return;
                }
            }

            System.out.print("Enter Training Program ID: ");
            int programId = Integer.parseInt(scanner.nextLine());

            // Check if training program exists
            String programCheck = "SELECT program_id FROM training_program WHERE program_id = ?";
            try (PreparedStatement progStmt = conn.prepareStatement(programCheck)) {
                progStmt.setInt(1, programId);
                ResultSet progRs = progStmt.executeQuery();
                if (!progRs.next()) {
                    System.out.println("Training program with ID " + programId + " does not exist.");
                    return;
                }
            }

            System.out.print("Enter Enrollment Status (e.g., Enrolled/Completed): ");
            String status = scanner.nextLine();

            String insertQuery = "INSERT INTO employee_training (ssn, program_id, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, ssn);
                pstmt.setInt(2, programId);
                pstmt.setString(3, status);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Employee enrolled in training successfully.");
                } else {
                    System.out.println("Failed to enroll employee.");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("This employee is already enrolled in this training.");
        } catch (Exception e) {
            System.out.println("Error enrolling employee.");
            e.printStackTrace();
        }
    }

    public void getTrainingEnrollmentsByEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            String query = "SELECT et.program_id, tp.title, et.status FROM employee_training et JOIN training_program tp ON et.program_id = tp.program_id WHERE et.ssn = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, ssn);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Program ID: " + rs.getInt("program_id"));
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("--------------------------");
                }

                if (!found) {
                    System.out.println("No trainings found for employee with SSN: " + ssn);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching trainings.");
            e.printStackTrace();
        }
    }

    public void getEmployeesInTraining() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Training Program ID: ");
            int programId = Integer.parseInt(scanner.nextLine());

            String query = "SELECT et.ssn, e.fname, e.lname, et.status FROM employee_training et JOIN employee e ON et.ssn = e.ssn WHERE et.program_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, programId);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Employee SSN: " + rs.getString("ssn"));
                    System.out.println("Name: " + rs.getString("fname") + " " + rs.getString("lname"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("--------------------------");
                }

                if (!found) {
                    System.out.println("No employees found enrolled in training ID: " + programId);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching employees.");
            e.printStackTrace();
        }
    }

    public void removeEmployeeFromTraining() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            System.out.print("Enter Training Program ID: ");
            int programId = Integer.parseInt(scanner.nextLine());

            String deleteQuery = "DELETE FROM employee_training WHERE ssn = ? AND program_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setString(1, ssn);
                pstmt.setInt(2, programId);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Employee removed from training.");
                } else {
                    System.out.println("No such enrollment found.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error removing employee from training.");
            e.printStackTrace();
        }
    }
}
