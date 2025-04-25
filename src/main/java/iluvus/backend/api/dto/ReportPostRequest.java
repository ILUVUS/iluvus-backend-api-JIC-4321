package iluvus.backend.api.dto;

public class ReportPostRequest {
    private String postId;
    private String reporterId;
    private String reason;

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getReporterId() { return reporterId; }
    public void setReporterId(String reporterId) { this.reporterId = reporterId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
