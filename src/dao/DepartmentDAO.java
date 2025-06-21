package dao;

import model.Department;
import db.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class DepartmentDAO {

    Scanner scanner = new Scanner(System.in);

    private boolean isEmployeeExists(Connection conn, String ssn) throws SQLException{
        String query = "SELECT COUNT(*) FROM employee WHERE ssn = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void addDepartment(){
        try(Connection conn = DBConnection.getConnection()){
            System.out.print("Enter department number: ");
            int dnumber = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter department name: ");
            String dname = scanner.nextLine();
            System.out.print("Enter manager's SSN: ");
            String mgr_ssn = scanner.nextLine();

            // Validate mgr_ssn exists
            if (!isEmployeeExists(conn, mgr_ssn)) {
                System.out.println("No employee found with SSN: " + mgr_ssn);
                return;
            }
            
            System.out.print("Enter Manager Start Date (yyyy-mm-dd): ");
            Date mgr_start_date = Date.valueOf(scanner.nextLine());


            String query = "INSERT INTO department (dnumber, dname, mgr_ssn, mgr_start_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, dnumber);
                pstmt.setString(2, dname);
                pstmt.setString(3, mgr_ssn);
                pstmt.setDate(4, mgr_start_date);
                pstmt.executeUpdate();
                System.out.println("Department added successfully.");
            }

        } catch (Exception e) {
            System.out.println("Error adding department.");
            e.printStackTrace();
        }
    }

    public void getDepartmentById() {
    try (Connection conn = DBConnection.getConnection()) {
        System.out.print("Enter department number to search for: ");
        int dnumber = Integer.parseInt(scanner.nextLine());

        String query = "SELECT * FROM department WHERE dnumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, dnumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Department dept = new Department(
                    rs.getInt("dnumber"),
                    rs.getString("dname"),
                    rs.getString("mgr_ssn"),
                    rs.getDate("mgr_start_date")
                );

                System.out.println("Department Details:");
                System.out.println("Number      : " + dept.getDnumber());
                System.out.println("Name        : " + dept.getDname());
                System.out.println("Manager SSN : " + dept.getMgr_ssn());
                System.out.println("Start Date  : " + dept.getMgr_start_date());
            } else {
                System.out.println("No department found with number: " + dnumber);
            }
        }
    } catch (Exception e) {
        System.out.println("Error fetching department.");
        e.printStackTrace();
    }
}


    public void getAllDepartments() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM department";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("\n--- All Departments ---");
                while (rs.next()) {
                    System.out.println("Dept #: " + rs.getInt("dnumber") +
                                       ", Name: " + rs.getString("dname") +
                                       ", Manager SSN: " + rs.getString("mgr_ssn") +
                                       ", Start Date: " + rs.getDate("mgr_start_date"));
                }
            }

        } catch (Exception e) {
            System.out.println("Error fetching all departments.");
            e.printStackTrace();
        }
    }

    public void updateDepartment() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number to Update: ");
            int dnumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter New Department Name: ");
            String dname = scanner.nextLine();

            System.out.print("Enter New Manager SSN: ");
            String mgr_ssn = scanner.nextLine();

            // Validate mgr_ssn exists
            if (!isEmployeeExists(conn, mgr_ssn)) {
                System.out.println("No employee found with SSN: " + mgr_ssn);
                return;
            }

            System.out.print("Enter New Manager Start Date (yyyy-mm-dd): ");
            Date mgr_start_date = Date.valueOf(scanner.nextLine());

            String query = "UPDATE department SET dname = ?, mgr_ssn = ?, mgr_start_date = ? WHERE dnumber = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, dname);
                pstmt.setString(2, mgr_ssn);
                pstmt.setDate(3, mgr_start_date);
                pstmt.setInt(4, dnumber);

                int updated = pstmt.executeUpdate();
                if (updated > 0)
                    System.out.println("Department updated successfully.");
                else
                    System.out.println("No department found with number: " + dnumber);
            }

        } catch (Exception e) {
            System.out.println("Error updating department.");
            e.printStackTrace();
        }
    }

    public void deleteDepartment() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number to Delete: ");
            int dnumber = Integer.parseInt(scanner.nextLine());

            String query = "DELETE FROM department WHERE dnumber = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, dnumber);
                int deleted = pstmt.executeUpdate();
                if (deleted > 0)
                    System.out.println("Department deleted successfully.");
                else
                    System.out.println("No department found with number: " + dnumber);
            }

        } catch (Exception e) {
            System.out.println("Error deleting department.");
            e.printStackTrace();
        }
    }

        // ------------------- FILTER FEATURES -------------------------


    private void filterByManagerSSN() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Manager SSN: ");
            String ssn = scanner.nextLine();
            String query = "SELECT * FROM department WHERE mgr_ssn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nDepartments managed by SSN: " + ssn);
            while (rs.next()) {
                System.out.println("Dept#: " + rs.getInt("dnumber") + ", Name: " + rs.getString("dname"));
            }
        } catch (Exception e) {
            System.out.println("Error filtering departments.");
            e.printStackTrace();
        }
    }

    private void filterWithNoManager() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM department WHERE mgr_ssn IS NULL";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nDepartments with No Manager Assigned:");
            while (rs.next()) {
                System.out.println("Dept#: " + rs.getInt("dnumber") + ", Name: " + rs.getString("dname"));
            }
        } catch (Exception e) {
            System.out.println("Error filtering departments.");
            e.printStackTrace();
        }
    }

    private void filterByNameStartsWith() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter name prefix: ");
            String prefix = scanner.nextLine();
            String query = "SELECT * FROM department WHERE dname LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, prefix + "%");
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nDepartments starting with '" + prefix + "':");
            while (rs.next()) {
                System.out.println("Dept#: " + rs.getInt("dnumber") + ", Name: " + rs.getString("dname"));
            }
        } catch (Exception e) {
            System.out.println("Error filtering departments.");
            e.printStackTrace();
        }
    }

    private void filterByNameContains() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter name substring: ");
            String substr = scanner.nextLine();
            String query = "SELECT * FROM department WHERE dname LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + substr + "%");
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nDepartments containing '" + substr + "':");
            while (rs.next()) {
                System.out.println("Dept#: " + rs.getInt("dnumber") + ", Name: " + rs.getString("dname"));
            }
        } catch (Exception e) {
            System.out.println("Error filtering departments.");
            e.printStackTrace();
        }
    }

    private void filterByStartDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            Date start = Date.valueOf(scanner.nextLine());
            System.out.print("Enter End Date (YYYY-MM-DD): ");
            Date end = Date.valueOf(scanner.nextLine());

            String query = "SELECT * FROM department WHERE mgr_start_date BETWEEN ? AND ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, start);
            pstmt.setDate(2, end);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nDepartments with Manager Start Dates Between " + start + " and " + end + ":");
            while (rs.next()) {
                System.out.println("Dept#: " + rs.getInt("dnumber") + ", Name: " + rs.getString("dname") +
                                   ", Start Date: " + rs.getDate("mgr_start_date"));
            }
        } catch (Exception e) {
            System.out.println("Error filtering departments.");
            e.printStackTrace();
        }
        
    }
    public void departmentFilterMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Department Filter Menu ---");
        System.out.println("1. Filter by Manager SSN");
        System.out.println("2. Departments with no Manager");
        System.out.println("3. Department Name Starts With");
        System.out.println("4. Department Name Contains");
        System.out.println("5. Filter by Manager Start Date Range");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByManagerSSN();
            case 2 -> filterWithNoManager();
            case 3 -> filterByNameStartsWith();
            case 4 -> filterByNameContains();
            case 5 -> filterByStartDateRange();
            case 0 -> System.out.println("Exiting Department Filter Menu.");
            default -> System.out.println("Invalid choice.");
        }
        scanner.close();
    }

    

    
}
