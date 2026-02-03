package com.hawaii.compliance.worker.workflows.impl;

import com.hawaii.compliance.worker.activities.ComplianceActivities;
import com.hawaii.compliance.worker.dtos.Appeal;
import com.hawaii.compliance.worker.dtos.WorkflowResult;
import com.hawaii.compliance.worker.dtos.results.AppealDecisionResult;
import com.hawaii.compliance.worker.dtos.results.HearingResult;
import com.hawaii.compliance.worker.workflows.ViolationAppealWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class ViolationAppealWorkflowImpl implements ViolationAppealWorkflow {

    private final static Logger logger = Workflow.getLogger(ViolationAppealWorkflowImpl.class);

    private final ComplianceActivities activities = Workflow.newActivityStub(
            ComplianceActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(10))
                    .build()
    );

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

}
