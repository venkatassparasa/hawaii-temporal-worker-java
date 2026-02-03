package com.hawaii.compliance.worker.config;

import com.hawaii.compliance.worker.activities.ComplianceActivitiesImpl;
import com.hawaii.compliance.worker.workflows.AnnualInspectionWorkflow;
import com.hawaii.compliance.worker.workflows.ComplaintInvestigationWorkflow;
import com.hawaii.compliance.worker.workflows.TVRRegistrationWorkflow;
import com.hawaii.compliance.worker.workflows.ViolationAppealWorkflow;
import com.hawaii.compliance.worker.workflows.impl.ComplianceWorkflowsImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorkerConfiguration {

    @Value("${temporal.worker.task-queue}")
    private String taskQueue;

    @Autowired
    private WorkerFactory workerFactory;

    @EventListener(ApplicationReadyEvent.class)
    public void setupWorker() {
        Worker worker = workerFactory.newWorker(taskQueue);
        
        // Register workflows
        worker.registerWorkflowImplementationTypes(
                TVRRegistrationWorkflow.class,
                ComplaintInvestigationWorkflow.class,
                ViolationAppealWorkflow.class,
                AnnualInspectionWorkflow.class
        );

        worker.registerActivitiesImplementations(
                TVRRegistrationWorkflow.class,
                ComplaintInvestigationWorkflow.class,
                ViolationAppealWorkflow.class,
                AnnualInspectionWorkflow.class
        );

        // Start the worker
        workerFactory.start();
        
        System.out.println("🚀 Temporal Worker started successfully!");
        System.out.println("📋 Task Queue: " + taskQueue);
        System.out.println("🔄 Ready to process Hawaii Compliance workflows...");
    }
}
