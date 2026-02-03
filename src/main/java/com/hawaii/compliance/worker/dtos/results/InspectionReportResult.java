package com.hawaii.compliance.worker.dtos.results;

public class InspectionReportResult extends ActivityResult {
    private String reportId;
    private String reportUrl;

    public InspectionReportResult(String reportId, String reportUrl) {
        super(true, "Inspection report generated", reportId, null);
        this.reportId = reportId;
        this.reportUrl = reportUrl;
    }

    public String getReportId() {
        return reportId;
    }

    public String getReportUrl() {
        return reportUrl;
    }
}
