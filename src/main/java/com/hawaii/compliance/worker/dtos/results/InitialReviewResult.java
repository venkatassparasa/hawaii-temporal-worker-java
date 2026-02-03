package com.hawaii.compliance.worker.dtos.results;

public class InitialReviewResult extends ActivityResult {
    private boolean approved;
    private String reason;

    public InitialReviewResult(boolean approved, String reason) {
        super(approved, reason, null, null);
        this.approved = approved;
        this.reason = reason;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getReason() {
        return reason;
    }
}
