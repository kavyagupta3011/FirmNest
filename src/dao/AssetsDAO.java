package dao;

import db.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class AssetsDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void addAsset() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Asset Name: ");
            String assetName = scanner.nextLine();

            System.out.print("Enter Assigned Date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            Date assignedDate = Date.valueOf(dateInput);

            System.out.print("Enter Status (e.g., Available, In Use): ");
            String status = scanner.nextLine();

            String query = "INSERT INTO assets (asset_name, assigned_date, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, assetName);
                pstmt.setDate(2, assignedDate);
                pstmt.setString(3, status);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Asset added successfully.");
                } else {
                    System.out.println("Failed to add asset.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding asset.");
            e.printStackTrace();
        }
    }

    public void assignAssetToEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Asset ID to assign: ");
            int assetId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Employee SSN to assign to: ");
            String ssn = scanner.nextLine();

            // Check if employee exists
            String checkEmp = "SELECT ssn FROM employee WHERE ssn = ?";
            try (PreparedStatement empStmt = conn.prepareStatement(checkEmp)) {
                empStmt.setString(1, ssn);
                ResultSet rs = empStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Employee with SSN " + ssn + " does not exist.");
                    return;
                }
            }

            String update = "UPDATE assets SET assigned_to = ?, assigned_date = CURRENT_DATE, status = 'Assigned' WHERE asset_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(update)) {
                pstmt.setString(1, ssn);
                pstmt.setInt(2, assetId);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Asset assigned successfully.");
                } else {
                    System.out.println("Asset not found or update failed.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error assigning asset.");
            e.printStackTrace();
        }
    }

    public void getAssetsByEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            String query = "SELECT asset_id, asset_name, assigned_date, status FROM assets WHERE assigned_to = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, ssn);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Asset ID: " + rs.getInt("asset_id"));
                    System.out.println("Asset Name: " + rs.getString("asset_name"));
                    System.out.println("Assigned Date: " + rs.getDate("assigned_date"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("--------------------------");
                }

                if (!found) {
                    System.out.println("No assets assigned to this employee.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving assets.");
            e.printStackTrace();
        }
    }

    public void updateAssetStatus() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Asset ID to update: ");
            int assetId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter new status (e.g., Available, In Repair, Lost): ");
            String status = scanner.nextLine();

            String update = "UPDATE assets SET status = ? WHERE asset_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(update)) {
                pstmt.setString(1, status);
                pstmt.setInt(2, assetId);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Asset status updated successfully.");
                } else {
                    System.out.println("Asset not found or update failed.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error updating asset status.");
            e.printStackTrace();
        }
    }

    public void deleteAsset() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Asset ID to delete: ");
            int assetId = Integer.parseInt(scanner.nextLine());

            String deleteQuery = "DELETE FROM assets WHERE asset_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, assetId);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Asset deleted successfully.");
                } else {
                    System.out.println("Asset not found.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting asset.");
            e.printStackTrace();
        }
    }

        // ------------------- FILTER FEATURES -------------------------

    

    private void filterByStatus() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter status to filter (e.g., Available, Assigned): ");
            String status = scanner.nextLine();

            String query = "SELECT * FROM assets WHERE status = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, status);
                ResultSet rs = pstmt.executeQuery();
                displayAssetResults(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by status.");
            e.printStackTrace();
        }
    }

    private void filterByDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String start = scanner.nextLine();
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String end = scanner.nextLine();

            String query = "SELECT * FROM assets WHERE assigned_date BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, Date.valueOf(start));
                pstmt.setDate(2, Date.valueOf(end));
                ResultSet rs = pstmt.executeQuery();
                displayAssetResults(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by date range.");
            e.printStackTrace();
        }
    }

    private void filterByEmployeeSSN() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter full or partial SSN of employee: ");
            String ssn = scanner.nextLine();

            String query = "SELECT * FROM assets WHERE assigned_to LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, ssn + "%");
                ResultSet rs = pstmt.executeQuery();
                displayAssetResults(rs);
            }
        } catch (Exception e) {
            System.out.println("Error filtering by employee SSN.");
            e.printStackTrace();
        }
    }

    private void showUnassignedAssets() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM assets WHERE assigned_to IS NULL";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();
                displayAssetResults(rs);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving unassigned assets.");
            e.printStackTrace();
        }
    }

    private void displayAssetResults(ResultSet rs) throws SQLException {
        boolean found = false;
        System.out.println("\n--- Asset Results ---");
        while (rs.next()) {
            found = true;
            System.out.println("Asset ID: " + rs.getInt("asset_id"));
            System.out.println("Asset Name: " + rs.getString("asset_name"));
            System.out.println("Assigned To: " + rs.getString("assigned_to"));
            System.out.println("Assigned Date: " + rs.getDate("assigned_date"));
            System.out.println("Status: " + rs.getString("status"));
            System.out.println("----------------------------");
        }
        if (!found) {
            System.out.println("No matching assets found.");
        }
    }

    public void assetFilterMenu() {
        System.out.println("\n--- Asset Filter Menu ---");
        System.out.println("1. Filter by Status");
        System.out.println("2. Filter by Assignment Date Range");
        System.out.println("3. Filter by Assigned Employee SSN");
        System.out.println("4. Show Unassigned Assets");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByStatus();
            case 2 -> filterByDateRange();
            case 3 -> filterByEmployeeSSN();
            case 4 -> showUnassignedAssets();
            case 0 -> System.out.println("Exiting Asset Filter Menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

}

