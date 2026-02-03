package com.hawaii.compliance.worker.workflows.impl;

import com.hawaii.compliance.worker.activities.ComplianceActivities;
import com.hawaii.compliance.worker.dtos.*;
import com.hawaii.compliance.worker.workflows.ComplianceWorkflows;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

// Workflow Implementation
public class ComplianceWorkflowsImpl implements ComplianceWorkflows {

    private final static Logger logger = Workflow.getLogger(ComplianceWorkflowsImpl.class);

    private final ComplianceActivities activities = Workflow.newActivityStub(
            ComplianceActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(10))
                    .build()
    );

    @Override
    public WorkflowResult TVRRegistrationWorkflow(TVRApplication application) {
        logger.info("Starting TVR Registration workflow for application: {}", application.getApplicationId());

        try {
            // Step 1: Initial Review (2 hours)
            logger.info("Step 1: Performing initial review...");
            Workflow.sleep(Duration.ofHours(2));
            InitialReviewResult initialReview = activities.performInitialReview(application);

            if (!initialReview.isApproved()) {
                return new WorkflowResult("rejected", initialReview.getReason(), null, null);
            }

            // Step 2: Zoning Verification (1 day)
            logger.info("Step 2: Verifying zoning...");
            Workflow.sleep(Duration.ofDays(1));
            ZoningResult zoningResult = activities.verifyZoning(application.getPropertyId());

            if (!zoningResult.isCompliant()) {
                return new WorkflowResult("rejected", "Zoning violation detected", null, null);
            }

            // Step 3: NCUC Processing (if required)
            if (application.isRequiresNCUC()) {
                logger.info("Step 3: Processing NCUC...");
                Workflow.sleep(Duration.ofDays(3));
                NCUCResult ncucResult = activities.processNCUC(application.getApplicationId());

                if (!ncucResult.isApproved()) {
                    return new WorkflowResult("rejected", "NCUC approval denied", null, null);
                }
            }

            // Step 4: Final Registration
            logger.info("Step 4: Finalizing registration...");
            Workflow.sleep(Duration.ofHours(1));
            RegistrationResult registrationResult = activities.finalizeRegistration(application);

            return new WorkflowResult("completed", "TVR registration completed successfully",
                    registrationResult.getRegistrationId(), null);

        } catch (Exception e) {
            logger.error("Error in TVR Registration workflow", e);
            return new WorkflowResult("failed", e.getMessage(), null, null);
        }
    }

    @Override
    public WorkflowResult ComplaintInvestigationWorkflow(Complaint complaint) {
        logger.info("Starting Complaint Investigation workflow for complaint: {}", complaint.getComplaintId());

        try {
            // Step 1: Initial Assessment (4 hours)
            logger.info("Step 1: Initial assessment...");
            Workflow.sleep(Duration.ofHours(4));
            activities.performInitialAssessment(complaint);

            // Step 2: Evidence Collection (2 days)
            logger.info("Step 2: Collecting evidence...");
            Workflow.sleep(Duration.ofDays(2));
            activities.collectEvidence(complaint);

            // Step 3: Site Visit (1 day)
            logger.info("Step 3: Conducting site visit...");
            Workflow.sleep(Duration.ofDays(1));
            activities.conductSiteVisit(complaint);

            // Step 4: Investigation Report (1 day)
            logger.info("Step 4: Preparing investigation report...");
            Workflow.sleep(Duration.ofDays(1));
            activities.generateInvestigationReport(complaint);

            // Step 5: Decision (4 hours)
            logger.info("Step 5: Making decision...");
            Workflow.sleep(Duration.ofHours(4));
            activities.determineViolations(complaint);

            // Step 6: Generate Notice (if needed)
            NoticeResult noticeResult = activities.generateNotice(complaint);

            return new WorkflowResult("completed", "Complaint investigation completed",
                    noticeResult.getNoticeId(), null);

        } catch (Exception e) {
            logger.error("Error in Complaint Investigation workflow", e);
            return new WorkflowResult("failed", e.getMessage(), null, null);
        }
    }

    @Override
    public WorkflowResult ViolationAppealWorkflow(Appeal appeal) {
        logger.info("Starting Violation Appeal workflow for appeal: {}", appeal.getAppealId());

        try {
            // Step 1: Document Review (1 day)
            logger.info("Step 1: Reviewing appeal documents...");
            Workflow.sleep(Duration.ofDays(1));
            activities.reviewAppealDocuments(appeal);

            // Step 2: Legal Review (2 days)
            logger.info("Step 2: Legal review...");
            Workflow.sleep(Duration.ofDays(2));
            activities.conductLegalReview(appeal);

            // Step 3: Hearing (3 days)
            logger.info("Step 3: Scheduling hearing...");
            Workflow.sleep(Duration.ofDays(3));
            HearingResult hearingResult = activities.scheduleHearing(appeal);

            // Step 4: Decision (1 day)
            logger.info("Step 4: Making appeal decision...");
            Workflow.sleep(Duration.ofDays(1));
            AppealDecisionResult decisionResult = activities.makeAppealDecision(appeal);

            return new WorkflowResult("completed", "Violation appeal processed",
                    hearingResult.getHearingId(), decisionResult);

        } catch (Exception e) {
            logger.error("Error in Violation Appeal workflow", e);
            return new WorkflowResult("failed", e.getMessage(), null, null);
        }
    }

    @Override
    public WorkflowResult AnnualInspectionWorkflow(AnnualInspection inspection) {
        logger.info("Starting Annual Inspection workflow for property: {}", inspection.getPropertyId());

        try {
            // Step 1: Schedule Inspection (1 week)
            logger.info("Step 1: Scheduling annual inspection...");
            Workflow.sleep(Duration.ofDays(7));
            InspectionSchedulingResult schedulingResult = activities.scheduleAnnualInspection(inspection);

            // Step 2: On-site Inspection (1 day)
            logger.info("Step 2: Conducting on-site inspection...");
            Workflow.sleep(Duration.ofDays(1));
            OnSiteInspectionResult onSiteResult = activities.conductOnSiteInspection(inspection);

            // Step 3: Report Generation (2 days)
            logger.info("Step 3: Generating inspection report...");
            Workflow.sleep(Duration.ofDays(2));
            InspectionReportResult reportResult = activities.generateInspectionReport(inspection);

            // Step 4: Follow-up (if needed)
            logger.info("Step 4: Processing follow-up actions...");
            Workflow.sleep(Duration.ofDays(3));
            FollowUpResult followUpResult = activities.processFollowUpActions(inspection);

            // Step 5: Compliance Determination
            logger.info("Step 5: Determining compliance status...");
            Workflow.sleep(Duration.ofHours(4));
            ComplianceResult complianceResult = activities.determineComplianceStatus(inspection);

            return new WorkflowResult("completed", "Annual inspection completed",
                    schedulingResult.getInspectionId(), complianceResult);

        } catch (Exception e) {
            logger.error("Error in Annual Inspection workflow", e);
            return new WorkflowResult("failed", e.getMessage(), null, null);
        }
    }
}
