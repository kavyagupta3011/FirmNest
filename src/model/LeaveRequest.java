package model;

import java.time.LocalDate;

public class LeaveRequest {
    private int requestId;
    private String ssn;
    private String type; // "Leave" or "WorkFromHome"
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status; // "Pending", "Approved", or "Rejected"
    private LocalDate appliedOn;

    // Constructors
    public LeaveRequest() {
    }

    public LeaveRequest(int requestId, String ssn, String type, LocalDate startDate,
                        LocalDate endDate, String reason, String status, LocalDate appliedOn) {
        this.requestId = requestId;
        this.ssn = ssn;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.appliedOn = appliedOn;
    }

    // Getters
    public int getRequestId() {
        return requestId;
    }

    public String getSsn() {
        return ssn;
    }

    public String getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getAppliedOn() {
        return appliedOn;
    }

    // Setters
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAppliedOn(LocalDate appliedOn) {
        this.appliedOn = appliedOn;
    }
}

