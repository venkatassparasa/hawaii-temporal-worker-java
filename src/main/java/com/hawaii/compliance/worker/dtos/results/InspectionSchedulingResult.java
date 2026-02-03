package com.hawaii.compliance.worker.dtos.results;

import com.hawaii.compliance.worker.dtos.ActivityResult;

public class InspectionSchedulingResult extends ActivityResult {
    private String inspectionId;
    private String scheduledDate;

    public InspectionSchedulingResult(String inspectionId, String scheduledDate) {
        super(true, "Inspection scheduled", inspectionId, null);
        this.inspectionId = inspectionId;
        this.scheduledDate = scheduledDate;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }
}
