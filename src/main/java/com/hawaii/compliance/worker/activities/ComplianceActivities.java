package com.hawaii.compliance.worker.activities;

import com.hawaii.compliance.worker.dtos.InitialReviewResult;
import com.hawaii.compliance.worker.dtos.results.AppealDecisionResult;
import com.hawaii.compliance.worker.dtos.results.AppealDocumentResult;
import com.hawaii.compliance.worker.dtos.results.ComplianceResult;
import com.hawaii.compliance.worker.dtos.results.FollowUpResult;
import com.hawaii.compliance.worker.dtos.results.HearingResult;
import com.hawaii.compliance.worker.dtos.results.InspectionReportResult;
import com.hawaii.compliance.worker.dtos.results.InspectionSchedulingResult;
import com.hawaii.compliance.worker.dtos.results.LegalReviewResult;
import com.hawaii.compliance.worker.dtos.results.NoticeResult;
import com.hawaii.compliance.worker.dtos.results.OnSiteInspectionResult;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import org.springframework.stereotype.Component;

import com.hawaii.compliance.worker.dtos.*;

@ActivityInterface
@Component
public interface ComplianceActivities {

    // TVR Registration Activities
    @ActivityMethod
    InitialReviewResult performInitialReview(TVRApplication application);

    @ActivityMethod
    ZoningResult verifyZoning(String propertyId);

    @ActivityMethod
    NCUCResult processNCUC(String applicationId);

    @ActivityMethod
    InspectionSchedulingResult scheduleInspection(TVRApplication application);

    @ActivityMethod
    RegistrationResult finalizeRegistration(TVRApplication application);

    // Complaint Investigation Activities
    @ActivityMethod
    ActivityResult performInitialAssessment(Complaint complaint);

    @ActivityMethod
    ActivityResult collectEvidence(Complaint complaint);

    @ActivityMethod
    ActivityResult conductSiteVisit(Complaint complaint);

    @ActivityMethod
    ActivityResult generateInvestigationReport(Complaint complaint);

    @ActivityMethod
    ActivityResult determineViolations(Complaint complaint);

    @ActivityMethod
    NoticeResult generateNotice(Complaint complaint);

    // Violation Appeal Activities
    @ActivityMethod
    AppealDocumentResult reviewAppealDocuments(Appeal appeal);

    @ActivityMethod
    LegalReviewResult conductLegalReview(Appeal appeal);

    @ActivityMethod
    HearingResult scheduleHearing(Appeal appeal);

    @ActivityMethod
    AppealDecisionResult makeAppealDecision(Appeal appeal);

    // Annual Inspection Activities
    @ActivityMethod
    InspectionSchedulingResult scheduleAnnualInspection(AnnualInspection inspection);

    @ActivityMethod
    OnSiteInspectionResult conductOnSiteInspection(AnnualInspection inspection);

    @ActivityMethod
    InspectionReportResult generateInspectionReport(AnnualInspection inspection);

    @ActivityMethod
    FollowUpResult processFollowUpActions(AnnualInspection inspection);

    @ActivityMethod
    ComplianceResult determineComplianceStatus(AnnualInspection inspection);
}
