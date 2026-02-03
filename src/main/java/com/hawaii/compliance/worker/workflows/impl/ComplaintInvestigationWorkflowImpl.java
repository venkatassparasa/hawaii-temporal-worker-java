package com.hawaii.compliance.worker.workflows.impl;

import com.hawaii.compliance.worker.activities.ComplianceActivities;
import com.hawaii.compliance.worker.dtos.Complaint;
import com.hawaii.compliance.worker.dtos.WorkflowResult;
import com.hawaii.compliance.worker.dtos.results.NoticeResult;
import com.hawaii.compliance.worker.workflows.ComplaintInvestigationWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class ComplaintInvestigationWorkflowImpl implements ComplaintInvestigationWorkflow {

    private final static Logger logger = Workflow.getLogger(ComplaintInvestigationWorkflowImpl.class);

    private final ComplianceActivities activities = Workflow.newActivityStub(
            ComplianceActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(10))
                    .build()
    );


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

}
