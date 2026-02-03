package com.hawaii.compliance.worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.temporal.worker.Worker;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @Autowired(required = false)
    private Worker worker;

    // Inject the client to access the Namespace
    @Autowired(required = false)
    private io.temporal.client.WorkflowClient workflowClient;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("service", "Hawaii Compliance Temporal Worker");
        response.put("timestamp", System.currentTimeMillis());
        response.put("workerRunning", worker != null);

        if (worker != null) {
            response.put("taskQueue", worker.getTaskQueue());

            // Fix: Get namespace from the client instead of the worker
            if (workflowClient != null) {
                response.put("namespace", workflowClient.getOptions().getNamespace());
            }
        }

        return response;
    }
    // ... rest of your code
}