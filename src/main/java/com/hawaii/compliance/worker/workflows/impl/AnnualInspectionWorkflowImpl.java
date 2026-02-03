package com.hawaii.compliance.worker.workflows.impl;

import com.hawaii.compliance.worker.activities.ComplianceActivities;
import com.hawaii.compliance.worker.dtos.AnnualInspection;
import com.hawaii.compliance.worker.dtos.WorkflowResult;
import com.hawaii.compliance.worker.dtos.results.*;
import com.hawaii.compliance.worker.workflows.AnnualInspectionWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class AnnualInspectionWorkflowImpl implements AnnualInspectionWorkflow {

    private final static Logger logger = Workflow.getLogger(AnnualInspectionWorkflowImpl.class);

    private final ComplianceActivities activities = Workflow.newActivityStub(
            ComplianceActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(10))
                    .build()
    );

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
            FollowUpResult followUpResult;
            followUpResult = activities.processFollowUpActions(inspection);

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
