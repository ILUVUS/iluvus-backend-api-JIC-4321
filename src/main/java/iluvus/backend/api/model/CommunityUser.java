package iluvus.backend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "community_user")
public class CommunityUser {
    @Id
    private String id;
    private String communityId;
    private String memberId;
    private CommunityUserStatus status;

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
        this.status = status;
    }
}
