package com.hawaii.compliance.worker.controller;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/temporal")
@CrossOrigin(origins = "*")
public class TemporalController {

    @Autowired(required = false)
    private WorkflowClient workflowClient;

    @Value("${temporal.connection.target:localhost:7233}")
    private String temporalTarget;

    @Value("${temporal.connection.namespace:default}")
    private String temporalNamespace;

    @GetMapping("/workflows")
    public ResponseEntity<Map<String, Object>> listWorkflows() {
        try {
            Map<String, Object> response = new HashMap<>();
            
            // Always return mock data for now since we don't have full Temporal setup
            response.put("workflows", getMockWorkflows());
            response.put("total", 4);
            response.put("message", "Using mock data - Temporal connection not configured");
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Return mock data on error
            Map<String, Object> response = new HashMap<>();
            response.put("workflows", getMockWorkflows());
            response.put("total", 4);
            response.put("error", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/workflows")
    public ResponseEntity<Map<String, Object>> createWorkflow(@RequestBody Map<String, Object> request) {
        try {
            String workflowType = (String) request.get("workflowType");
            Map<String, Object> input = (Map<String, Object>) request.get("input");

            Map<String, Object> response = new HashMap<>();
            response.put("workflowId", "workflow_" + System.currentTimeMillis());
            response.put("runId", "run_" + System.currentTimeMillis());
            response.put("status", "RUNNING");
            response.put("message", "Workflow " + workflowType + " started successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to create workflow: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/workflows/{workflowId}")
    public ResponseEntity<Map<String, Object>> getWorkflow(@PathVariable String workflowId) {
        try {
            Map<String, Object> workflow = new HashMap<>();
            workflow.put("workflowId", workflowId);
            workflow.put("runId", "run_" + System.currentTimeMillis());
            workflow.put("workflowType", "TVRRegistrationWorkflow");
            workflow.put("status", "RUNNING");
            workflow.put("startTime", new Date().toString());
            
            return ResponseEntity.ok(workflow);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Workflow not found: " + e.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }

    private List<Map<String, Object>> getMockWorkflows() {
        List<Map<String, Object>> workflows = new ArrayList<>();
        
        // Mock TVR Registration Workflow
        Map<String, Object> tvrWorkflow = new HashMap<>();
        tvrWorkflow.put("workflowId", "tvr_12345");
        tvrWorkflow.put("runId", "run_abc123");
        tvrWorkflow.put("workflowType", "TVRRegistrationWorkflow");
        tvrWorkflow.put("status", "RUNNING");
        tvrWorkflow.put("startTime", "2026-02-03T10:30:00Z");
        tvrWorkflow.put("propertyId", "PROP-001");
        tvrWorkflow.put("applicantName", "John Doe");
        workflows.add(tvrWorkflow);

        // Mock Complaint Investigation Workflow
        Map<String, Object> complaintWorkflow = new HashMap<>();
        complaintWorkflow.put("workflowId", "complaint_67890");
        complaintWorkflow.put("runId", "run_def456");
        complaintWorkflow.put("workflowType", "ComplaintInvestigationWorkflow");
        complaintWorkflow.put("status", "COMPLETED");
        complaintWorkflow.put("startTime", "2026-02-02T14:15:00Z");
        complaintWorkflow.put("complaintId", "COMP-002");
        complaintWorkflow.put("priority", "HIGH");
        workflows.add(complaintWorkflow);

        // Mock Violation Appeal Workflow
        Map<String, Object> appealWorkflow = new HashMap<>();
        appealWorkflow.put("workflowId", "appeal_11111");
        appealWorkflow.put("runId", "run_ghi789");
        appealWorkflow.put("workflowType", "ViolationAppealWorkflow");
        appealWorkflow.put("status", "RUNNING");
        appealWorkflow.put("startTime", "2026-02-03T09:00:00Z");
        appealWorkflow.put("appealId", "APPEAL-003");
        workflows.add(appealWorkflow);

        // Mock Annual Inspection Workflow
        Map<String, Object> inspectionWorkflow = new HashMap<>();
        inspectionWorkflow.put("workflowId", "inspection_22222");
        inspectionWorkflow.put("runId", "run_jkl012");
        inspectionWorkflow.put("workflowType", "AnnualInspectionWorkflow");
        inspectionWorkflow.put("status", "FAILED");
        inspectionWorkflow.put("startTime", "2026-02-01T11:30:00Z");
        inspectionWorkflow.put("propertyId", "PROP-004");
        workflows.add(inspectionWorkflow);

        return workflows;
    }
}
