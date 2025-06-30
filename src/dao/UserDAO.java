package dao;

import db.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void registerUser() {
    try (Connection conn = DBConnection.getConnection()) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        // Check if username already exists
        String checkQuery = "SELECT username FROM users WHERE username = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Username already exists. Try another.");
                return;
            }
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Role (Admin/HR/CompanyEmployee): ");
        String inputRole = scanner.nextLine().trim();
        String role;

        if (inputRole.equalsIgnoreCase("Admin")) {
            role = "Admin";
        } else if (inputRole.equalsIgnoreCase("HR")) {
            role = "HR";
        } else if (inputRole.equalsIgnoreCase("CompanyEmployee")) {
            role = "CompanyEmployee";
        } else {
            System.out.println("Invalid role. Please enter Admin, HR, or CompanyEmployee.");
            return;
        }

        System.out.print("Enter SSN (must already exist in employee table): ");
        String ssn = scanner.nextLine().trim();

        if (ssn.isEmpty()) {
            System.out.println("SSN is required for all users.");
            return;
        }

        // Check if the SSN exists in the employee table
        String ssnCheckQuery = "SELECT ssn FROM employee WHERE ssn = ?";
        try (PreparedStatement ssnStmt = conn.prepareStatement(ssnCheckQuery)) {
            ssnStmt.setString(1, ssn);
            ResultSet ssnRs = ssnStmt.executeQuery();
            if (!ssnRs.next()) {
                System.out.println("No employee found with that SSN. Register the employee first.");
                return;
            }
        }

        // Insert user

        String query = "INSERT INTO users (username, password, role, ssn) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password); // (Optional: hash before storing in production)
            pstmt.setString(3, role);
            pstmt.setString(4, ssn);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("User registration failed.");
            }
        }

        } catch (Exception e) {
            System.out.println("Error during user registration.");
            e.printStackTrace();
        }
}


    public boolean validateLogin(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                return rs.next(); // login valid if user exists
            }
        } catch (Exception e) {
            System.out.println("Login failed due to error.");
            e.printStackTrace();
            return false;
        }
    }

    public String getUserRole(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT role FROM users WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving user role.");
            e.printStackTrace();
        }
        return null;
    }

    public String getUserSSN(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT ssn FROM users WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("ssn");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving user SSN.");
            e.printStackTrace();
        }
        return null;
    }
}

