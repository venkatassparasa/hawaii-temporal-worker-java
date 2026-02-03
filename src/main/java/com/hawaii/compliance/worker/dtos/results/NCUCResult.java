package com.hawaii.compliance.worker.dtos.results;

import com.hawaii.compliance.worker.dtos.ActivityResult;

public class NCUCResult extends ActivityResult {
    private boolean approved;
    private String ncucNumber;

    public NCUCResult(boolean approved, String ncucNumber) {
        super(approved, approved ? "NCUC approved" : "NCUC denied", null, null);
        this.approved = approved;
        this.ncucNumber = ncucNumber;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getNcucNumber() {
        return ncucNumber;
    }
}
