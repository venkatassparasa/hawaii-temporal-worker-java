package com.hawaii.compliance.worker.dtos;

public class Appeal {
    private String appealId;
    
    public Appeal() {}
    
    public Appeal(String appealId) {
        this.appealId = appealId;
    }
    
    public String getAppealId() { return appealId; }
    public void setAppealId(String appealId) { this.appealId = appealId; }
}
