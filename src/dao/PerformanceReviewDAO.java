package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import db.DBConnection;

public class PerformanceReviewDAO {
    Scanner scanner = new Scanner(System.in);

    public void addReview() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter SSN of employee being reviewed: ");
            String ssn = scanner.nextLine();

            // Check if employee exists
            String checkEmp = "SELECT * FROM employee WHERE ssn = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkEmp)) {
                checkStmt.setString(1, ssn);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("No employee found with SSN: " + ssn);
                    return;
                }
            }

            System.out.print("Enter review date (YYYY-MM-DD): ");
            LocalDate reviewDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter rating (1-5): ");
            int rating = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter comments: ");
            String comments = scanner.nextLine();

            String insertQuery = "INSERT INTO performance_review (ssn, review_date, rating, comments) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, ssn);
                pstmt.setDate(2, Date.valueOf(reviewDate));
                pstmt.setInt(3, rating);
                pstmt.setString(4, comments);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Performance review added successfully.");
                } else {
                    System.out.println("Failed to add review.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error adding review.");
            e.printStackTrace();
        }
    }

    public void getReviewsByEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter SSN of employee: ");
            String ssn = scanner.nextLine();

            String query = "SELECT * FROM performance_review WHERE ssn = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, ssn);
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Performance Reviews for SSN: " + ssn);
                while (rs.next()) {
                    System.out.println("Review ID: " + rs.getInt("review_id"));
                    System.out.println("Date: " + rs.getDate("review_date"));
                    System.out.println("Rating: " + rs.getInt("rating"));
                    System.out.println("Comments: " + rs.getString("comments"));
                    System.out.println("-------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("Error fetching reviews.");
            e.printStackTrace();
        }
    }

    

    public void updateReview() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Review ID to update: ");
            int reviewId = Integer.parseInt(scanner.nextLine());

            // Check if review exists
            String checkQuery = "SELECT * FROM performance_review WHERE review_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, reviewId);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("No review found with ID: " + reviewId);
                    return;
                }
            }

            System.out.print("Enter new rating (1-5): ");
            int newRating = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter new comments: ");
            String newComments = scanner.nextLine();

            String updateQuery = "UPDATE performance_review SET rating = ?, comments = ? WHERE review_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setInt(1, newRating);
                pstmt.setString(2, newComments);
                pstmt.setInt(3, reviewId);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Review updated successfully.");
                } else {
                    System.out.println("Failed to update review.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error updating review.");
            e.printStackTrace();
        }
    }
}

