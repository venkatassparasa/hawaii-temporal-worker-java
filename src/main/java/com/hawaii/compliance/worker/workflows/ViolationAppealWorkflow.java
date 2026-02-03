package com.hawaii.compliance.worker.workflows;

import com.hawaii.compliance.worker.dtos.Appeal;
import com.hawaii.compliance.worker.dtos.WorkflowResult;
import io.temporal.workflow.WorkflowMethod;

public interface ViolationAppealWorkflow {
    @WorkflowMethod
    WorkflowResult ViolationAppealWorkflow(Appeal appeal);
}
