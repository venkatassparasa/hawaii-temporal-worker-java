package com.hawaii.compliance.worker.dtos.results;

public class NoticeResult extends ActivityResult {
    private String noticeId;
    private String noticeType;

    public NoticeResult(String noticeId, String noticeType) {
        super(true, "Notice sent", noticeId, null);
        this.noticeId = noticeId;
        this.noticeType = noticeType;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public String getNoticeType() {
        return noticeType;
    }
}
