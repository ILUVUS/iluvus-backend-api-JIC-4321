package iluvus.backend.api.dto;

public class ReportUserRequest {
    private String reporterId;
    private String reportedUserId;
    private String communityId;
    private String reason;

    public String getReporterId() { return reporterId; }
    public void setReporterId(String reporterId) { this.reporterId = reporterId; }

    public String getReportedUserId() { return reportedUserId; }
    public void setReportedUserId(String reportedUserId) { this.reportedUserId = reportedUserId; }

    public String getCommunityId() { return communityId; }
    public void setCommunityId(String communityId) { this.communityId = communityId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
