package model;

import java.math.BigDecimal; //for BigDecimal (ideal for decimal(5,2) in MySQL)
public class WorksOn {
    private String essn;
    private int pno;
    private BigDecimal hours;

    public WorksOn(String essn, int pno, BigDecimal hours){
        this.essn=essn;
        this.pno=pno;
        this.hours=hours;
    }

    public String getEssn() {return essn;}
    public int getPno() {return pno;}
    public BigDecimal getHours() {return hours;}

    public void setEssn(String essn) {this.essn=essn;}
    public void setPno(int pno) {this.pno=pno;}
    public void setHours(BigDecimal hours) {this.hours=hours;}
}
