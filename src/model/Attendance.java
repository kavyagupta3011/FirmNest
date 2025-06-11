package model;

import java.sql.Date;

public class Attendance {
    private int attendance_id;
    private String ssn;
    private Date date;
    private String status;

    public Attendance(int attendance_id, String ssn, Date date, String status){
        this.attendance_id=attendance_id;
        this.ssn=ssn;
        this.date=date;
        this.status=status;
    }

    public int getAttendance_id() {return attendance_id;}
    public String getSsn() {return ssn;}
    public Date getDate() {return date;}
    public String getStatus() {return status;}
    
    public void setAttendance_id(int attendance_id) {this.attendance_id = attendance_id;}
    public void setSsn(String ssn) {this.ssn = ssn;}
    public void setDate(Date date) {this.date = date;}
    public void setStatus(String status) {this.status = status;}
}
