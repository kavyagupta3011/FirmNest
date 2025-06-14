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
}

