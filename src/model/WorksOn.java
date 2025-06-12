package model;

public class WorksOn {
    private String essn;
    private int pno;
    private double hours;

    public WorksOn(String essn, int pno, double hours){
        this.essn=essn;
        this.pno=pno;
        this.hours=hours;
    }

    public String getEssn() {return essn;}
    public int getPno() {return pno;}
    public double getHours() {return hours;}

    public void setEssn(String essn) {this.essn=essn;}
    public void setPno(int pno) {this.pno=pno;}
    public void setHours(double hours) {this.hours=hours;}
}
