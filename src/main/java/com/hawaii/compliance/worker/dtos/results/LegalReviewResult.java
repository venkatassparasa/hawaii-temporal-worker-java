package com.hawaii.compliance.worker.dtos.results;

public class LegalReviewResult extends ActivityResult {
    private boolean favorable;
    private String legalOpinion;

    public LegalReviewResult(boolean favorable, String legalOpinion) {
        super(true, "Legal review completed", null, null);
        this.favorable = favorable;
        this.legalOpinion = legalOpinion;
    }

    public boolean isFavorable() {
        return favorable;
    }

    public String getLegalOpinion() {
        return legalOpinion;
    }
}
