package com.hawaii.compliance.worker.dtos;

public class TVRApplication {
    private String applicationId;
    private String propertyId;
    private String applicantName;
    private boolean requiresNCUC;
    
    // Constructors
    public TVRApplication() {}
    
    public TVRApplication(String applicationId, String propertyId, String applicantName, boolean requiresNCUC) {
        this.applicationId = applicationId;
        this.propertyId = propertyId;
        this.applicantName = applicantName;
        this.requiresNCUC = requiresNCUC;
    }
    
    // Getters and setters
    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public boolean isRequiresNCUC() { return requiresNCUC; }
    public void setRequiresNCUC(boolean requiresNCUC) { this.requiresNCUC = requiresNCUC; }
}
