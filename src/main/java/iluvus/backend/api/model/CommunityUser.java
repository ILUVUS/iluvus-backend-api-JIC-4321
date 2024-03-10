package iluvus.backend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "community_user")
public class CommunityUser {
    @Id
    private String id;
    private String communityId;
    private String memberId;

    @Field("status")
    private CommunityUserStatus status;

    @Field("requestDateTime")
    private String requestDateTime;

    public CommunityUser() {
    }

    // get id
    public String getId() {
        return id;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public CommunityUserStatus getStatus() {
        return status;
    }

    public void setStatus(CommunityUserStatus status) {
        if (status == CommunityUserStatus.APPROVED) {
            this.removeRequestDateTime();
        }
        this.status = status;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public void removeRequestDateTime() {
        this.requestDateTime = null;
    }
}
