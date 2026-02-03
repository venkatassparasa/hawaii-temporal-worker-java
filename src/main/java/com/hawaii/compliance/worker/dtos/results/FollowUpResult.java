package com.hawaii.compliance.worker.dtos.results;

public class FollowUpResult extends ActivityResult {
    private String followUpId;
    private String followUpAction;

    public FollowUpResult(String followUpId, String followUpAction) {
        super(true, "Follow-up completed", followUpId, null);
        this.followUpId = followUpId;
        this.followUpAction = followUpAction;
    }

    public String getFollowUpId() {
        return followUpId;
    }

    public String getFollowUpAction() {
        return followUpAction;
    }
}
