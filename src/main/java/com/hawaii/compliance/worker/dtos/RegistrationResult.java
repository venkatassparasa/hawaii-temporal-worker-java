package com.hawaii.compliance.worker.dtos;

public class RegistrationResult extends ActivityResult {
    private String registrationId;
    private String registrationDate;

    public RegistrationResult(String registrationId, String registrationDate) {
        super(true, "Registration completed", registrationId, null);
        this.registrationId = registrationId;
        this.registrationDate = registrationDate;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
}
