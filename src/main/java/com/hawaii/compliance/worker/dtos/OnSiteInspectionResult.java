package com.hawaii.compliance.worker.dtos;

public class OnSiteInspectionResult extends ActivityResult {
    private boolean passed;
    private String inspectionNotes;

    public OnSiteInspectionResult(boolean passed, String inspectionNotes) {
        super(true, "On-site inspection completed", null, null);
        this.passed = passed;
        this.inspectionNotes = inspectionNotes;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getInspectionNotes() {
        return inspectionNotes;
    }
}
