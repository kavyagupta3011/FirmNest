package dao;

import java.sql.*;
import java.util.Scanner;
import db.DBConnection;


public class ProjectDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void addProject() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Project Number: ");
            int pnumber = Integer.parseInt(scanner.nextLine());

            // Check for duplicate project number
            PreparedStatement checkStmt = conn.prepareStatement("SELECT pnumber FROM project WHERE pnumber = ?");
            checkStmt.setInt(1, pnumber);
            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next()) {
                System.out.println("Project with this number already exists.");
                return;
            }

            System.out.print("Enter Project Name: ");
            String pname = scanner.nextLine();

            System.out.print("Enter Project Location: ");
            String plocation = scanner.nextLine();

            System.out.print("Enter Department Number: ");
            int dnum = Integer.parseInt(scanner.nextLine());

            // Check if department exists
            PreparedStatement deptStmt = conn.prepareStatement("SELECT dnumber FROM department WHERE dnumber = ?");
            deptStmt.setInt(1, dnum);
            ResultSet deptRs = deptStmt.executeQuery();
            if (!deptRs.next()) {
                System.out.println("Department does not exist. Cannot add project.");
                return;
            }

            String query = "INSERT INTO project (pnumber, pname, plocation, dnum) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pnumber);
            pstmt.setString(2, pname);
            pstmt.setString(3, plocation);
            pstmt.setInt(4, dnum);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Project added successfully.");
            } else {
                System.out.println("Failed to add project.");
            }
        } catch (Exception e) {
            System.out.println("Error adding project.");
            e.printStackTrace();
        }
    }

    public void getProjectById() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Project Number to search: ");
            int pnumber = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM project WHERE pnumber = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pnumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Project Number: " + rs.getInt("pnumber"));
                System.out.println("Name: " + rs.getString("pname"));
                System.out.println("Location: " + rs.getString("plocation"));
                System.out.println("Department Number: " + rs.getInt("dnum"));
            } else {
                System.out.println("No project found with number: " + pnumber);
            }
        } catch (Exception e) {
            System.out.println("Error fetching project.");
            e.printStackTrace();
        }
    }

    public void getAllProjects() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM project";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("=== All Projects ===");
            while (rs.next()) {
                System.out.println("Project Number: " + rs.getInt("pnumber"));
                System.out.println("Name: " + rs.getString("pname"));
                System.out.println("Location: " + rs.getString("plocation"));
                System.out.println("Department Number: " + rs.getInt("dnum"));
                System.out.println("---------------------");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving projects.");
            e.printStackTrace();
        }
    }

    public void getProjectsByDeptId() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number: ");
            int dnum = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM project WHERE dnum = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, dnum);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Project Number: " + rs.getInt("pnumber"));
                System.out.println("Name: " + rs.getString("pname"));
                System.out.println("Location: " + rs.getString("plocation"));
                System.out.println("---------------------");
            }

            if (!found) {
                System.out.println("No projects found for department number: " + dnum);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving projects by department.");
            e.printStackTrace();
        }
    }

    public void updateProject() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Project Number to update: ");
            int pnumber = Integer.parseInt(scanner.nextLine());

            // Check if project exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM project WHERE pnumber = ?");
            checkStmt.setInt(1, pnumber);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Project not found.");
                return;
            }

            System.out.print("Enter New Project Name: ");
            String pname = scanner.nextLine();

            System.out.print("Enter New Location: ");
            String plocation = scanner.nextLine();

            System.out.print("Enter New Department Number: ");
            int dnum = Integer.parseInt(scanner.nextLine());

            // Check if department exists
            PreparedStatement deptStmt = conn.prepareStatement("SELECT dnumber FROM department WHERE dnumber = ?");
            deptStmt.setInt(1, dnum);
            ResultSet deptRs = deptStmt.executeQuery();
            if (!deptRs.next()) {
                System.out.println("Department does not exist.");
                return;
            }

            String updateQuery = "UPDATE project SET pname = ?, plocation = ?, dnum = ? WHERE pnumber = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, pname);
            pstmt.setString(2, plocation);
            pstmt.setInt(3, dnum);
            pstmt.setInt(4, pnumber);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Project updated successfully.");
            } else {
                System.out.println("Failed to update project.");
            }
        } catch (Exception e) {
            System.out.println("Error updating project.");
            e.printStackTrace();
        }
    }

    public void deleteProject() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Project Number to delete: ");
            int pnumber = Integer.parseInt(scanner.nextLine());

            String deleteQuery = "DELETE FROM project WHERE pnumber = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, pnumber);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Project deleted successfully.");
            } else {
                System.out.println("No project found with the given number.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting project.");
            e.printStackTrace();
        }
    }

        // ---------------------- FILTERS ------------------------

    

    private void filterByNameKeyword() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter keyword to search in project name: ");
            String keyword = scanner.nextLine();

            String query = "SELECT * FROM project WHERE pname LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();
            displayProjectResults(rs);
        } catch (Exception e) {
            System.out.println("Error filtering by project name.");
            e.printStackTrace();
        }
    }

    private void filterByLocation() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter location to filter by: ");
            String location = scanner.nextLine();

            String query = "SELECT * FROM project WHERE plocation = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, location);

            ResultSet rs = pstmt.executeQuery();
            displayProjectResults(rs);
        } catch (Exception e) {
            System.out.println("Error filtering by location.");
            e.printStackTrace();
        }
    }

    private void filterByDepartmentNumber() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number to filter by: ");
            int dnum = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM project WHERE dnum = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, dnum);

            ResultSet rs = pstmt.executeQuery();
            displayProjectResults(rs);
        } catch (Exception e) {
            System.out.println("Error filtering by department number.");
            e.printStackTrace();
        }
    }

    private void displayProjectResults(ResultSet rs) throws SQLException {
        boolean found = false;
        System.out.println("\n--- Filtered Projects ---");
        while (rs.next()) {
            found = true;
            System.out.println("Project Number : " + rs.getInt("pnumber"));
            System.out.println("Name           : " + rs.getString("pname"));
            System.out.println("Location       : " + rs.getString("plocation"));
            System.out.println("Department No. : " + rs.getInt("dnum"));
            System.out.println("---------------------------");
        }
        if (!found) {
            System.out.println("No matching projects found.");
        }
    }

    public void projectFilterMenu() {
        System.out.println("\n--- Project Filter Menu ---");
        System.out.println("1. Filter by Project Name Keyword");
        System.out.println("2. Filter by Location");
        System.out.println("3. Filter by Department Number");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByNameKeyword();
            case 2 -> filterByLocation();
            case 3 -> filterByDepartmentNumber();
            case 0 -> System.out.println("Exiting project filter menu.");
            default -> System.out.println("Invalid choice.");
        }
    }


}
