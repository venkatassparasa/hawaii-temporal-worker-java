package com.hawaii.compliance.worker.dtos.results;

import com.hawaii.compliance.worker.dtos.ActivityResult;

public class AppealDocumentResult extends ActivityResult {
    private String documentId;
    private String documentType;

    public AppealDocumentResult(String documentId, String documentType) {
        super(true, "Document processed", documentId, null);
        this.documentId = documentId;
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocumentType() {
        return documentType;
    }
}
