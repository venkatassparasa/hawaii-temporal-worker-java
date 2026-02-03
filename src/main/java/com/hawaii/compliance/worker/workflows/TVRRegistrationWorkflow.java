package com.hawaii.compliance.worker.workflows;

import com.hawaii.compliance.worker.dtos.TVRApplication;
import com.hawaii.compliance.worker.dtos.WorkflowResult;
import io.temporal.workflow.WorkflowMethod;

public interface TVRRegistrationWorkflow {
    @WorkflowMethod
    WorkflowResult TVRRegistrationWorkflow(TVRApplication application);
}
