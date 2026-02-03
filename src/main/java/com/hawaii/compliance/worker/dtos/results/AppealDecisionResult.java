package com.hawaii.compliance.worker.dtos.results;

import com.hawaii.compliance.worker.dtos.ActivityResult;

public class AppealDecisionResult extends ActivityResult {
    private boolean appealGranted;
    private String decisionReason;

    public AppealDecisionResult(boolean appealGranted, String decisionReason) {
        super(true, "Appeal decision made", null, null);
        this.appealGranted = appealGranted;
        this.decisionReason = decisionReason;
    }

    public boolean isAppealGranted() {
        return appealGranted;
    }

    public String getDecisionReason() {
        return decisionReason;
    }
}
