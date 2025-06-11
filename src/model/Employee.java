package model;

import java.sql.Date; //for Date bdate
import java.math.BigDecimal; //for BigDecimal (ideal for Decimal(10,2) in MySQL)

public class Employee {
    private String ssn;
    private String fname;
    private String minit;
    private String lname;
    private Date bdate;
    private String address;
    private String sex;
    private BigDecimal salary;
    private String super_ssn;
    private Integer dno; //we use Integer when the value is nullable

    public Employee(String ssn, String fname, String minit, String lname, Date bdate, String address, String sex, BigDecimal salary, String super_ssn, Integer dno){
        this.ssn=ssn;
        this.fname=fname;
        this.minit=minit;
        this.lname=lname;
        this.bdate=bdate;
        this.address=address;
        this.sex=sex;
        this.salary=salary;
        this.super_ssn=super_ssn;
        this.dno=dno;
    }

    public String getSsn() {return ssn;}
    public String getFname() {return fname;}
    public String getMinit() {return minit;}
    public String getLname() {return lname;}
    public Date getBdate() {return bdate;}
    public String getAddress() {return address;}
    public String getSex() {return sex;}
    public BigDecimal getSalary() {return salary;}
    public String getSuper_ssn() {return super_ssn;}
    public Integer getDno() {return dno;}

    public void setSsn(String ssn) {this.ssn=ssn;}
    public void setFname(String fname) {this.fname=fname;}
    public void setMinit(String minit) {this.minit=minit;}
    public void setLname(String lname) {this.lname=lname;}
    public void setBdate(Date bdate) {this.bdate=bdate;}
    public void setAddress(String address) {this.address=address;}
    public void setSex(String sex) {this.sex=sex;}
    public void setSalary(BigDecimal salary) {this.salary=salary;}
    public void setSuper_ssn(String super_ssn) {this.super_ssn=super_ssn;}
    public void setDno(Integer dno) {this.dno=dno;}
}
