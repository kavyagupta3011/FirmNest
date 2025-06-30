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


    public void markTrainingAsCompleted() {
    try (Connection conn = DBConnection.getConnection()) {
        System.out.print("Enter Employee SSN: ");
        String ssn = scanner.nextLine().trim();

        System.out.print("Enter Program ID: ");
        int programId = Integer.parseInt(scanner.nextLine());

        // Step 1: Check if employee is enrolled
        String checkQuery = "SELECT status FROM employee_training WHERE ssn = ? AND program_id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, ssn);
            checkStmt.setInt(2, programId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No enrollment found for this employee in the given program.");
                return;
            }

            String currentStatus = rs.getString("status");

            // Step 2: Validate current status
            if ("Completed".equalsIgnoreCase(currentStatus)) {
                System.out.println("Training is already marked as Completed.");
                return;
            }

            if (!"Enrolled".equalsIgnoreCase(currentStatus)) {
                System.out.println("Training is not currently in 'Enrolled' status. Found status: " + currentStatus);
                return;
            }

            // Step 3: Update to Completed
            String updateQuery = "UPDATE employee_training SET status = 'Completed' WHERE ssn = ? AND program_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, ssn);
                updateStmt.setInt(2, programId);
                int updated = updateStmt.executeUpdate();
                if (updated > 0) {
                    System.out.println("Training marked as Completed successfully.");
                } else {
                    System.out.println("Failed to mark as Completed.");
                }
            }
        }

        } catch (Exception e) {
            System.out.println("Error marking training as completed.");
            e.printStackTrace();
        }
    }
        // ---------------------- FILTERS ------------------------

    

    private void filterByStatus() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Enrollment Status (Enrolled/Completed etc.): ");
            String status = scanner.nextLine();

            String query = "SELECT et.ssn, e.fname, e.lname, tp.title, et.status " +
                           "FROM employee_training et " +
                           "JOIN employee e ON et.ssn = e.ssn " +
                           "JOIN training_program tp ON et.program_id = tp.program_id " +
                           "WHERE LOWER(et.status) = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, status.toLowerCase());
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    printEnrollment(rs);
                }

                if (!found) {
                    System.out.println("No records found with status: " + status);
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by status.");
            e.printStackTrace();
        }
    }

    private void filterByTitleKeyword() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter keyword in training title: ");
            String keyword = scanner.nextLine();

            String query = "SELECT et.ssn, e.fname, e.lname, tp.title, et.status " +
                           "FROM employee_training et " +
                           "JOIN employee e ON et.ssn = e.ssn " +
                           "JOIN training_program tp ON et.program_id = tp.program_id " +
                           "WHERE LOWER(tp.title) LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    printEnrollment(rs);
                }

                if (!found) {
                    System.out.println("No trainings found with keyword: " + keyword);
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by title keyword.");
            e.printStackTrace();
        }
    }

    private void filterByDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Start Date From (YYYY-MM-DD): ");
            Date start = Date.valueOf(scanner.nextLine());

            System.out.print("Enter End Date To (YYYY-MM-DD): ");
            Date end = Date.valueOf(scanner.nextLine());

            String query = "SELECT et.ssn, e.fname, e.lname, tp.title, tp.start_date, tp.end_date, et.status " +
                           "FROM employee_training et " +
                           "JOIN employee e ON et.ssn = e.ssn " +
                           "JOIN training_program tp ON et.program_id = tp.program_id " +
                           "WHERE tp.start_date >= ? AND tp.end_date <= ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, start);
                pstmt.setDate(2, end);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Employee: " + rs.getString("fname") + " " + rs.getString("lname") +
                            " (SSN: " + rs.getString("ssn") + ")");
                    System.out.println("Training Title: " + rs.getString("title"));
                    System.out.println("Start Date   : " + rs.getDate("start_date"));
                    System.out.println("End Date     : " + rs.getDate("end_date"));
                    System.out.println("Status       : " + rs.getString("status"));
                    System.out.println("----------------------------");
                }

                if (!found) {
                    System.out.println("No training enrollments in the given date range.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by date range.");
            e.printStackTrace();
        }
    }

    private void printEnrollment(ResultSet rs) throws SQLException {
        System.out.println("Employee: " + rs.getString("fname") + " " + rs.getString("lname") +
                " (SSN: " + rs.getString("ssn") + ")");
        System.out.println("Training Title: " + rs.getString("title"));
        System.out.println("Status: " + rs.getString("status"));
        System.out.println("----------------------------");
    }


    public void trainingEnrollmentFilterMenu() {
        System.out.println("\n--- Training Enrollment Filter Menu ---");
        System.out.println("1. Filter by Enrollment Status");
        System.out.println("2. Filter by Program Title Keyword");
        System.out.println("3. Filter Enrollments by Training Date Range");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByStatus();
            case 2 -> filterByTitleKeyword();
            case 3 -> filterByDateRange();
            case 0 -> System.out.println("Exiting filter menu.");
            default -> System.out.println("Invalid choice.");
        }
    }
}
