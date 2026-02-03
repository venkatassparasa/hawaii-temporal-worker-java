package com.hawaii.compliance.worker.workflows;

import io.temporal.workflow.*;
import com.hawaii.compliance.worker.activities.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@WorkflowInterface
public interface ComplianceWorkflows {

    @WorkflowMethod
    WorkflowResult TVRRegistrationWorkflow(TVRApplication application);

    @WorkflowMethod
    WorkflowResult ComplaintInvestigationWorkflow(Complaint complaint);

    @WorkflowMethod
    WorkflowResult ViolationAppealWorkflow(Appeal appeal);

    @WorkflowMethod
    WorkflowResult AnnualInspectionWorkflow(AnnualInspection inspection);
}

// Workflow Implementation
public class ComplianceWorkflowsImpl implements ComplianceWorkflows {

    private final ComplianceActivities activities = Workflow.newActivityStub(
        ComplianceActivities.class,
        ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofMinutes(10))
            .build()
    );

    @Override
    public WorkflowResult TVRRegistrationWorkflow(TVRApplication application) {
        Workflow.getLogger().info("Starting TVR Registration workflow for application: {}", application.getApplicationId());

        try {
            // Step 1: Initial Review (2 hours)
            Workflow.getLogger().info("Step 1: Performing initial review...");
            Workflow.sleep(Duration.ofHours(2));
            InitialReviewResult initialReview = activities.performInitialReview(application);
            
            if (!initialReview.isApproved()) {
                return new WorkflowResult("rejected", initialReview.getReason(), null, null);
            }

            // Step 2: Zoning Verification (1 day)
            Workflow.getLogger().info("Step 2: Verifying zoning...");
            Workflow.sleep(Duration.ofDays(1));
            ZoningResult zoningResult = activities.verifyZoning(application.getPropertyId());
            
            if (!zoningResult.isCompliant()) {
                return new WorkflowResult("rejected", "Zoning violation detected", null, null);
            }

            // Step 3: NCUC Processing (if required, 3-7 days)
            if (application.isRequiresNCUC()) {
                Workflow.getLogger().info("Step 3: Processing NCUC application...");
                Workflow.sleep(Duration.ofDays(3));
                NCUCResult ncucResult = activities.processNCUC(application);
                
                if (!ncucResult.isApproved()) {
                    return new WorkflowResult("rejected", "NCUC denied", null, null);
                }
            }

            // Step 4: Inspection Scheduling (2-3 days)
            Workflow.getLogger().info("Step 4: Scheduling inspection...");
            Workflow.sleep(Duration.ofDays(2));
            InspectionResult inspection = activities.scheduleInspection(application);

            // Step 5: Final Approval (1-2 days)
            Workflow.getLogger().info("Step 5: Final approval process...");
            Workflow.sleep(Duration.ofDays(1));
            RegistrationResult finalApproval = activities.finalizeRegistration(application, inspection);
            
            return new WorkflowResult("approved", null, finalApproval.getRegistrationNumber(), Workflow.now().toString());

        } catch (Exception e) {
            Workflow.getLogger().error("TVR Registration workflow failed", e);
            return new WorkflowResult("rejected", "Workflow error occurred", null, null);
        }
    }

    @Override
    public WorkflowResult ComplaintInvestigationWorkflow(Complaint complaint) {
        Workflow.getLogger().info("Starting Complaint Investigation workflow for complaint: {}", complaint.getComplaintId());

        try {
            // Step 1: Initial Assessment (1 day)
            Workflow.getLogger().info("Step 1: Initial assessment...");
            Workflow.sleep(Duration.ofDays(1));
            AssessmentResult assessment = activities.performInitialAssessment(complaint);
            
            if (!assessment.isRequiresInvestigation()) {
                return new WorkflowResult("compliant", null, null, Workflow.now().toString());
            }

            // Step 2: Evidence Collection (3-7 days)
            Workflow.getLogger().info("Step 2: Collecting evidence...");
            Workflow.sleep(Duration.ofDays(3));
            EvidenceResult evidence = activities.collectEvidence(complaint);

            // Step 3: Site Visit (2-3 days)
            Workflow.getLogger().info("Step 3: Conducting site visit...");
            Workflow.sleep(Duration.ofDays(2));
            SiteVisitResult siteVisit = activities.conductSiteVisit(complaint, evidence);

            // Step 4: Investigation Report (2-3 days)
            Workflow.getLogger().info("Step 4: Generating investigation report...");
            Workflow.sleep(Duration.ofDays(2));
            InvestigationReportResult report = activities.generateInvestigationReport(complaint, evidence, siteVisit);

            // Step 5: Violation Determination (1-2 days)
            Workflow.getLogger().info("Step 5: Determining violations...");
            Workflow.sleep(Duration.ofDays(1));
            ViolationDeterminationResult determination = activities.determineViolations(report);

            // Step 6: Notice Generation (1 day)
            Workflow.getLogger().info("Step 6: Generating notice...");
            Workflow.sleep(Duration.ofDays(1));
            NoticeResult notice = activities.generateNotice(complaint, determination);

            String status = determination.isHasViolations() ? "violated" : "compliant";
            return new WorkflowResult(status, null, notice.getNoticeId(), Workflow.now().toString());

        } catch (Exception e) {
            Workflow.getLogger().error("Complaint Investigation workflow failed", e);
            return new WorkflowResult("pending", "Workflow error occurred", null, null);
        }
    }

    @Override
    public WorkflowResult ViolationAppealWorkflow(Appeal appeal) {
        Workflow.getLogger().info("Starting Violation Appeal workflow for appeal: {}", appeal.getAppealId());

        try {
            // Step 1: Document Review (3-5 days)
            Workflow.getLogger().info("Step 1: Reviewing documents...");
            Workflow.sleep(Duration.ofDays(3));
            AppealDocumentResult documentReview = activities.reviewAppealDocuments(appeal);

            if (!documentReview.isValid()) {
                return new WorkflowResult("upheld", "Invalid appeal documentation", null, null);
            }

            // Step 2: Legal Review (1-2 weeks)
            Workflow.getLogger().info("Step 2: Legal review...");
            Workflow.sleep(Duration.ofDays(7));
            LegalReviewResult legalReview = activities.performLegalReview(appeal, documentReview);

            // Step 3: Hearing Scheduling (1 week)
            Workflow.getLogger().info("Step 3: Scheduling hearing...");
            Workflow.sleep(Duration.ofDays(7));
            HearingResult hearing = activities.scheduleHearing(appeal, legalReview);

            // Step 4: Decision Making (3-5 days)
            Workflow.getLogger().info("Step 4: Making decision...");
            Workflow.sleep(Duration.ofDays(3));
            AppealDecisionResult decision = activities.makeAppealDecision(appeal, hearing);

            // Step 5: Notification (1 day)
            Workflow.getLogger().info("Step 5: Notifying parties...");
            Workflow.sleep(Duration.ofDays(1));
            activities.notifyAppealDecision(appeal, decision);

            String status = decision.isUpheld() ? "upheld" : "overturned";
            return new WorkflowResult(status, decision.getReasoning(), null, Workflow.now().toString());

        } catch (Exception e) {
            Workflow.getLogger().error("Violation Appeal workflow failed", e);
            return new WorkflowResult("pending", "Workflow error occurred", null, null);
        }
    }

    @Override
    public WorkflowResult AnnualInspectionWorkflow(AnnualInspection inspection) {
        Workflow.getLogger().info("Starting Annual Inspection workflow for inspection: {}", inspection.getInspectionId());

        try {
            // Step 1: Scheduling (2-3 days)
            Workflow.getLogger().info("Step 1: Scheduling inspection...");
            Workflow.sleep(Duration.ofDays(2));
            InspectionSchedulingResult scheduling = activities.scheduleInspectionDate(inspection);

            if (!scheduling.isSuccess()) {
                return new WorkflowResult("rescheduled", "Scheduling conflict - needs rescheduling", null, null);
            }

            // Step 2: On-site Inspection (1 day)
            Workflow.getLogger().info("Step 2: Conducting on-site inspection...");
            Workflow.sleep(Duration.ofDays(1));
            OnSiteInspectionResult onSiteInspection = activities.conductOnSiteInspection(inspection);

            // Step 3: Report Generation (1-2 days)
            Workflow.getLogger().info("Step 3: Generating inspection report...");
            Workflow.sleep(Duration.ofDays(1));
            InspectionReportResult report = activities.generateInspectionReport(inspection, onSiteInspection);

            // Step 4: Follow-up Required (if needed)
            if (report.isRequiresFollowUp()) {
                Workflow.getLogger().info("Step 4: Scheduling follow-up...");
                Workflow.sleep(Duration.ofDays(3));
                FollowUpResult followUp = activities.scheduleFollowUp(inspection, report);
                
                String status = followUp.isCompliant() ? "compliant" : "violated";
                return new WorkflowResult(status, report.getSummary(), null, Workflow.now().toString());
            }

            // Step 5: Compliance Verification (1 day)
            Workflow.getLogger().info("Step 5: Verifying compliance...");
            Workflow.sleep(Duration.ofDays(1));
            ComplianceResult compliance = activities.verifyCompliance(report);

            String status = compliance.isCompliant() ? "compliant" : "violated";
            return new WorkflowResult(status, report.getSummary(), null, Workflow.now().toString());

        } catch (Exception e) {
            Workflow.getLogger().error("Annual Inspection workflow failed", e);
            return new WorkflowResult("rescheduled", "Workflow error occurred", null, null);
        }
    }
}

// Workflow Result Class
class WorkflowResult {
    private String status;
    private String reason;
    private String registrationId;
    private String completedAt;

    public WorkflowResult(String status, String reason, String registrationId, String completedAt) {
        this.status = status;
        this.reason = reason;
        this.registrationId = registrationId;
        this.completedAt = completedAt;
    }

    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public String getRegistrationId() { return registrationId; }
    public void setRegistrationId(String registrationId) { this.registrationId = registrationId; }
    
    public String getCompletedAt() { return completedAt; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }
}
