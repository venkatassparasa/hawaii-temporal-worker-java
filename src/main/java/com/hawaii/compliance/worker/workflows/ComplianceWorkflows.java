package com.hawaii.compliance.worker.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.*;
import com.hawaii.compliance.worker.activities.*;
import com.hawaii.compliance.worker.dtos.*;
import org.slf4j.Logger;

import java.time.Duration;

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

