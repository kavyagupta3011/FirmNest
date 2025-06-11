package model;

import java.sql.Date;

public class AssetMaintenance {
    private int maintenance_id;
    private int asset_id;
    private Date maintenance_date;
    private String details;

    public AssetMaintenance(int maintenance_id, int asset_id, Date maintenance_date, String details) {
        this.maintenance_id = maintenance_id;
        this.asset_id = asset_id;
        this.maintenance_date = maintenance_date;
        this.details = details;
    }

    public int getMaintenance_id() {return maintenance_id;}
    public int getAsset_id() {return asset_id;}
    public Date getMaintenance_date() {return maintenance_date;}
    public String getDetails() {return details;}

    public void setMaintenance_id(int maintenance_id) {this.maintenance_id = maintenance_id;}
    public void setAsset_id(int asset_id) {this.asset_id = asset_id;}
    public void setMaintenance_date(Date maintenance_date) {this.maintenance_date = maintenance_date;}
    public void setDetails(String details) {this.details = details;}
}
