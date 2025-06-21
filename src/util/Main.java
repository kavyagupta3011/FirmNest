package util;

import java.util.Scanner;


import dao.EmployeeDAO;
import dao.DepartmentDAO;
import dao.DeptLocationsDAO;
import dao.ProjectDAO;
import dao.WorksOnDAO;
import dao.DependentDAO;
import dao.TrainingProgramDAO;
import dao.EmployeeTrainingDAO;
import dao.AssetsDAO;
import dao.AssetMaintenanceDAO;
import dao.AttendanceDAO;
import dao.PerformanceReviewDAO;


public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("===Welcome to FirmNest!===");
        
            System.out.println("\n===Main Menu===");
            System.out.println("1. Add Employee");
            System.out.println("2. Find Employee By SSN");
            System.out.println("3. See Details of All Employees");
            System.out.println("4. Update Employee Details");
            System.out.println("5. Delete Employee Details");
            System.out.println("6. Find Employees in a Department");
            System.out.println("7. Find details of Employees Working on a Project");
            System.out.println("8. Miscellaneous Employee Filters");

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
            System.out.println("25. Find Projects by Employee");
            System.out.println("26. Find Employees by Project");
            System.out.println("27. De-assign Employee from Project");
            System.out.println("28. Update Employee Project Hours");
            System.out.println("29. See All Works On Records");

            System.out.println("30. Add Dependent");
            System.out.println("31. Find Dependents by Employee");
            System.out.println("32. Delete Dependent");

            System.out.println("33. Add Training Program");
            System.out.println("34. See All Training Programs");
            System.out.println("35. Get Training Program By ID");
            System.out.println("36. Update Training Program");
            System.out.println("37. Delete Training Program");

            System.out.println("38. Enroll Employee in Training");
            System.out.println("39. Get Training Enrollments by Employee");
            System.out.println("40. Get Training Enrollments by Training Program");
            System.out.println("41. Delete Training Enrollment");

            System.out.println("42. Add Asset");
            System.out.println("43. Assign Asset to Employee");
            System.out.println("44. Get Assets by Employee");
            System.out.println("45. Update Asset Details");
            System.out.println("46. Delete Asset");

            System.out.println("47. Record Asset Maintenance");
            System.out.println("48. Get Maintenance Records by Asset");
            System.out.println("49. Get All Maintenance Logs");

            System.out.println("50. Mark Attendance");
            System.out.println("51. Get Attendance by Employee");
            System.out.println("52. Get Attendance by Date");
            System.out.println("53. Get Monthly Attendance Report");

            System.out.println("54. Add Performance Review");
            System.out.println("55. Get Reviews by Employee");
            System.out.println("56. Update Performance Review");

            System.out.println("57. View Department Change History");
            System.out.println("58. View Supervisor Change History");
            System.out.println("59. View Salary Change History");
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

            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());


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
                case 9:
                    departmentDAO.addDepartment();
                    break;
                case 10:
                    departmentDAO.getDepartmentById();
                    break;
                case 11:
                    departmentDAO.getAllDepartments();
                    break;
                case 12:
                    departmentDAO.updateDepartment();
                    break;
                case 13:
                    departmentDAO.deleteDepartment();
                    break;
                case 14:
                    deptLocationsDAO.addLocation();
                    break;
                case 15:
                    deptLocationsDAO.getLocationsByDeptId();
                    break;
                case 16:
                    deptLocationsDAO.deleteLocation();
                    break;
                case 17:
                    deptLocationsDAO.getAllLocations();
                    break;
                case 18:
                    projectDAO.addProject();
                    break;
                case 19:
                    projectDAO.getProjectById();
                    break;
                case 20:
                    projectDAO.getAllProjects();
                    break;
                case 21:
                    projectDAO.getProjectsByDeptId();
                    break;
                case 22:
                    projectDAO.updateProject();
                    break;
                case 23:
                    projectDAO.deleteProject();
                    break;
                case 24:
                    worksOnDAO.assignEmployeeToProject();
                    break;
                case 25:
                    worksOnDAO.getProjectsByEmployee();
                    break;
                case 26:
                    worksOnDAO.getEmployeesByProject();
                    break;
                case 27:
                    worksOnDAO.removeEmployeeFromProject();
                    break;
                case 28:
                    worksOnDAO.updateHoursForAssignment();
                    break;
                case 29:
                    worksOnDAO.getAllAssignments();
                    break;
                case 30:
                    dependentDAO.addDependent();
                    break;
                case 31:
                    dependentDAO.getDependentsByEmployee();
                    break;
                case 32:
                    dependentDAO.deleteDependent();
                    break;
                case 33:
                    trainingProgramDAO.addTraining();
                    break;
                case 34:
                    trainingProgramDAO.getAllTrainings();
                    break;
                case 35:
                    trainingProgramDAO.getTrainingById();
                    break;
                case 36:
                    trainingProgramDAO.updateTraining();
                    break;
                case 37:
                    trainingProgramDAO.deleteTraining();
                    break;
                case 38:
                    employeeTrainingDAO.enrollEmployeeInTraining();
                    break;
                case 39:
                    employeeTrainingDAO.getTrainingEnrollmentsByEmployee();
                    break;
                case 40:
                    employeeTrainingDAO.getEmployeesInTraining();
                    break;
                case 41:
                    employeeTrainingDAO.removeEmployeeFromTraining();
                    break;
                case 42:
                    assetsDAO.addAsset();
                    break;
                case 43:
                    assetsDAO.assignAssetToEmployee();
                    break;
                case 44:
                    assetsDAO.getAssetsByEmployee();
                    break;
                case 45:
                    assetsDAO.updateAssetStatus();
                    break;
                case 46:
                    assetsDAO.deleteAsset();
                    break;
                case 47:
                    assetMaintenanceDAO.recordMaintenance();
                    break;
                case 48:
                    assetMaintenanceDAO.getMaintenanceByAsset();
                    break;
                case 49:
                    assetMaintenanceDAO.getAllMaintenanceLogs();
                    break;
                case 50:
                    attendanceDAO.markAttendance();
                    break;
                case 51:
                    attendanceDAO.getAttendanceByEmployee();
                    break;
                case 52:
                    attendanceDAO.getAttendanceByDate();
                    break;
                case 53:
                    attendanceDAO.getMonthlyAttendanceReport();
                    break;
                case 54:
                    performanceReviewDAO.addReview();
                    break;
                case 55:
                    performanceReviewDAO.getReviewsByEmployee();
                    break;
                case 56:
                    performanceReviewDAO.updateReview();
                    break;
                case 57:
                    employeeDAO.viewDepartmentChangeHistory();
                    break;
                case 58:
                    employeeDAO.viewSupervisorChangeHistory();
                    break;
                case 59:
                    employeeDAO.viewSalaryChangeHistory();
                    break;
                case 60:
                    departmentDAO.departmentFilterMenu();
                    break;
                case 61:
                    deptLocationsDAO.deptLocationFilterMenu();
                    break;
                case 62:
                    dependentDAO.dependentFilterMenu();
                    break;
                case 63:
                    assetsDAO.assetFilterMenu();
                    break;
                case 64:
                    assetMaintenanceDAO.maintenanceFilterMenu();
                    break;
                case 65:
                    projectDAO.projectFilterMenu();
                    break;
                case 66:
                    worksOnDAO.worksOnFilterMenu();
                    break;
                case 67:
                    trainingProgramDAO.trainingFilterMenu();
                    break;
                case 68:
                    employeeTrainingDAO.trainingEnrollmentFilterMenu();
                    break;
                case 69:
                    attendanceDAO.attendanceFilterMenu();
                    break;
                case 70:
                    performanceReviewDAO.reviewFilterMenu();
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
