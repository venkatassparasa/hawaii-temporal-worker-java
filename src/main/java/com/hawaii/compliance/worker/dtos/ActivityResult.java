package com.hawaii.compliance.worker.dtos;

public class ActivityResult {
    private boolean success;
    private String message;
    private String resultId;
    private Object data;

    public ActivityResult() {}

    public ActivityResult(boolean success, String message, String resultId, Object data) {
        this.success = success;
        this.message = message;
        this.resultId = resultId;
        this.data = data;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

