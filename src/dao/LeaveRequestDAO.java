
package dao;

import db.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class LeaveRequestDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void applyLeave() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter your SSN: ");
            String ssn = scanner.nextLine().trim();

            System.out.print("Enter Type (Leave/WorkFromHome): ");
            String type = scanner.nextLine().trim();

            if (!type.equalsIgnoreCase("Leave") && !type.equalsIgnoreCase("WorkFromHome")) {
                System.out.println("Invalid type. Must be 'Leave' or 'WorkFromHome'.");
                return;
            }

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());

            if (end.isBefore(start)) {
                System.out.println("End date cannot be before start date.");
                return;
            }

            System.out.print("Enter Reason: ");
            String reason = scanner.nextLine().trim();

            String query = "INSERT INTO leave_requests (ssn, type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, ssn);
            pstmt.setString(2, type);
            pstmt.setDate(3, Date.valueOf(start));
            pstmt.setDate(4, Date.valueOf(end));
            pstmt.setString(5, reason);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Leave request submitted successfully.");
            } else {
                System.out.println("Failed to submit leave request.");
            }
        } catch (Exception e) {
            System.out.println("Error during leave application.");
            e.printStackTrace();
        }
    }

    public void viewLeavesByEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String ssn = scanner.nextLine();

            String query = "SELECT * FROM leave_requests WHERE ssn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Request ID: " + rs.getInt("request_id"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("From: " + rs.getDate("start_date") + " To: " + rs.getDate("end_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Reason: " + rs.getString("reason"));
                System.out.println("Applied On: " + rs.getDate("applied_on"));
                System.out.println("------------------------");
            }

            if (!found) {
                System.out.println("No leave requests found for this employee.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching leave records.");
            e.printStackTrace();
        }
    }

    public void viewAllRequests() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM leave_requests";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Request ID: " + rs.getInt("request_id"));
                System.out.println("SSN: " + rs.getString("ssn"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("From: " + rs.getDate("start_date") + " To: " + rs.getDate("end_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Reason: " + rs.getString("reason"));
                System.out.println("Applied On: " + rs.getDate("applied_on"));
                System.out.println("------------------------");
            }

            if (!found) {
                System.out.println("No leave requests found.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching all leave requests.");
            e.printStackTrace();
        }
    }

    public void updateRequestStatus() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Request ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter new Status (Approved/Rejected): ");
            String status = scanner.nextLine().trim();

            if (!status.equalsIgnoreCase("Approved") && !status.equalsIgnoreCase("Rejected")) {
                System.out.println("Invalid status. Must be 'Approved' or 'Rejected'.");
                return;
            }

            // Get leave details to insert into attendance if approved
            String fetchQuery = "SELECT ssn, start_date, end_date, type FROM leave_requests WHERE request_id = ?";
            PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery);
            fetchStmt.setInt(1, id);
            ResultSet rs = fetchStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No such request found.");
                return;
            }

            String ssn = rs.getString("ssn");
            LocalDate start = rs.getDate("start_date").toLocalDate();
            LocalDate end = rs.getDate("end_date").toLocalDate();
            String type = rs.getString("type");

            // Update status
            String updateQuery = "UPDATE leave_requests SET status = ? WHERE request_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Status updated successfully.");

                if (status.equalsIgnoreCase("Approved")) {
                    String insertAttendance = "INSERT INTO attendance (ssn, date, status) VALUES (?, ?, ?)";
                    PreparedStatement attStmt = conn.prepareStatement(insertAttendance);
                    LocalDate date = start;
                    while (!date.isAfter(end)) {
                        attStmt.setString(1, ssn);
                        attStmt.setDate(2, Date.valueOf(date));
                        attStmt.setString(3, type);
                        attStmt.executeUpdate();
                        date = date.plusDays(1);
                    }
                    System.out.println("Attendance auto-marked as " + type + " for approved period.");
                }
            } else {
                System.out.println("Failed to update request.");
            }
        } catch (Exception e) {
            System.out.println("Error updating leave request status.");
            e.printStackTrace();
        }
    }

    // Advanced Filters
    public void filterPendingRequests() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM leave_requests WHERE status = 'Pending'";
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                System.out.println("Request ID: " + rs.getInt("request_id") + ", SSN: " + rs.getString("ssn") +
                        ", Type: " + rs.getString("type") + ", From: " + rs.getDate("start_date") +
                        " To: " + rs.getDate("end_date") + ", Reason: " + rs.getString("reason"));
                System.out.println("Applied On: " + rs.getDate("applied_on"));
                System.out.println("-------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error filtering pending requests.");
            e.printStackTrace();
        }
    }

    public void filterLeavesInMonth() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Year (YYYY): ");
            int year = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM leave_requests WHERE MONTH(start_date) = ? AND YEAR(start_date) = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Request ID: " + rs.getInt("request_id") + ", SSN: " + rs.getString("ssn") +
                        ", Type: " + rs.getString("type") + ", Status: " + rs.getString("status") +
                        ", From: " + rs.getDate("start_date") + " To: " + rs.getDate("end_date"));
                System.out.println("Applied On: " + rs.getDate("applied_on"));
                System.out.println("-------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error filtering leaves by month.");
            e.printStackTrace();
        }
    }
}
