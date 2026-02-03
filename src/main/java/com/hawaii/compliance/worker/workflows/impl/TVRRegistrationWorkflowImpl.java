package com.hawaii.compliance.worker.workflows.impl;

import com.hawaii.compliance.worker.activities.ComplianceActivities;
import com.hawaii.compliance.worker.dtos.*;
import com.hawaii.compliance.worker.dtos.results.InitialReviewResult;
import com.hawaii.compliance.worker.dtos.results.NCUCResult;
import com.hawaii.compliance.worker.dtos.results.RegistrationResult;
import com.hawaii.compliance.worker.workflows.TVRRegistrationWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class TVRRegistrationWorkflowImpl implements TVRRegistrationWorkflow {
    private final static Logger logger = Workflow.getLogger(TVRRegistrationWorkflowImpl.class);

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

}
