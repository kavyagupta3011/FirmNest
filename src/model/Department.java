package model;

import java.sql.Date; //for Date mgr_start_date

public class Department {
    private int dnumber;
    private String dname;
    private String mgr_ssn;
    private Date mgr_start_date;

    public Department(int dnumber, String dname, String mgr_ssn, Date mgr_start_date){
        this.dnumber=dnumber;
        this.dname=dname;
        this.mgr_ssn=mgr_ssn;
        this.mgr_start_date=mgr_start_date;
    }

    public int getDnumber() {return dnumber;}
    public String getDname() {return dname;}
    public String getMgr_ssn() {return mgr_ssn;}
    public Date getMgr_start_date() {return mgr_start_date;}

    public void setDnumber(int dnumber) {this.dnumber=dnumber;}
    public void setDname(String dname) {this.dname=dname;}
    public void setMgr_ssn(String mgr_ssn) {this.mgr_ssn=mgr_ssn;}
    public void setMgr_start_date(Date mgr_start_date) {this.mgr_start_date=mgr_start_date;}
}
