package com.hawaii.compliance.worker.dtos;

public class ZoningResult extends ActivityResult {
    private boolean compliant;
    private String violationType;

    public ZoningResult(boolean compliant, String violationType) {
        super(compliant, compliant ? "Zoning compliant" : "Zoning violation: " + violationType, null, null);
        this.compliant = compliant;
        this.violationType = violationType;
    }

    public boolean isCompliant() {
        return compliant;
    }

    public String getViolationType() {
        return violationType;
    }
}
