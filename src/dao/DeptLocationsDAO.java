package dao;

import db.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class DeptLocationsDAO {

    private final Scanner scanner = new Scanner(System.in);

    public void addLocation() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number: ");
            int dnumber = Integer.parseInt(scanner.nextLine());

            // Check if department exists
            String checkDeptQuery = "SELECT dnumber FROM department WHERE dnumber = ?";
            try (PreparedStatement deptStmt = conn.prepareStatement(checkDeptQuery)) {
                deptStmt.setInt(1, dnumber);
                ResultSet rs = deptStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Department number " + dnumber + " does not exist.");
                    return;
                }
            }

            System.out.print("Enter Location: ");
            String dlocation = scanner.nextLine();

            // Check if this combination already exists
            String checkComboQuery = "SELECT * FROM dept_locations WHERE dnumber = ? AND dlocation = ?";
            try (PreparedStatement comboStmt = conn.prepareStatement(checkComboQuery)) {
                comboStmt.setInt(1, dnumber);
                comboStmt.setString(2, dlocation);
                ResultSet rs = comboStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("This department-location combination already exists.");
                    return;
                }
            }

            String insertQuery = "INSERT INTO dept_locations (dnumber, dlocation) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1, dnumber);
                pstmt.setString(2, dlocation);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Department location added successfully.");
                } else {
                    System.out.println("Failed to add department location.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error adding location.");
            e.printStackTrace();
        }
    }

    public void getLocationsByDeptId() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number: ");
            int dnumber = Integer.parseInt(scanner.nextLine());

            String query = "SELECT dlocation FROM dept_locations WHERE dnumber = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, dnumber);
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Locations for Department " + dnumber + ":");
                boolean found = false;
                while (rs.next()) {
                    System.out.println("- " + rs.getString("dlocation"));
                    found = true;
                }
                if (!found) {
                    System.out.println("No locations found for this department.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching locations.");
            e.printStackTrace();
        }
    }

    public void deleteLocation() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number: ");
            int dnumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Location to Delete: ");
            String dlocation = scanner.nextLine();

            String deleteQuery = "DELETE FROM dept_locations WHERE dnumber = ? AND dlocation = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, dnumber);
                pstmt.setString(2, dlocation);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Location deleted successfully.");
                } else {
                    System.out.println("No such location found for the given department.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error deleting location.");
            e.printStackTrace();
        }
    }

    public void getAllLocations() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM dept_locations ORDER BY dnumber, dlocation";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("All Department Locations:");
                boolean found = false;
                while (rs.next()) {
                    int dnumber = rs.getInt("dnumber");
                    String dlocation = rs.getString("dlocation");
                    System.out.println("Department: " + dnumber + " | Location: " + dlocation);
                    found = true;
                }
                if (!found) {
                    System.out.println("No department locations found.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving department locations.");
            e.printStackTrace();
        }
    }
}

