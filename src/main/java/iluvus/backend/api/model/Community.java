package iluvus.backend.api.model;

import iluvus.backend.api.dto.CommunityDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;

@Document(collection = "communities")
public class Community {
    @Id
    private String id;
    private String name;
    private String description;
    private String rule;

    private boolean isPublic;

    @DBRef
    private User owner;

    @Field("members")
    private List<String> members;

    public Community() {
    }

    public Community(CommunityDto communityDto) {
        this.name = communityDto.getName();
        this.description = communityDto.getDescription();
        this.rule = communityDto.getRule();
        this.isPublic = communityDto.isPublic();
        this.owner = communityDto.getOwner();
        this.members = communityDto.getMembers();
    }

    // get id
    public String getId() {
        return id;
    }

    // Getter and Setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter methods for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter methods for rule
    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    // Getter and Setter methods for isPublic
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    // Getter and Setter methods for owner
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    // Getter and Setter methods for members
    public List<String> getMembers() {
        return members;
    }

    public void initMembers() {
        this.members = new ArrayList<String>();
    }

    public boolean addMember(String userId) {
        return this.members.add(userId);
    }
}
