package com.hawaii.compliance.worker.dtos;

public class AnnualInspection {
    private String inspectionId;
    private String propertyId;
    
    public AnnualInspection() {}
    
    public AnnualInspection(String inspectionId) {
        this.inspectionId = inspectionId;
    }
    
    public AnnualInspection(String inspectionId, String propertyId) {
        this.inspectionId = inspectionId;
        this.propertyId = propertyId;
    }
    
    public String getInspectionId() { return inspectionId; }
    public void setInspectionId(String inspectionId) { this.inspectionId = inspectionId; }
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }
}
