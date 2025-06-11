package model;

import java.sql.Date;
import java.math.BigDecimal;

public class SalaryHistory {
    private int record_id;
    private String ssn;
    private BigDecimal previous_salary;
    private BigDecimal new_salary;
    private Date change_date;

    public SalaryHistory(int record_id, String ssn, BigDecimal previous_salary, BigDecimal new_salary, Date change_date) {
        this.record_id = record_id;
        this.ssn = ssn;
        this.previous_salary = previous_salary;
        this.new_salary = new_salary;
        this.change_date = change_date;
    }

    public int getRecord_id() {return record_id;}
    public String getSsn() {return ssn;}
    public BigDecimal getPrevious_salary() {return previous_salary;}
    public BigDecimal getNew_salary() {return new_salary;}
    public Date getChange_date() {return change_date;}

    public void setRecord_id(int record_id) {this.record_id = record_id;}
    public void setSsn(String ssn) {this.ssn = ssn;}
    public void setPrevious_salary(BigDecimal previous_salary) {this.previous_salary = previous_salary;}
    public void setNew_salary(BigDecimal new_salary) {this.new_salary = new_salary;}
    public void setChange_date(Date change_date) {this.change_date = change_date;}
}
