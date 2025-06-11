package model;

public class Project {
    private int pnumber;
    private String pname;
    private String plocation;
    private Integer dnum;

    public Project(int pnumber, String pname, String plocation, Integer dnum){
        this.pnumber=pnumber;
        this.pname=pname;
        this.plocation=plocation;
        this.dnum=dnum;
    }

    public int getPnumber() {return pnumber;}
    public String getPname() {return pname;}
    public String getPlocation() {return plocation;}
    public Integer getDnum() {return dnum;}
}
