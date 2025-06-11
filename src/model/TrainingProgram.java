package model;

import java.sql.Date;

public class TrainingProgram {
    private int program_id;
    private String title;
    private Date start_date;
    private Date end_date;
    private String location;

    public TrainingProgram(int program_id, String title, Date start_date, Date end_date, String location) {
        this.program_id = program_id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
    }

    public int getProgram_id() {return program_id;}
    public String getTitle() {return title;}
    public Date getStart_date() {return start_date;}
    public Date getEnd_date() {return end_date;}
    public String getLocation() {return location;}

    public void setProgram_id(int program_id) {this.program_id = program_id;}
    public void setTitle(String title) {this.title = title;}
    public void setStart_date(Date start_date) {this.start_date = start_date;}
    public void setEnd_date(Date end_date) {this.end_date = end_date;}
    public void setLocation(String location) {this.location = location;}
}
