package com.hawaii.compliance.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HawaiiComplianceWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HawaiiComplianceWorkerApplication.class, args);
    }
}
