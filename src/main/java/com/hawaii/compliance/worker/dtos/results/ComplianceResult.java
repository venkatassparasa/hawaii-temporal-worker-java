package com.hawaii.compliance.worker.dtos.results;

public class ComplianceResult extends ActivityResult {
    private boolean compliant;
    private String complianceStatus;

    public ComplianceResult(boolean compliant, String complianceStatus) {
        super(true, "Compliance check completed", null, null);
        this.compliant = compliant;
        this.complianceStatus = complianceStatus;
    }

    public boolean isCompliant() {
        return compliant;
    }

    public String getComplianceStatus() {
        return complianceStatus;
    }
}
