package model;

import java.sql.Date;

public class Assets {
    private int asset_id;
    private String asset_name;
    private String assigned_to;
    private Date assigned_date;
    private String status;

    public Assets(int asset_id, String asset_name, String assigned_to, Date assigned_date, String status) {
        this.asset_id = asset_id;
        this.asset_name = asset_name;
        this.assigned_to = assigned_to;
        this.assigned_date = assigned_date;
        this.status = status;
    }

    public int getAsset_id() {return asset_id;}
    public String getAsset_name() {return asset_name;}
    public String getAssigned_to() {return assigned_to;}
    public Date getAssigned_date() {return assigned_date;}
    public String getStatus() {return status;}

    public void setAsset_id(int asset_id) {this.asset_id = asset_id;}
    public void setAsset_name(String asset_name) {this.asset_name = asset_name;}
    public void setAssigned_to(String assigned_to) {this.assigned_to = assigned_to;}
    public void setAssigned_date(Date assigned_date) {this.assigned_date = assigned_date;}
    public void setStatus(String status) {this.status = status;}

}
