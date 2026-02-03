package com.hawaii.compliance.worker.dtos;

public class Complaint {
    private String complaintId;
    private int priority;
    
    public Complaint() {}
    
    public Complaint(String complaintId, int priority) {
        this.complaintId = complaintId;
        this.priority = priority;
    }
    
    public String getComplaintId() { return complaintId; }
    public void setComplaintId(String complaintId) { this.complaintId = complaintId; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
}
