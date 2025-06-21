package dao;

import db.DBConnection;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class EmployeeDAO {

    public void addEmployee(){
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DBConnection.getConnection()){
            System.out.println("Enter employee details:");

            System.out.println("SSN: ");
            String ssn = scanner.nextLine();

            String checkSSNQuery = "SELECT ssn FROM employee WHERE ssn = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSSNQuery)) {
                checkStmt.setString(1, ssn);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Employee with SSN " + ssn + " already exists.");
                    return;
                }
            }

            System.out.println("First Name: ");
            String fname = scanner.nextLine();
            System.out.println("Middle Initial: ");
            String minit = scanner.nextLine();
            System.out.println("Last Name: ");
            String lname = scanner.nextLine();
            System.out.println("Birth Date (YYYY-MM-DD): ");
            String bdate = scanner.nextLine();
            System.out.println("Address: ");
            String address = scanner.nextLine();
            System.out.println("Gender(M/F): ");
            String sex = scanner.nextLine();
            System.out.println("Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine(); // Consume newline left-over
            System.out.println("Supervisor SSN(can be null): ");
            String super_ssn = scanner.nextLine();
            if (super_ssn.isBlank()) {
                super_ssn = null; // Handle null case
            }
            else {
                // Check if supervisor SSN exists
                String checkSuperQuery = "SELECT ssn FROM employee WHERE ssn = ?";
                try (PreparedStatement superStmt = conn.prepareStatement(checkSuperQuery)) {
                    superStmt.setString(1, super_ssn);
                    ResultSet rs = superStmt.executeQuery();
                    if (!rs.next()) {
                        System.out.println("Supervisor SSN " + super_ssn + " does not exist.");
                        return;
                    }
                }
            }
            System.out.println("Department Number: ");
            int dno = Integer.parseInt(scanner.nextLine());

            // Check if department exists
            String checkDeptQuery = "SELECT dnumber FROM department WHERE dnumber = ?";
            try (PreparedStatement deptStmt = conn.prepareStatement(checkDeptQuery)) {
                deptStmt.setInt(1, dno);
                ResultSet rs = deptStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Department number " + dno + " does not exist.");
                    return;
                }
            }
            String query="INSERT INTO employee (ssn, fname, minit, lname, bdate, address, sex, salary, super_ssn, dno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, ssn);
            pstmt.setString(2, fname);
            pstmt.setString(3, minit);
            pstmt.setString(4, lname);
            pstmt.setDate(5, Date.valueOf(bdate));
            pstmt.setString(6, address);
            pstmt.setString(7, sex);
            pstmt.setDouble(8, salary);
            if (super_ssn == null) pstmt.setNull(9, Types.VARCHAR); else pstmt.setString(9, super_ssn);
            pstmt.setInt(10, dno);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee added successfully.");
            } else {
                System.out.println("Failed to add employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    public Employee getEmployeeById(){
        Scanner scanner = new Scanner(System.in);
        Employee emp = null;

        System.out.println("Enter employee SSN to search: ");
        String ssn = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT * FROM employee WHERE ssn = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, ssn);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            emp = new Employee(
                rs.getString("ssn"),
                rs.getString("fname"),
                rs.getString("minit"),
                rs.getString("lname"),
                rs.getDate("bdate"),
                rs.getString("address"),
                rs.getString("sex"),
                rs.getDouble("salary"),
                rs.getString("super_ssn"),
                rs.getInt("dno")
            );

            System.out.println("Employee found:");
            System.out.println("Name: " + emp.getFname() + " " + emp.getMinit() + " " + emp.getLname());
            System.out.println("DOB: " + emp.getBdate());
            System.out.println("Address: " + emp.getAddress());
            System.out.println("Gender: " + emp.getSex());
            System.out.println("Salary: " + emp.getSalary());
            System.out.println("Supervisor SSN: " + emp.getSuper_ssn());
            System.out.println("Department No: " + emp.getDno());

        } else {
            System.out.println("No employee found with SSN: " + ssn);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    scanner.close();
    return emp;
    }

    public void getAllEmployees() {
    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT ssn, fname, minit, lname FROM employee";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("\n--- List of All Employees ---");
        System.out.printf("%-15s %-30s\n", "SSN", "Full Name");
        System.out.println("-----------------------------------------------");

        boolean hasResults = false;
        while (rs.next()) {
            hasResults = true;
            String ssn = rs.getString("ssn");
            String fname = rs.getString("fname");
            String minit = rs.getString("minit");
            String lname = rs.getString("lname");

            String fullName = fname + " " + (minit != null && !minit.isEmpty() ? minit + ". " : "") + lname;
            System.out.printf("%-15s %-30s\n", ssn, fullName);
        }

        if (!hasResults) {
            System.out.println("No employees found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public Employee getEmployeeByIdHelper(String ssn) {
    Employee emp = null;
    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT * FROM employee WHERE ssn = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, ssn);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            emp = new Employee(
                rs.getString("ssn"),
                rs.getString("fname"),
                rs.getString("minit"),
                rs.getString("lname"),
                rs.getDate("bdate"),
                rs.getString("address"),
                rs.getString("sex"),
                rs.getDouble("salary"),
                rs.getString("super_ssn"),
                rs.getInt("dno")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return emp;
    }

    public void updateEmployee() {
    Scanner scanner = new Scanner(System.in);

    try (Connection conn = DBConnection.getConnection()) {
        System.out.print("Enter SSN of employee to update: ");
        String ssn = scanner.nextLine();

        Employee emp = getEmployeeByIdHelper(ssn);
        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        // Pre-fill current values
        String newFname = emp.getFname();
        String newLname = emp.getLname();
        String newAddress = emp.getAddress();
        double newSalary = emp.getSalary();
        String newSex = emp.getSex();
        Date newBdate = emp.getBdate();
        String newSuperSSN = emp.getSuper_ssn();
        Integer oldDno = emp.getDno();  // For tracking change
        Integer newDno = oldDno;

        boolean done = false;
        while (!done) {
            System.out.println("\nWhat do you want to update?");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Address");
            System.out.println("4. Salary");
            System.out.println("5. Sex");
            System.out.println("6. Birth Date (yyyy-mm-dd)");
            System.out.println("7. Supervisor SSN");
            System.out.println("8. Department No");
            System.out.println("0. Done");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new First Name: ");
                    newFname = scanner.nextLine();
                }
                case 2 -> {
                    System.out.print("Enter new Last Name: ");
                    newLname = scanner.nextLine();
                }
                case 3 -> {
                    System.out.print("Enter new Address: ");
                    newAddress = scanner.nextLine();
                }
                case 4 -> {
                    System.out.print("Enter new Salary: ");
                    newSalary = Double.parseDouble(scanner.nextLine());
                }
                case 5 -> {
                    System.out.print("Enter new Sex (M/F): ");
                    newSex = scanner.nextLine();
                }
                case 6 -> {
                    System.out.print("Enter new Birth Date (yyyy-mm-dd): ");
                    newBdate = Date.valueOf(scanner.nextLine());
                }
                case 7 -> {
                    System.out.print("Enter new Supervisor SSN: ");
                    newSuperSSN = scanner.nextLine();
                }
                case 8 -> {
                    System.out.print("Enter new Department No: ");
                    newDno = Integer.parseInt(scanner.nextLine());
                }
                case 0 -> done = true;
                default -> System.out.println("Invalid choice.");
            }
        }

        String query = "UPDATE employee SET fname = ?, lname = ?, address = ?, salary = ?, sex = ?, bdate = ?, super_ssn = ?, dno = ? WHERE ssn = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newFname);
        pstmt.setString(2, newLname);
        pstmt.setString(3, newAddress);
        pstmt.setDouble(4, newSalary);
        pstmt.setString(5, newSex);
        pstmt.setDate(6, newBdate);
        pstmt.setString(7, newSuperSSN);
        pstmt.setInt(8, newDno);
        pstmt.setString(9, ssn);

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Employee updated successfully.");

            // Log department change if it was changed
            if (!Objects.equals(oldDno, newDno)) {
                String logQuery = "INSERT INTO dept_change_history (ssn, old_dno, new_dno) VALUES (?, ?, ?)";
                try (PreparedStatement logStmt = conn.prepareStatement(logQuery)) {
                    logStmt.setString(1, ssn);
                    logStmt.setInt(2, oldDno);
                    logStmt.setInt(3, newDno);
                    logStmt.executeUpdate();
                    System.out.println("Department change history recorded.");
                }
            }
            if (emp.getSalary() != newSalary) {
            String salaryLogQuery = "INSERT INTO salary_change_history (ssn, old_salary, new_salary) VALUES (?, ?, ?)";
            try (PreparedStatement sStmt = conn.prepareStatement(salaryLogQuery)) {
                sStmt.setString(1, ssn);
                sStmt.setDouble(2, emp.getSalary());
                sStmt.setDouble(3, newSalary);
                sStmt.executeUpdate();
                System.out.println("Salary change history recorded.");
                }
            }
            if (!Objects.equals(emp.getSuper_ssn(), newSuperSSN)) {
            String superLogQuery = "INSERT INTO supervisor_change_history (ssn, old_super_ssn, new_super_ssn) VALUES (?, ?, ?)";
            try (PreparedStatement supStmt = conn.prepareStatement(superLogQuery)) {
                supStmt.setString(1, ssn);
                supStmt.setString(2, emp.getSuper_ssn());
                supStmt.setString(3, newSuperSSN);
                supStmt.executeUpdate();
                System.out.println("Supervisor change history recorded.");
                }
            }



        } else {
            System.out.println("Update failed.");
        }

    } catch (Exception e) {
        System.out.println("Error updating employee.");
        e.printStackTrace();
    }

    scanner.close();
}

    public void viewDepartmentChangeHistory() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter employee SSN to view department change history: ");
    String ssn = scanner.nextLine();

    String query = """
        SELECT dch.old_dno, dch.new_dno, dch.change_date,
               d1.dname AS old_dept, d2.dname AS new_dept
        FROM dept_change_history dch
        LEFT JOIN department d1 ON dch.old_dno = d1.dnumber
        LEFT JOIN department d2 ON dch.new_dno = d2.dnumber
        WHERE dch.ssn = ?
        ORDER BY dch.change_date DESC
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, ssn);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\n--- Department Change History ---");
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.printf("From Dept #%d (%s) → To Dept #%d (%s) on %s\n",
                    rs.getInt("old_dno"),
                    rs.getString("old_dept"),
                    rs.getInt("new_dno"),
                    rs.getString("new_dept"),
                    rs.getTimestamp("change_date").toString());
        }

        if (!found) {
            System.out.println("No department changes recorded for this employee.");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving department change history.");
        e.printStackTrace();
    }
    scanner.close();
}


    public void viewSalaryChangeHistory() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Employee SSN to view salary change history: ");
    String ssn = scanner.nextLine();

    String query = "SELECT * FROM salary_change_history WHERE ssn = ? ORDER BY change_date DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, ssn);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\n--- Salary Change History ---");
        System.out.printf("%-10s %-12s %-12s %-20s%n", "SSN", "Old Salary", "New Salary", "Change Date");

        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.printf("%-10s %-12.2f %-12.2f %-20s%n",
                rs.getString("ssn"),
                rs.getDouble("old_salary"),
                rs.getDouble("new_salary"),
                rs.getTimestamp("change_date").toString()
            );
        }

        if (!found) System.out.println("No salary change history found for this employee.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
    scanner.close();
}

    public void viewSupervisorChangeHistory() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Employee SSN to view supervisor change history: ");
    String ssn = scanner.nextLine();

    String query = "SELECT * FROM supervisor_change_history WHERE ssn = ? ORDER BY change_date DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, ssn);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\n--- Supervisor Change History ---");
        System.out.printf("%-10s %-15s %-15s %-20s%n", "SSN", "Old Supervisor", "New Supervisor", "Change Date");

        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.printf("%-10s %-15s %-15s %-20s%n",
                rs.getString("ssn"),
                rs.getString("old_super_ssn") != null ? rs.getString("old_super_ssn") : "NULL",
                rs.getString("new_super_ssn") != null ? rs.getString("new_super_ssn") : "NULL",
                rs.getTimestamp("change_date").toString()
            );
        }

        if (!found) System.out.println("No supervisor change history found for this employee.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
    scanner.close();
}



    public void deleteEmployee(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter SSN of employee to delete: ");
        String ssn = scanner.nextLine();
        try (Connection conn = DBConnection.getConnection()){
            String query = "DELETE FROM employee WHERE ssn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,ssn);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("No employee found with SSN: " + ssn);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
        scanner.close();
    }

    public List<Employee> getEmployeeByDeptId(){
        List<Employee> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DBConnection.getConnection()){
            System.out.print("Enter department No. to search employees: ");
            int dno = Integer.parseInt(scanner.nextLine());
            String query = "SELECT * FROM employee WHERE dno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, dno);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            list.add(new Employee(
                rs.getString("ssn"),
                rs.getString("fname"),
                rs.getString("minit"),
                rs.getString("lname"),
                rs.getDate("bdate"),
                rs.getString("address"),
                rs.getString("sex"),
                rs.getDouble("salary"),
                rs.getString("super_ssn"),
                rs.getInt("dno")
            ));
        }

        if (list.isEmpty()) {
            System.out.println("No employees found for Department ID: " + dno);
        } else {
            System.out.println("\nEmployees in Department " + dno + ":");
            for (Employee e : list) {
                System.out.println("- " + e.getFname() + " " + e.getLname() + " (SSN: " + e.getSsn() + ")");
            }
        }
        } catch (Exception e) {
        e.printStackTrace();
    }
    scanner.close();
    return list;
    }


    public List<Employee> getEmployeesOnProject() {
        List<Employee> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Project ID to search employees working on it: ");
            int projectId = Integer.parseInt(scanner.nextLine());

            String query = "SELECT e.* FROM employee e JOIN works_on w ON e.ssn = w.essn WHERE w.pno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Employee(
                    rs.getString("ssn"),
                    rs.getString("fname"),
                    rs.getString("minit"),
                    rs.getString("lname"),
                    rs.getDate("bdate"),
                    rs.getString("address"),
                    rs.getString("sex"),
                    rs.getDouble("salary"),
                    rs.getString("super_ssn"),
                    rs.getInt("dno")
                ));
            }

            if (list.isEmpty()) {
                System.out.println("No employees found for Project ID: " + projectId);
            } else {
                System.out.println("\nEmployees on Project " + projectId + ":");
                for (Employee e : list) {
                    System.out.println("- " + e.getFname() + " " + e.getLname() + " (SSN: " + e.getSsn() + ")");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner.close();
        return list;
    }



    //--------------------------------
    private void displayEmployeeResults(ResultSet rs) throws SQLException {
        boolean hasResults = false;

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-10s | %-10s | %-10s | %-6s | %-10s | %-10s |\n",
                        "SSN", "Full Name", "Gender", "Birth Date", "Salary", "Dept#", "Supervisor", "Address");
        System.out.println("--------------------------------------------------------------------------------");

        while (rs.next()) {
            hasResults = true;

            String ssn = rs.getString("ssn");
            String fname = rs.getString("fname");
            String minit = rs.getString("minit");
            String lname = rs.getString("lname");
            String fullName = fname + " " + (minit != null ? minit + ". " : "") + lname;

            String gender = rs.getString("sex");
            Date bdate = rs.getDate("bdate");
            double salary = rs.getDouble("salary");
            String super_ssn = rs.getString("super_ssn");
            int dno = rs.getInt("dno");
            String address = rs.getString("address");

            System.out.printf("| %-10s | %-15s | %-10s | %-10s | %-10.2f | %-6d | %-10s | %-10s |\n",
                            ssn, fullName, gender, bdate.toString(), salary, dno,
                            (super_ssn != null ? super_ssn : "NULL"), (address != null ? address : "-"));
        }

        if (!hasResults) {
            System.out.println("No matching employees found.");
        } else {
            System.out.println("--------------------------------------------------------------------------------");
        }
    }

    public void filterByName(String type) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name pattern: ");
        String pattern = scanner.nextLine();
        String query;
        scanner.close();
        switch (type) {
            case "start" -> query = "SELECT * FROM employee WHERE fname LIKE ?";
            case "end" -> query = "SELECT * FROM employee WHERE fname LIKE ?";
            case "contains" -> query = "SELECT * FROM employee WHERE fname LIKE ?";
            default -> {
                System.out.println("Invalid type.");
                return;
            }
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            switch (type) {
                case "start" -> pstmt.setString(1, pattern + "%");
                case "end" -> pstmt.setString(1, "%" + pattern);
                case "contains" -> pstmt.setString(1, "%" + pattern + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }


    public void filterByGender() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter gender (M/F): ");
        String gender = scanner.nextLine().toUpperCase();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE sex = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, gender);
            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    public void filterByBirthDateRange() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String start = scanner.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String end = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE bdate BETWEEN ? AND ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(start));
            pstmt.setDate(2, Date.valueOf(end));
            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
    }


    public void filterByBirthYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter birth year (e.g., 1995): ");
        String year = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE YEAR(bdate) = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, year);
            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
    }



    public void filterBySalary(String comparison) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter salary value: ");
        double salary = Double.parseDouble(scanner.nextLine());
        scanner.close();
        String query = switch (comparison) {
            case "greater" -> "SELECT * FROM employee WHERE salary > ?";
            case "less" -> "SELECT * FROM employee WHERE salary < ?";
            default -> null;
        };

        if (query == null) return;

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, salary);
            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }


    public void filterBySalaryRange() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum salary: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter maximum salary: ");
        double max = Double.parseDouble(scanner.nextLine());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE salary BETWEEN ? AND ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, min);
            pstmt.setDouble(2, max);
            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    public void filterBySupervisorSSN() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Supervisor SSN: ");
        String superSsn = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE super_ssn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, superSsn);
            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    public void filterByNullSupervisor() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE super_ssn IS NULL";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            displayEmployeeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void employeeFilterMenu() {
        Scanner scanner = new Scanner(System.in);
        
            System.out.println("\n--- Employee Filter Menu ---");
            System.out.println("1. Name starts with");
            System.out.println("2. Name ends with");
            System.out.println("3. Name contains");
            System.out.println("4. Filter by Gender");
            System.out.println("5. Filter by Birth Date Range");
            System.out.println("6. Filter by Birth Year");
            System.out.println("7. Salary > X");
            System.out.println("8. Salary < X");
            System.out.println("9. Salary between X and Y");
            System.out.println("10. Filter by Department No.");
            System.out.println("11. Filter by Supervisor SSN");
            System.out.println("12. Employees with no Supervisor");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> filterByName("start");
                case 2 -> filterByName("end");
                case 3 -> filterByName("contains");
                case 4 -> filterByGender();
                case 5 -> filterByBirthDateRange();
                case 6 -> filterByBirthYear();
                case 7 -> filterBySalary("greater");
                case 8 -> filterBySalary("less");
                case 9 -> filterBySalaryRange();
                case 10 -> getEmployeeByDeptId();
                case 11 -> filterBySupervisorSSN();
                case 12 -> filterByNullSupervisor();
                default -> System.out.println("Invalid choice.");            
        }
        scanner.close();
    }
        


}
