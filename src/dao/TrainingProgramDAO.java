package dao;

import java.sql.*;
import java.util.Scanner;
import db.DBConnection;

public class TrainingProgramDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void addTraining() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            String start = scanner.nextLine();

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            String end = scanner.nextLine();

            System.out.print("Enter Location: ");
            String location = scanner.nextLine();

            String query = "INSERT INTO training_program (title, start_date, end_date, location) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, title);
                pstmt.setDate(2, Date.valueOf(start));
                pstmt.setDate(3, Date.valueOf(end));
                pstmt.setString(4, location);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Training program added successfully.");
                } else {
                    System.out.println("Failed to add training program.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding training program.");
            e.printStackTrace();
        }
    }

    public void getAllTrainings() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM training_program";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    System.out.println("Program ID: " + rs.getInt("program_id"));
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Start Date: " + rs.getDate("start_date"));
                    System.out.println("End Date: " + rs.getDate("end_date"));
                    System.out.println("Location: " + rs.getString("location"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching training programs.");
            e.printStackTrace();
        }
    }

    public void getTrainingById() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Program ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM training_program WHERE program_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Program ID: " + rs.getInt("program_id"));
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Start Date: " + rs.getDate("start_date"));
                    System.out.println("End Date: " + rs.getDate("end_date"));
                    System.out.println("Location: " + rs.getString("location"));
                } else {
                    System.out.println("No training found with ID: " + id);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching training by ID.");
            e.printStackTrace();
        }
    }

    public void updateTraining() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Program ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            String checkQuery = "SELECT * FROM training_program WHERE program_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Training program with ID " + id + " does not exist.");
                    return;
                }
            }

            System.out.print("Enter New Title: ");
            String title = scanner.nextLine();

            System.out.print("Enter New Start Date (YYYY-MM-DD): ");
            String start = scanner.nextLine();

            System.out.print("Enter New End Date (YYYY-MM-DD): ");
            String end = scanner.nextLine();

            System.out.print("Enter New Location: ");
            String location = scanner.nextLine();

            String updateQuery = "UPDATE training_program SET title = ?, start_date = ?, end_date = ?, location = ? WHERE program_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, title);
                pstmt.setDate(2, Date.valueOf(start));
                pstmt.setDate(3, Date.valueOf(end));
                pstmt.setString(4, location);
                pstmt.setInt(5, id);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Training program updated successfully.");
                } else {
                    System.out.println("Failed to update training program.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error updating training program.");
            e.printStackTrace();
        }
    }

    public void deleteTraining() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Program ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            String deleteQuery = "DELETE FROM training_program WHERE program_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, id);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Training program deleted successfully.");
                } else {
                    System.out.println("No training program found with ID: " + id);
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting training program.");
            e.printStackTrace();
        }
    }


    


        // ---------------------- FILTERS ------------------------

    

    private void filterByTitleKeyword() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter keyword in title: ");
            String keyword = scanner.nextLine();

            String query = "SELECT * FROM training_program WHERE LOWER(title) LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                System.out.println("\n--- Programs with title containing \"" + keyword + "\" ---");
                while (rs.next()) {
                    found = true;
                    printTraining(rs);
                }
                if (!found) {
                    System.out.println("No matching training programs found.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by title.");
            e.printStackTrace();
        }
    }

    private void filterByLocation() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter location: ");
            String location = scanner.nextLine();

            String query = "SELECT * FROM training_program WHERE LOWER(location) = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, location.toLowerCase());
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                System.out.println("\n--- Programs at location \"" + location + "\" ---");
                while (rs.next()) {
                    found = true;
                    printTraining(rs);
                }
                if (!found) {
                    System.out.println("No matching training programs found.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by location.");
            e.printStackTrace();
        }
    }

    private void filterByStartDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter start date (from) [YYYY-MM-DD]: ");
            Date from = Date.valueOf(scanner.nextLine());

            System.out.print("Enter start date (to) [YYYY-MM-DD]: ");
            Date to = Date.valueOf(scanner.nextLine());

            String query = "SELECT * FROM training_program WHERE start_date BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, from);
                pstmt.setDate(2, to);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                System.out.println("\n--- Programs starting between " + from + " and " + to + " ---");
                while (rs.next()) {
                    found = true;
                    printTraining(rs);
                }
                if (!found) {
                    System.out.println("No programs in this start date range.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by start date range.");
            e.printStackTrace();
        }
    }

    private void filterByEndDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter end date (from) [YYYY-MM-DD]: ");
            Date from = Date.valueOf(scanner.nextLine());

            System.out.print("Enter end date (to) [YYYY-MM-DD]: ");
            Date to = Date.valueOf(scanner.nextLine());

            String query = "SELECT * FROM training_program WHERE end_date BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, from);
                pstmt.setDate(2, to);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;
                System.out.println("\n--- Programs ending between " + from + " and " + to + " ---");
                while (rs.next()) {
                    found = true;
                    printTraining(rs);
                }
                if (!found) {
                    System.out.println("No programs in this end date range.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by end date range.");
            e.printStackTrace();
        }
    }

    private void printTraining(ResultSet rs) throws SQLException {
        System.out.println("Program ID : " + rs.getInt("program_id"));
        System.out.println("Title      : " + rs.getString("title"));
        System.out.println("Start Date : " + rs.getDate("start_date"));
        System.out.println("End Date   : " + rs.getDate("end_date"));
        System.out.println("Location   : " + rs.getString("location"));
        System.out.println("-----------------------------");
    }

    public void trainingFilterMenu() {
        System.out.println("\n--- Training Program Filter Menu ---");
        System.out.println("1. Filter by Title Keyword");
        System.out.println("2. Filter by Location");
        System.out.println("3. Filter by Start Date Range");
        System.out.println("4. Filter by End Date Range");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByTitleKeyword();
            case 2 -> filterByLocation();
            case 3 -> filterByStartDateRange();
            case 4 -> filterByEndDateRange();
            case 0 -> System.out.println("Exiting filter menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

}

