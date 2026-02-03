package com.hawaii.compliance.worker.workflows;

import com.hawaii.compliance.worker.dtos.Complaint;
import com.hawaii.compliance.worker.dtos.WorkflowResult;
import io.temporal.workflow.WorkflowMethod;

public interface ComplaintInvestigationWorkflow {
    @WorkflowMethod
    WorkflowResult ComplaintInvestigationWorkflow(Complaint complaint);
}
