package model;

public class EmployeeTraining {
    private String ssn;
    private int program_id;
    private String status;

    public EmployeeTraining(String ssn, int program_id, String status) {
        this.ssn = ssn;
        this.program_id = program_id;
        this.status = status;
    }

    public String getSsn() {return ssn;}
    public int getProgram_id() {return program_id;}
    public String getStatus() {return status;}

    public void setSsn(String ssn) {this.ssn = ssn;}
    public void setProgram_id(int program_id) {this.program_id = program_id;}
    public void setStatus(String status) {this.status = status;}
}
