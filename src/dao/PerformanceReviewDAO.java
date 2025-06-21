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

        // -------------------- ADVANCED FILTERS --------------------

    

    private void filterByRatingRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter minimum rating (1-5): ");
            int min = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter maximum rating (1-5): ");
            int max = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM performance_review WHERE rating BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, min);
                pstmt.setInt(2, max);
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Reviews with ratings between " + min + " and " + max + ":");
                while (rs.next()) {
                    System.out.println("Review ID: " + rs.getInt("review_id") +
                                       ", SSN: " + rs.getString("ssn") +
                                       ", Rating: " + rs.getInt("rating") +
                                       ", Date: " + rs.getDate("review_date") +
                                       ", Comments: " + rs.getString("comments"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by rating range.");
            e.printStackTrace();
        }
    }

    private void filterByDateRange() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter end date (YYYY-MM-DD): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());

            String query = "SELECT * FROM performance_review WHERE review_date BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, Date.valueOf(start));
                pstmt.setDate(2, Date.valueOf(end));
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Reviews from " + start + " to " + end + ":");
                while (rs.next()) {
                    System.out.println("Review ID: " + rs.getInt("review_id") +
                                       ", SSN: " + rs.getString("ssn") +
                                       ", Rating: " + rs.getInt("rating") +
                                       ", Comments: " + rs.getString("comments"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering by date range.");
            e.printStackTrace();
        }
    }

    private void topNEmployeesByAverageRating() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter number of top employees to show: ");
            int n = Integer.parseInt(scanner.nextLine());

            String query = """
                SELECT e.ssn, e.fname, e.lname, AVG(pr.rating) as avg_rating
                FROM performance_review pr
                JOIN employee e ON pr.ssn = e.ssn
                GROUP BY e.ssn, e.fname, e.lname
                ORDER BY avg_rating DESC
                LIMIT ?
                """;

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, n);
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Top " + n + " Employees by Average Rating:");
                while (rs.next()) {
                    System.out.println("SSN: " + rs.getString("ssn") +
                                       ", Name: " + rs.getString("fname") + " " + rs.getString("lname") +
                                       ", Average Rating: " + rs.getDouble("avg_rating"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching top employees.");
            e.printStackTrace();
        }
    }

    private void recentReviewsInDepartment() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Department Number: ");
            int dno = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter number of days to look back: ");
            int days = Integer.parseInt(scanner.nextLine());

            String query = """
                SELECT pr.review_id, pr.ssn, e.fname, e.lname, pr.rating, pr.comments, pr.review_date
                FROM performance_review pr
                JOIN employee e ON pr.ssn = e.ssn
                WHERE e.dno = ? AND pr.review_date >= CURRENT_DATE - INTERVAL ? DAY
                ORDER BY pr.review_date DESC
                """;

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, dno);
                pstmt.setInt(2, days);
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Recent Reviews in Department " + dno + " (last " + days + " days):");
                while (rs.next()) {
                    System.out.println("Review ID: " + rs.getInt("review_id") +
                                       ", Name: " + rs.getString("fname") + " " + rs.getString("lname") +
                                       ", Rating: " + rs.getInt("rating") +
                                       ", Date: " + rs.getDate("review_date") +
                                       ", Comments: " + rs.getString("comments"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching department reviews.");
            e.printStackTrace();
        }
    }

    public void reviewFilterMenu() {
        System.out.println("\n--- Performance Review Filter Menu ---");
        System.out.println("1. Filter Reviews by Rating Range");
        System.out.println("2. Filter Reviews by Date Range");
        System.out.println("3. Top N Employees by Average Rating");
        System.out.println("4. Recent Reviews in a Department");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> filterByRatingRange();
            case 2 -> filterByDateRange();
            case 3 -> topNEmployeesByAverageRating();
            case 4 -> recentReviewsInDepartment();
            case 0 -> System.out.println("Exiting filter menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

}

