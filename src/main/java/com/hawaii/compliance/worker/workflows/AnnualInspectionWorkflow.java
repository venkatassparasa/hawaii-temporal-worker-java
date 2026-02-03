package com.hawaii.compliance.worker.workflows;

import com.hawaii.compliance.worker.dtos.AnnualInspection;
import com.hawaii.compliance.worker.dtos.WorkflowResult;
import io.temporal.workflow.WorkflowMethod;

public interface AnnualInspectionWorkflow {
    @WorkflowMethod
    WorkflowResult AnnualInspectionWorkflow(AnnualInspection inspection);
}
