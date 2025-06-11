package model;

import java.sql.Date;

public class PerformanceReview {
    private int review_id;
    private String ssn;
    private Date review_date;
    private int rating;
    private String comments;


    public PerformanceReview(int review_id, String ssn, Date review_date, int rating, String comments) {
        this.review_id = review_id;
        this.ssn = ssn;
        this.review_date = review_date;
        this.rating = rating;
        this.comments = comments;
    }

    public int getReview_id() {return review_id;}
    public String getSsn() {return ssn;}
    public Date getReview_date() {return review_date;}
    public int getRating() {return rating;}
    public String getComments() {return comments;}

    public void setReview_id(int review_id) {this.review_id = review_id;}
    public void setSsn(String ssn) {this.ssn = ssn;}
    public void setReview_date(Date review_date) {this.review_date = review_date;}
    public void setRating(int rating) {this.rating = rating;}
    public void setComments(String comments) {this.comments = comments;}
}
