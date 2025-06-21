package dao;

import java.sql.*;
import java.util.Scanner;
import db.DBConnection;

public class WorksOnDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void assignEmployeeToProject() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String essn = scanner.nextLine();

            // Check if employee exists
            PreparedStatement empCheck = conn.prepareStatement("SELECT ssn FROM employee WHERE ssn = ?");
            empCheck.setString(1, essn);
            ResultSet empRs = empCheck.executeQuery();
            if (!empRs.next()) {
                System.out.println("No such employee exists.");
                return;
            }
            System.out.print("Enter Project Number: ");
            int pno = Integer.parseInt(scanner.nextLine());

            // Check if project exists
            PreparedStatement projCheck = conn.prepareStatement("SELECT pnumber FROM project WHERE pnumber = ?");
            projCheck.setInt(1, pno);
            ResultSet projRs = projCheck.executeQuery();
            if (!projRs.next()) {
                System.out.println("No such project exists.");
                return;
            }

            // Check if already assigned
            PreparedStatement checkExist = conn.prepareStatement("SELECT * FROM works_on WHERE essn = ? AND pno = ?");
            checkExist.setString(1, essn);
            checkExist.setInt(2, pno);
            ResultSet existRs = checkExist.executeQuery();
            if (existRs.next()) {
                System.out.println("Employee is already assigned to this project.");
                return;
            }
            System.out.print("Enter Hours Worked: ");
            double hours = Double.parseDouble(scanner.nextLine());

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO works_on (essn, pno, hours) VALUES (?, ?, ?)");
            pstmt.setString(1, essn);
            pstmt.setInt(2, pno);
            pstmt.setDouble(3, hours);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee assigned to project successfully.");
            } else {
                System.out.println("Failed to assign.");
            }
        } catch (Exception e) {
            System.out.println("Error assigning employee to project.");
            e.printStackTrace();
        }
    }

    public void getProjectsByEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String essn = scanner.nextLine();

            String query = "SELECT w.pno, p.pname, w.hours FROM works_on w JOIN project p ON w.pno = p.pnumber WHERE w.essn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, essn);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Project Number: " + rs.getInt("pno"));
                System.out.println("Project Name: " + rs.getString("pname"));
                System.out.println("Hours Worked: " + rs.getDouble("hours"));
                System.out.println("-------------------");
            }
            if (!found) {
                System.out.println("No projects found for this employee.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving projects.");
            e.printStackTrace();
        }
    }

    public void getEmployeesByProject() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Project Number: ");
            int pno = Integer.parseInt(scanner.nextLine());

            String query = "SELECT w.essn, e.fname, e.lname, w.hours FROM works_on w JOIN employee e ON w.essn = e.ssn WHERE w.pno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pno);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Employee SSN: " + rs.getString("essn"));
                System.out.println("Name: " + rs.getString("fname") + " " + rs.getString("lname"));
                System.out.println("Hours Worked: " + rs.getDouble("hours"));
                System.out.println("-------------------");
            }
            if (!found) {
                System.out.println("No employees found for this project.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving employees.");
            e.printStackTrace();
        }
    }

    public void removeEmployeeFromProject() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String essn = scanner.nextLine();

            System.out.print("Enter Project Number: ");
            int pno = Integer.parseInt(scanner.nextLine());

            String query = "DELETE FROM works_on WHERE essn = ? AND pno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, essn);
            pstmt.setInt(2, pno);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Removed successfully.");
            } else {
                System.out.println("No such assignment found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing assignment.");
            e.printStackTrace();
        }
    }

    public void updateHoursForAssignment() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String essn = scanner.nextLine();

            System.out.print("Enter Project Number: ");
            int pno = Integer.parseInt(scanner.nextLine());

            String checkQuery = "SELECT * FROM works_on WHERE essn = ? AND pno = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, essn);
            checkStmt.setInt(2, pno);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No such assignment found.");
                return;
            }

            System.out.print("Enter new number of hours: ");
            double newHours = Double.parseDouble(scanner.nextLine());

            String updateQuery = "UPDATE works_on SET hours = ? WHERE essn = ? AND pno = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setDouble(1, newHours);
            updateStmt.setString(2, essn);
            updateStmt.setInt(3, pno);

            int rows = updateStmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Hours updated successfully.");
            } else {
                System.out.println("Update failed.");
            }
        } catch (Exception e) {
            System.out.println("Error updating hours.");
            e.printStackTrace();
        }
    }

    public void getAllAssignments() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT w.essn, e.fname, e.lname, w.pno, p.pname, w.hours FROM works_on w " +
                           "JOIN employee e ON w.essn = e.ssn " +
                           "JOIN project p ON w.pno = p.pnumber";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("=== All Work Assignments ===");
            while (rs.next()) {
                System.out.println("Employee: " + rs.getString("fname") + " " + rs.getString("lname") +
                                   " (SSN: " + rs.getString("essn") + ")");
                System.out.println("Project: " + rs.getString("pname") + " (PNo: " + rs.getInt("pno") + ")");
                System.out.println("Hours: " + rs.getDouble("hours"));
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error fetching all assignments.");
            e.printStackTrace();
        }
    }

        // ---------------------- FILTERS ------------------------

    

    private void filterByEmployeeSSN() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Employee SSN: ");
            String essn = scanner.nextLine();

            String query = "SELECT w.pno, p.pname, w.hours FROM works_on w JOIN project p ON w.pno = p.pnumber WHERE w.essn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, essn);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            System.out.println("\n--- Projects for Employee SSN: " + essn + " ---");
            while (rs.next()) {
                found = true;
                System.out.println("Project Number: " + rs.getInt("pno"));
                System.out.println("Project Name  : " + rs.getString("pname"));
                System.out.println("Hours Worked  : " + rs.getDouble("hours"));
                System.out.println("----------------------");
            }
            if (!found) {
                System.out.println("No assignments found.");
            }
        } catch (Exception e) {
            System.out.println("Error filtering by employee.");
            e.printStackTrace();
        }
    }

    private void filterByProjectNumber() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Project Number: ");
            int pno = Integer.parseInt(scanner.nextLine());

            String query = "SELECT w.essn, e.fname, e.lname, w.hours FROM works_on w JOIN employee e ON w.essn = e.ssn WHERE w.pno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pno);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            System.out.println("\n--- Employees for Project Number: " + pno + " ---");
            while (rs.next()) {
                found = true;
                System.out.println("Employee SSN : " + rs.getString("essn"));
                System.out.println("Name         : " + rs.getString("fname") + " " + rs.getString("lname"));
                System.out.println("Hours Worked : " + rs.getDouble("hours"));
                System.out.println("----------------------");
            }
            if (!found) {
                System.out.println("No assignments found.");
            }
        } catch (Exception e) {
            System.out.println("Error filtering by project.");
            e.printStackTrace();
        }
    }

    private void filterByMinimumHours() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter minimum hours: ");
            double minHours = Double.parseDouble(scanner.nextLine());

            String query = "SELECT w.essn, e.fname, e.lname, w.pno, p.pname, w.hours FROM works_on w " +
                           "JOIN employee e ON w.essn = e.ssn " +
                           "JOIN project p ON w.pno = p.pnumber " +
                           "WHERE w.hours >= ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, minHours);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            System.out.println("\n--- Assignments with at least " + minHours + " hours ---");
            while (rs.next()) {
                found = true;
                System.out.println("Employee : " + rs.getString("fname") + " " + rs.getString("lname") +
                                   " (SSN: " + rs.getString("essn") + ")");
                System.out.println("Project  : " + rs.getString("pname") + " (PNo: " + rs.getInt("pno") + ")");
                System.out.println("Hours    : " + rs.getDouble("hours"));
                System.out.println("----------------------");
            }
            if (!found) {
                System.out.println("No matching records found.");
            }
        } catch (Exception e) {
            System.out.println("Error filtering by minimum hours.");
            e.printStackTrace();
        }
    }

    private void filterByMaximumHours() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter maximum hours: ");
            double maxHours = Double.parseDouble(scanner.nextLine());

            String query = "SELECT w.essn, e.fname, e.lname, w.pno, p.pname, w.hours FROM works_on w " +
                           "JOIN employee e ON w.essn = e.ssn " +
                           "JOIN project p ON w.pno = p.pnumber " +
                           "WHERE w.hours <= ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, maxHours);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            System.out.println("\n--- Assignments with at most " + maxHours + " hours ---");
            while (rs.next()) {
                found = true;
                System.out.println("Employee : " + rs.getString("fname") + " " + rs.getString("lname") +
                                   " (SSN: " + rs.getString("essn") + ")");
                System.out.println("Project  : " + rs.getString("pname") + " (PNo: " + rs.getInt("pno") + ")");
                System.out.println("Hours    : " + rs.getDouble("hours"));
                System.out.println("----------------------");
            }
            if (!found) {
                System.out.println("No matching records found.");
            }
        } catch (Exception e) {
            System.out.println("Error filtering by maximum hours.");
            e.printStackTrace();
        }
    }

    public void worksOnFilterMenu() {
        System.out.println("\n--- Works_On Filter Menu ---");
        System.out.println("1. Filter by Employee SSN");
        System.out.println("2. Filter by Project Number");
        System.out.println("3. Filter by Minimum Hours Worked");
        System.out.println("4. Filter by Maximum Hours Worked");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByEmployeeSSN();
            case 2 -> filterByProjectNumber();
            case 3 -> filterByMinimumHours();
            case 4 -> filterByMaximumHours();
            case 0 -> System.out.println("Exiting filter menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

}
