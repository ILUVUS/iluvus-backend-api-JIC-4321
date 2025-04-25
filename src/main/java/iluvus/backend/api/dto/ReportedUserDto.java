package iluvus.backend.api.dto;

import iluvus.backend.api.model.User.UserReport;
import java.util.List;

public class ReportedUserDto {
    private String userId;
    private String username;
    private List<UserReport> reports;

    public ReportedUserDto(String userId, String username, List<UserReport> reports) {
        this.userId = userId;
        this.username = username;
        this.reports = reports;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<UserReport> getReports() {
        return reports;
    }
}
