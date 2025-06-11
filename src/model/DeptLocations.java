package model;

public class DeptLocations {
    private int dnumber;
    private String dlocation;

    public DeptLocations(int dnumber, String dlocation){
        this.dnumber=dnumber;
        this.dlocation=dlocation;
    }

    public int getDnumber() {return dnumber;}
    public String getDlocation() {return dlocation;}

    public void setDnumber(int dnumber) {this.dnumber=dnumber;}
    public void setDlocation(String dlocation) {this.dlocation=dlocation;}
}
