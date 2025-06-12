package util;

import java.util.Scanner;

import dao.EmployeeDAO;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("===Welcome to FirmNest!===");
        
            System.out.println("\n===Main Menu===");
            System.out.println("1. Add Employee");
            System.out.println("2. Find Employee By SSN");
            System.out.println("3. See Details of All Employees");
            System.out.println("4. Upddate Employee Details");
            System.out.println("5. Delete Employee Details");
            System.out.println("6. Find Employees in a Department");
            System.out.println("7. Find details of Employees Working on a Project");
            System.out.println("8. Miscellaneous Employee Filters");


            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());


        EmployeeDAO employeeDAO = new EmployeeDAO();

            switch(choice) {
                case 1:
                    employeeDAO.addEmployee();
                    break;
                case 2:
                    employeeDAO.getEmployeeById();
                    break;
                case 3:
                    employeeDAO.getAllEmployees();
                    break;
                case 4:
                    employeeDAO.updateEmployee();
                    break;
                case 5:
                    employeeDAO.deleteEmployee();
                    break;
                case 6:
                    employeeDAO.getEmployeeByDeptId();
                    break;
                case 7:
                    employeeDAO.getEmployeesOnProject();
                    break;
                case 8:
                    employeeDAO.employeeFilterMenu();
                    break;
                case 0:
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
        }
        scanner.close();
        System.out.println("===Exiting! GoodBye!===");
        
    }
}
