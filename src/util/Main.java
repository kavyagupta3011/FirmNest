package util;

import dao.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("=== Welcome to FirmNest ===");

        String username = null, role = null;
        boolean loggedIn = false;

        // Login/Register
        while (!loggedIn) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Enter choice: ");
            String authChoice = scanner.nextLine();

            if (authChoice.equals("1")) {
                userDAO.registerUser();
            } else if (authChoice.equals("2")) {
                System.out.print("Username: ");
                username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                if (userDAO.validateLogin(username, password)) {
                    role = userDAO.getUserRole(username);
                    userDAO.getUserSSN(username); // Removed assignment to unused variable
                    loggedIn = true;
                    System.out.println("\nLogin successful! Role: " + role);
                } else {
                    System.out.println("Invalid credentials. Try again.");
                }
            } else {
                System.out.println("Invalid input. Choose 1 or 2.");
            }
        }

        // DAOs
        EmployeeDAO employeeDAO = new EmployeeDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        DeptLocationsDAO deptLocationsDAO = new DeptLocationsDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        WorksOnDAO worksOnDAO = new WorksOnDAO();
        DependentDAO dependentDAO = new DependentDAO();
        TrainingProgramDAO trainingProgramDAO = new TrainingProgramDAO();
        EmployeeTrainingDAO employeeTrainingDAO = new EmployeeTrainingDAO();
        AssetsDAO assetsDAO = new AssetsDAO();
        AssetMaintenanceDAO assetMaintenanceDAO = new AssetMaintenanceDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        PerformanceReviewDAO performanceReviewDAO = new PerformanceReviewDAO();
        LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();

        int choice;
        do {
            // Display Role-Based Menu
            System.out.println("\n=== Main Menu (" + role + ") ===");

            if (role.equals("Admin") || role.equals("HR")) {
                System.out.println("1. Add Employee");
                System.out.println("2. Find Employee By SSN");
                System.out.println("4. Update Employee Details");
                System.out.println("6. Find Employees in a Department");
                System.out.println("7. Find details of Employees Working on a Project");
                System.out.println("8. Miscellaneous Employee Filters");
                System.out.println("25. Find Projects by Employee");
                System.out.println("26. Find Employees by Project");
                System.out.println("31. Find Dependents by Employee");
                System.out.println("33. Add Training Program");
                System.out.println("34. See All Training Programs");
                System.out.println("36. Update Training Program");
                System.out.println("37. Delete Training Program");
                System.out.println("39. Get Training Enrollments by Employee");
                System.out.println("40. Get Training Enrollments by Training Program");
                System.out.println("54. Add Performance Review");
                System.out.println("56. Update Performance Review");

            }

            if (role.equals("Admin")) {
                System.out.println("3. See Details of All Employees");
                System.out.println("5. Delete Employee Details");
                System.out.println("9. Add Department");
                System.out.println("10. Find Department By Number");
                System.out.println("11. See Details of All Departments");
                System.out.println("12. Update Department Details");
                System.out.println("13. Delete Department Details");
                System.out.println("14. Add Department Location");
                System.out.println("15. Find Locations of a Department");
                System.out.println("16. Delete Department Location");
                System.out.println("17. See All Department Locations");
                System.out.println("18. Add Project");
                System.out.println("19. Find Project By Number");
                System.out.println("20. See Details of All Projects");
                System.out.println("21. Find Project By Department");
                System.out.println("22. Update Project Details");
                System.out.println("23. Delete Project Details");
                System.out.println("24. Assign Employee to Project");
                System.out.println("27. De-assign Employee from Project");
                System.out.println("28. Update Employee Project Hours");
                System.out.println("29. See All Works On Records");
                System.out.println("42. Add Asset");
                System.out.println("43. Assign Asset to Employee");
                System.out.println("44. Get Assets by Employee");
                System.out.println("45. Update Asset Details");
                System.out.println("46. Delete Asset");
                System.out.println("47. Record Asset Maintenance");
                System.out.println("48. Get Maintenance Records by Asset");
                System.out.println("49. Get All Maintenance Logs");
                System.out.println("53. Get Monthly Attendance Report");
                System.out.println("57. View Department Change History");
                System.out.println("58. View Supervisor Change History");
                System.out.println("59. View Salary Change History");
                System.out.println("73. View All Leave Requests");
                System.out.println("74. Update Leave Request Status");
                System.out.println("75. Filter: Pending Requests");
                System.out.println("76. Filter: Leaves in a Given Month");
            }

            if (role.equals("Admin") || role.equals("HR") || role.equals("CompanyEmployee")) {
                System.out.println("30. Add Dependent");
                System.out.println("32. Delete Dependent");
                System.out.println("35. Get Training Program By ID");
                System.out.println("38. Enroll Employee in Training");
                System.out.println("41. Delete Training Enrollment");
                System.out.println("50. Mark Attendance");
                System.out.println("51. Get Attendance by Employee");
                System.out.println("52. Get Attendance by Date");
                System.out.println("55. Get Reviews by Employee");
                System.out.println("60. Miscellaneous Department Filter");
                System.out.println("61. Miscellaneous Department Locations Filter");
                System.out.println("62. Miscellaneous Dependent Filter");
                System.out.println("63. Miscellaneous Asset Filter");
                System.out.println("64. Miscellaneous Asset Maintenance Filter");
                System.out.println("65. Miscellaneous Project Filter");
                System.out.println("66. Miscellaneous Works On Filter");
                System.out.println("67. Miscellaneous Training Filter");
                System.out.println("68. Miscellaneous Employee Training Filter");
                System.out.println("69. Miscellaneous Attendance Filter");
                System.out.println("70. Miscellaneous Performance Review Filter");
                System.out.println("71. Apply for Leave / Work From Home");
                System.out.println("72. View Leave Requests by Employee");
                System.out.println("77. Mark Training as Completed");

            }

            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            if (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine().trim();
                if (inputLine.isEmpty()) {
                    choice = -1; // invalid
                } else {
                    try {
                        choice = Integer.parseInt(inputLine);
                    } catch (NumberFormatException e) {
                        choice = -1;
                    }
                }
            } else {
                choice = 0; // End of input stream (e.g., Ctrl+D)
                break;
            }

            // Action Routing (role-safe, no need to restrict again since menu is role-filtered)
            switch (choice) {
                case 1 -> employeeDAO.addEmployee();
                case 2 -> employeeDAO.getEmployeeById();
                case 3 -> employeeDAO.getAllEmployees();
                case 4 -> employeeDAO.updateEmployee();
                case 5 -> employeeDAO.deleteEmployee();
                case 6 -> employeeDAO.getEmployeeByDeptId();
                case 7 -> employeeDAO.getEmployeesOnProject();
                case 8 -> employeeDAO.employeeFilterMenu();
                case 9 -> departmentDAO.addDepartment();
                case 10 -> departmentDAO.getDepartmentById();
                case 11 -> departmentDAO.getAllDepartments();
                case 12 -> departmentDAO.updateDepartment();
                case 13 -> departmentDAO.deleteDepartment();
                case 14 -> deptLocationsDAO.addLocation();
                case 15 -> deptLocationsDAO.getLocationsByDeptId();
                case 16 -> deptLocationsDAO.deleteLocation();
                case 17 -> deptLocationsDAO.getAllLocations();
                case 18 -> projectDAO.addProject();
                case 19 -> projectDAO.getProjectById();
                case 20 -> projectDAO.getAllProjects();
                case 21 -> projectDAO.getProjectsByDeptId();
                case 22 -> projectDAO.updateProject();
                case 23 -> projectDAO.deleteProject();
                case 24 -> worksOnDAO.assignEmployeeToProject();
                case 25 -> worksOnDAO.getProjectsByEmployee();
                case 26 -> worksOnDAO.getEmployeesByProject();
                case 27 -> worksOnDAO.removeEmployeeFromProject();
                case 28 -> worksOnDAO.updateHoursForAssignment();
                case 29 -> worksOnDAO.getAllAssignments();
                case 30 -> dependentDAO.addDependent();
                case 31 -> dependentDAO.getDependentsByEmployee();
                case 32 -> dependentDAO.deleteDependent();
                case 33 -> trainingProgramDAO.addTraining();
                case 34 -> trainingProgramDAO.getAllTrainings();
                case 35 -> trainingProgramDAO.getTrainingById();
                case 36 -> trainingProgramDAO.updateTraining();
                case 37 -> trainingProgramDAO.deleteTraining();
                case 38 -> employeeTrainingDAO.enrollEmployeeInTraining();
                case 39 -> employeeTrainingDAO.getTrainingEnrollmentsByEmployee();
                case 40 -> employeeTrainingDAO.getEmployeesInTraining();
                case 41 -> employeeTrainingDAO.removeEmployeeFromTraining();
                case 42 -> assetsDAO.addAsset();
                case 43 -> assetsDAO.assignAssetToEmployee();
                case 44 -> assetsDAO.getAssetsByEmployee();
                case 45 -> assetsDAO.updateAssetStatus();
                case 46 -> assetsDAO.deleteAsset();
                case 47 -> assetMaintenanceDAO.recordMaintenance();
                case 48 -> assetMaintenanceDAO.getMaintenanceByAsset();
                case 49 -> assetMaintenanceDAO.getAllMaintenanceLogs();
                case 50 -> attendanceDAO.markAttendance();
                case 51 -> attendanceDAO.getAttendanceByEmployee();
                case 52 -> attendanceDAO.getAttendanceByDate();
                case 53 -> attendanceDAO.getMonthlyAttendanceReport();
                case 54 -> performanceReviewDAO.addReview();
                case 55 -> performanceReviewDAO.getReviewsByEmployee();
                case 56 -> performanceReviewDAO.updateReview();
                case 57 -> employeeDAO.viewDepartmentChangeHistory();
                case 58 -> employeeDAO.viewSupervisorChangeHistory();
                case 59 -> employeeDAO.viewSalaryChangeHistory();
                case 60 -> departmentDAO.departmentFilterMenu();
                case 61 -> deptLocationsDAO.deptLocationFilterMenu();
                case 62 -> dependentDAO.dependentFilterMenu();
                case 63 -> assetsDAO.assetFilterMenu();
                case 64 -> assetMaintenanceDAO.maintenanceFilterMenu();
                case 65 -> projectDAO.projectFilterMenu();
                case 66 -> worksOnDAO.worksOnFilterMenu();
                case 67 -> trainingProgramDAO.trainingFilterMenu();
                case 68 -> employeeTrainingDAO.trainingEnrollmentFilterMenu();
                case 69 -> attendanceDAO.attendanceFilterMenu();
                case 70 -> performanceReviewDAO.reviewFilterMenu();
                case 71 -> leaveRequestDAO.applyLeave();
                case 72 -> leaveRequestDAO.viewLeavesByEmployee();
                case 73 -> leaveRequestDAO.viewAllRequests();
                case 74 -> leaveRequestDAO.updateRequestStatus();
                case 75 -> leaveRequestDAO.filterPendingRequests();
                case 76 -> leaveRequestDAO.filterLeavesInMonth();
                case 77 -> employeeTrainingDAO.markTrainingAsCompleted();
                case 0 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
        scanner.close();
    }
}
