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

// Specific Result Classes
class InitialReviewResult extends ActivityResult {
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

class ZoningResult extends ActivityResult {
    private boolean compliant;
    private String violationType;

    public ZoningResult(boolean compliant, String violationType) {
        super(compliant, compliant ? "Zoning compliant" : "Zoning violation: " + violationType, null, null);
        this.compliant = compliant;
        this.violationType = violationType;
    }

    public boolean isCompliant() {
        return compliant;
    }

    public String getViolationType() {
        return violationType;
    }
}

class NCUCResult extends ActivityResult {
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

class RegistrationResult extends ActivityResult {
    private String registrationId;
    private String registrationDate;

    public RegistrationResult(String registrationId, String registrationDate) {
        super(true, "Registration completed", registrationId, null);
        this.registrationId = registrationId;
        this.registrationDate = registrationDate;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
}

class NoticeResult extends ActivityResult {
    private String noticeId;
    private String noticeType;

    public NoticeResult(String noticeId, String noticeType) {
        super(true, "Notice sent", noticeId, null);
        this.noticeId = noticeId;
        this.noticeType = noticeType;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public String getNoticeType() {
        return noticeType;
    }
}

class AppealDocumentResult extends ActivityResult {
    private String documentId;
    private String documentType;

    public AppealDocumentResult(String documentId, String documentType) {
        super(true, "Document processed", documentId, null);
        this.documentId = documentId;
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocumentType() {
        return documentType;
    }
}

class LegalReviewResult extends ActivityResult {
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

class HearingResult extends ActivityResult {
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

class AppealDecisionResult extends ActivityResult {
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

class InspectionSchedulingResult extends ActivityResult {
    private String inspectionId;
    private String scheduledDate;

    public InspectionSchedulingResult(String inspectionId, String scheduledDate) {
        super(true, "Inspection scheduled", inspectionId, null);
        this.inspectionId = inspectionId;
        this.scheduledDate = scheduledDate;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }
}

class OnSiteInspectionResult extends ActivityResult {
    private boolean passed;
    private String inspectionNotes;

    public OnSiteInspectionResult(boolean passed, String inspectionNotes) {
        super(true, "On-site inspection completed", null, null);
        this.passed = passed;
        this.inspectionNotes = inspectionNotes;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getInspectionNotes() {
        return inspectionNotes;
    }
}

class InspectionReportResult extends ActivityResult {
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

class FollowUpResult extends ActivityResult {
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

class ComplianceResult extends ActivityResult {
    private boolean compliant;
    private String complianceStatus;

    public ComplianceResult(boolean compliant, String complianceStatus) {
        super(true, "Compliance check completed", null, null);
        this.compliant = compliant;
        this.complianceStatus = complianceStatus;
    }

    public boolean isCompliant() {
        return compliant;
    }

    public String getComplianceStatus() {
        return complianceStatus;
    }
}
