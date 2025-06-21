package dao;

import db.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class AssetMaintenanceDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void recordMaintenance() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Asset ID: ");
            int assetId = Integer.parseInt(scanner.nextLine());

            // Check if asset exists
            String checkAsset = "SELECT asset_id FROM assets WHERE asset_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkAsset)) {
                checkStmt.setInt(1, assetId);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Asset with ID " + assetId + " does not exist.");
                    return;
                }
            }

            System.out.print("Enter Maintenance Date (YYYY-MM-DD): ");
            Date maintenanceDate = Date.valueOf(scanner.nextLine());

            System.out.print("Enter Maintenance Details: ");
            String details = scanner.nextLine();

            String insert = "INSERT INTO asset_maintenance (asset_id, maintenance_date, details) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insert)) {
                pstmt.setInt(1, assetId);
                pstmt.setDate(2, maintenanceDate);
                pstmt.setString(3, details);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Maintenance record added successfully.");
                } else {
                    System.out.println("Failed to add maintenance record.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error recording maintenance.");
            e.printStackTrace();
        }
    }

    public void getMaintenanceByAsset() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Asset ID: ");
            int assetId = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM asset_maintenance WHERE asset_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, assetId);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Maintenance ID: " + rs.getInt("maintenance_id"));
                    System.out.println("Date: " + rs.getDate("maintenance_date"));
                    System.out.println("Details: " + rs.getString("details"));
                    System.out.println("-----------------------------");
                }

                if (!found) {
                    System.out.println("No maintenance records found for Asset ID " + assetId);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching maintenance records.");
            e.printStackTrace();
        }
    }

    public void getAllMaintenanceLogs() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM asset_maintenance ORDER BY maintenance_date DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Maintenance ID: " + rs.getInt("maintenance_id"));
                    System.out.println("Asset ID: " + rs.getInt("asset_id"));
                    System.out.println("Date: " + rs.getDate("maintenance_date"));
                    System.out.println("Details: " + rs.getString("details"));
                    System.out.println("-----------------------------");
                }

                if (!found) {
                    System.out.println("No maintenance logs available.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching maintenance logs.");
            e.printStackTrace();
        }
    }

        // ---------------------- FILTERS ------------------------

    

    private void filterByAssetId() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Asset ID: ");
            int assetId = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM asset_maintenance WHERE asset_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, assetId);
                ResultSet rs = pstmt.executeQuery();
                displayMaintenanceResults(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by asset ID.");
            e.printStackTrace();
        }
    }

    private void filterByDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            Date start = Date.valueOf(scanner.nextLine());

            System.out.print("Enter end date (YYYY-MM-DD): ");
            Date end = Date.valueOf(scanner.nextLine());

            String query = "SELECT * FROM asset_maintenance WHERE maintenance_date BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, start);
                pstmt.setDate(2, end);
                ResultSet rs = pstmt.executeQuery();
                displayMaintenanceResults(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by date range.");
            e.printStackTrace();
        }
    }

    private void filterByDetailKeyword() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter keyword to search in maintenance details: ");
            String keyword = scanner.nextLine();

            String query = "SELECT * FROM asset_maintenance WHERE details LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, "%" + keyword + "%");
                ResultSet rs = pstmt.executeQuery();
                displayMaintenanceResults(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by keyword.");
            e.printStackTrace();
        }
    }

    private void displayMaintenanceResults(ResultSet rs) throws SQLException {
        boolean found = false;
        System.out.println("\n--- Filtered Maintenance Records ---");
        while (rs.next()) {
            found = true;
            System.out.println("Maintenance ID: " + rs.getInt("maintenance_id"));
            System.out.println("Asset ID: " + rs.getInt("asset_id"));
            System.out.println("Date: " + rs.getDate("maintenance_date"));
            System.out.println("Details: " + rs.getString("details"));
            System.out.println("-----------------------------");
        }
        if (!found) {
            System.out.println("No matching maintenance records found.");
        }
    }

    public void maintenanceFilterMenu() {
        System.out.println("\n--- Asset Maintenance Filter Menu ---");
        System.out.println("1. Filter by Asset ID");
        System.out.println("2. Filter by Maintenance Date Range");
        System.out.println("3. Filter by Keyword in Details");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByAssetId();
            case 2 -> filterByDateRange();
            case 3 -> filterByDetailKeyword();
            case 0 -> System.out.println("Exiting maintenance filter menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

}

