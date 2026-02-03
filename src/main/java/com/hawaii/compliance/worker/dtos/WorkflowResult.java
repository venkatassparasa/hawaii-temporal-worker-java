package com.hawaii.compliance.worker.dtos;

public class WorkflowResult {
    private String status;
    private String message;
    private String resultId;
    private Object data;

    public WorkflowResult() {}

    public WorkflowResult(String status, String message, String resultId, Object data) {
        this.status = status;
        this.message = message;
        this.resultId = resultId;
        this.data = data;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
