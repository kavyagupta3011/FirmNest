package model;

import java.sql.Date;


public class SalaryHistory {
    private int record_id;
    private String ssn;
    private double previous_salary;
    private double new_salary;
    private Date change_date;

    public SalaryHistory(int record_id, String ssn, double previous_salary, double new_salary, Date change_date) {
        this.record_id = record_id;
        this.ssn = ssn;
        this.previous_salary = previous_salary;
        this.new_salary = new_salary;
        this.change_date = change_date;
    }

    public int getRecord_id() {return record_id;}
    public String getSsn() {return ssn;}
    public double getPrevious_salary() {return previous_salary;}
    public double getNew_salary() {return new_salary;}
    public Date getChange_date() {return change_date;}

    public void setRecord_id(int record_id) {this.record_id = record_id;}
    public void setSsn(String ssn) {this.ssn = ssn;}
    public void setPrevious_salary(double previous_salary) {this.previous_salary = previous_salary;}
    public void setNew_salary(double new_salary) {this.new_salary = new_salary;}
    public void setChange_date(Date change_date) {this.change_date = change_date;}
}
