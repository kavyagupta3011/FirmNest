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

        // -------------------- ADVANCED FILTERS --------------------

    

    private void filterByStatusAndDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Status (Present/Absent/Leave): ");
            String status = scanner.nextLine();

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());

            String query = "SELECT * FROM attendance WHERE status = ? AND date BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, status);
                pstmt.setDate(2, Date.valueOf(start));
                pstmt.setDate(3, Date.valueOf(end));
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Attendance Records with Status '" + status + "' from " + start + " to " + end + ":");
                while (rs.next()) {
                    System.out.println("SSN: " + rs.getString("ssn") +
                                       ", Date: " + rs.getDate("date"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by status and date range.");
            e.printStackTrace();
        }
    }

    private void findExcessiveAbsentees() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Year (YYYY): ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Absence Threshold (number of Absent days): ");
            int threshold = Integer.parseInt(scanner.nextLine());

            String query = """
                    SELECT ssn, COUNT(*) as absents
                    FROM attendance
                    WHERE status = 'Absent' AND MONTH(date) = ? AND YEAR(date) = ?
                    GROUP BY ssn
                    HAVING absents >= ?
                    """;

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, month);
                pstmt.setInt(2, year);
                pstmt.setInt(3, threshold);

                ResultSet rs = pstmt.executeQuery();

                System.out.println("Employees with ≥ " + threshold + " absences in " + month + "/" + year + ":");
                while (rs.next()) {
                    System.out.println("SSN: " + rs.getString("ssn") +
                                       ", Absences: " + rs.getInt("absents"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching excessive absentees.");
            e.printStackTrace();
        }
    }

    private void departmentAttendanceOnDate() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number: ");
            int deptNo = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            String query = """
                    SELECT a.ssn, e.fname, e.lname, a.status
                    FROM attendance a
                    JOIN employee e ON a.ssn = e.ssn
                    WHERE e.dno = ? AND a.date = ?
                    """;

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, deptNo);
                pstmt.setDate(2, Date.valueOf(date));
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Attendance on " + date + " for Department " + deptNo + ":");
                while (rs.next()) {
                    System.out.println("Employee: " + rs.getString("fname") + " " + rs.getString("lname") +
                                       " (SSN: " + rs.getString("ssn") + "), Status: " + rs.getString("status"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching department attendance.");
            e.printStackTrace();
        }
    }

    public void attendanceFilterMenu() {
        System.out.println("\n--- Attendance Filter Menu ---");
        System.out.println("1. Filter by Status and Date Range");
        System.out.println("2. Find Employees with Excessive Absences");
        System.out.println("3. View Department Attendance on a Specific Date");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByStatusAndDateRange();
            case 2 -> findExcessiveAbsentees();
            case 3 -> departmentAttendanceOnDate();
            case 0 -> System.out.println("Exiting filter menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

}

