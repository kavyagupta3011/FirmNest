package model;

import java.sql.Date;

public class JobHistory {
    private int job_id;
    private String ssn;
    private Integer previous_dno;
    private Integer new_dno;
    private Date change_date;

    public JobHistory(int job_id, String ssn, Integer previous_dno, Integer new_dno, Date change_date){
        this.job_id=job_id;
        this.ssn=ssn;
        this.previous_dno=previous_dno;
        this.new_dno=new_dno;
        this.change_date=change_date;
    }

    public int getJob_id() {return job_id;}
    public String getSsn() {return ssn;}
    public Integer getPrevious_dno() {return previous_dno;}
    public Integer getNew_dno() {return new_dno;}
    public Date getChange_date() {return change_date;}

    public void setJob_id(int job_id) {this.job_id=job_id;}
    public void setSsn(String ssn) {this.ssn=ssn;}
    public void setPrevious_dno(Integer previous_dno) {this.previous_dno=previous_dno;}
    public void setNew_dno(Integer new_dno) {this.new_dno=new_dno;}
    public void setChange_date(Date change_date) {this.change_date=change_date;}

}
