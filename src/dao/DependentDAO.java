package dao;

import java.sql.*;
import java.util.Scanner;
import db.DBConnection;

public class DependentDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void addDependent() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String essn = scanner.nextLine();

            // Check if employee exists
            String empQuery = "SELECT ssn FROM employee WHERE ssn = ?";
            try (PreparedStatement empCheck = conn.prepareStatement(empQuery)) {
                empCheck.setString(1, essn);
                ResultSet empRs = empCheck.executeQuery();
                if (!empRs.next()) {
                    System.out.println("Employee with SSN " + essn + " does not exist.");
                    return;
                }
            }

            System.out.print("Enter Dependent Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Gender (M/F): ");
            String sex = scanner.nextLine();

            System.out.print("Enter Birth Date (YYYY-MM-DD): ");
            String bdate = scanner.nextLine();

            System.out.print("Enter Relationship: ");
            String relationship = scanner.nextLine();

            String insertQuery = "INSERT INTO dependent (essn, dependent_name, sex, bdate, relationship) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, essn);
                pstmt.setString(2, name);
                pstmt.setString(3, sex);
                pstmt.setDate(4, Date.valueOf(bdate));
                pstmt.setString(5, relationship);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Dependent added successfully.");
                } else {
                    System.out.println("Failed to add dependent.");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Dependent already exists for this employee.");
        } catch (Exception e) {
            System.out.println("Error adding dependent.");
            e.printStackTrace();
        }
    }

    public void getDependentsByEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN to view dependents: ");
            String essn = scanner.nextLine();

            String query = "SELECT * FROM dependent WHERE essn = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, essn);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Dependent Name: " + rs.getString("dependent_name"));
                    System.out.println("Gender: " + rs.getString("sex"));
                    System.out.println("Birth Date: " + rs.getDate("bdate"));
                    System.out.println("Relationship: " + rs.getString("relationship"));
                    System.out.println("----------------------------");
                }

                if (!found) {
                    System.out.println("No dependents found for employee with SSN: " + essn);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving dependents.");
            e.printStackTrace();
        }
    }

    public void deleteDependent() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String essn = scanner.nextLine();

            System.out.print("Enter Dependent Name to delete: ");
            String name = scanner.nextLine();

            String deleteQuery = "DELETE FROM dependent WHERE essn = ? AND dependent_name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setString(1, essn);
                pstmt.setString(2, name);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Dependent deleted successfully.");
                } else {
                    System.out.println("No such dependent found.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting dependent.");
            e.printStackTrace();
        }
    }

        // ------------------- FILTER FEATURES -------------------------

        private void filterByRelationship() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter relationship (e.g., Child, Spouse): ");
            String relationship = scanner.nextLine();

            String query = "SELECT * FROM dependent WHERE relationship LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, "%" + relationship + "%");
                ResultSet rs = pstmt.executeQuery();
                displayDependents(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by relationship.");
            e.printStackTrace();
        }
    }

    private void filterByGender() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter gender (M/F): ");
            String gender = scanner.nextLine().toUpperCase();

            String query = "SELECT * FROM dependent WHERE sex = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, gender);
                ResultSet rs = pstmt.executeQuery();
                displayDependents(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by gender.");
            e.printStackTrace();
        }
    }

    private void filterByBirthDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter start birth date (YYYY-MM-DD): ");
            String start = scanner.nextLine();
            System.out.print("Enter end birth date (YYYY-MM-DD): ");
            String end = scanner.nextLine();

            String query = "SELECT * FROM dependent WHERE bdate BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, Date.valueOf(start));
                pstmt.setDate(2, Date.valueOf(end));
                ResultSet rs = pstmt.executeQuery();
                displayDependents(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by birth date range.");
            e.printStackTrace();
        }
    }

    private void filterBySsnPrefix() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter starting digits of Employee SSN: ");
            String prefix = scanner.nextLine();

            String query = "SELECT * FROM dependent WHERE essn LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, prefix + "%");
                ResultSet rs = pstmt.executeQuery();
                displayDependents(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by SSN prefix.");
            e.printStackTrace();
        }
    }

    private void displayDependents(ResultSet rs) throws SQLException {
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.println("Employee SSN: " + rs.getString("essn"));
            System.out.println("Dependent Name: " + rs.getString("dependent_name"));
            System.out.println("Gender: " + rs.getString("sex"));
            System.out.println("Birth Date: " + rs.getDate("bdate"));
            System.out.println("Relationship: " + rs.getString("relationship"));
            System.out.println("----------------------------");
        }
        if (!found) {
            System.out.println("No matching dependents found.");
        }
    }
    public void dependentFilterMenu() {
        System.out.println("\n--- Dependent Filter Menu ---");
        System.out.println("1. Filter by Relationship");
        System.out.println("2. Filter by Gender");
        System.out.println("3. Filter by Birth Date Range");
        System.out.println("4. Filter by Employee SSN Prefix");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByRelationship();
            case 2 -> filterByGender();
            case 3 -> filterByBirthDateRange();
            case 4 -> filterBySsnPrefix();
            case 0 -> System.out.println("Exiting Dependent Filter Menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

    



}

