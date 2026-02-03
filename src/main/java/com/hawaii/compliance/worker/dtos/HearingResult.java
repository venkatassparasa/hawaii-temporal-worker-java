package com.hawaii.compliance.worker.dtos;

public class HearingResult extends ActivityResult {
    private String hearingId;
    private String hearingDate;
    private String outcome;

    public HearingResult(String hearingId, String hearingDate, String outcome) {
        super(true, "Hearing completed", hearingId, null);
        this.hearingId = hearingId;
        this.hearingDate = hearingDate;
        this.outcome = outcome;
    }

    public String getHearingId() {
        return hearingId;
    }

    public String getHearingDate() {
        return hearingDate;
    }

    public String getOutcome() {
        return outcome;
    }
}
