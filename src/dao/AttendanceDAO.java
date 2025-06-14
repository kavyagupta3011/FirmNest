package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import db.DBConnection;

public class AttendanceDAO {
    Scanner scanner = new Scanner(System.in);

    public void markAttendance() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateStr);

            System.out.print("Enter Status (Present/Absent/Leave): ");
            String status = scanner.nextLine();

            // Check if employee exists
            String checkEmp = "SELECT * FROM employee WHERE ssn = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkEmp)) {
                checkStmt.setString(1, ssn);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Employee with SSN " + ssn + " does not exist.");
                    return;
                }
            }

            // Insert attendance
            String insert = "INSERT INTO attendance (ssn, date, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insert)) {
                pstmt.setString(1, ssn);
                pstmt.setDate(2, Date.valueOf(date));
                pstmt.setString(3, status);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Attendance marked successfully.");
                } else {
                    System.out.println("Failed to mark attendance.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error marking attendance.");
            e.printStackTrace();
        }
    }

    public void getAttendanceByEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            String query = "SELECT * FROM attendance WHERE ssn = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, ssn);
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Attendance for Employee SSN: " + ssn);
                while (rs.next()) {
                    System.out.println("Date: " + rs.getDate("date") +
                                       ", Status: " + rs.getString("status"));
                }
            }

        } catch (Exception e) {
            System.out.println("Error fetching attendance.");
            e.printStackTrace();
        }
    }

    public void getAttendanceByDate() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateStr);

            String query = "SELECT * FROM attendance WHERE date = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, Date.valueOf(date));
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Attendance on " + date);
                while (rs.next()) {
                    System.out.println("SSN: " + rs.getString("ssn") +
                                       ", Status: " + rs.getString("status"));
                }
            }

        } catch (Exception e) {
            System.out.println("Error fetching attendance by date.");
            e.printStackTrace();
        }
    }

    public void getMonthlyAttendanceReport() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            System.out.print("Enter Month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Year (YYYY): ");
            int year = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM attendance WHERE ssn = ? AND MONTH(date) = ? AND YEAR(date) = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, ssn);
                pstmt.setInt(2, month);
                pstmt.setInt(3, year);
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Monthly Attendance for " + ssn + " in " + month + "/" + year);
                while (rs.next()) {
                    System.out.println("Date: " + rs.getDate("date") +
                                       ", Status: " + rs.getString("status"));
                }
            }

        } catch (Exception e) {
            System.out.println("Error generating attendance report.");
            e.printStackTrace();
        }
    }
}

