package iluvus.backend.api.dto;

import iluvus.backend.api.model.Community;

import java.util.*;

public class CommunityDto {
    private String name;
    private String description;
    private String rule;

    private boolean isPublic;

    private String owner;
    private List<String> members;
    private String image;

    // moderators
    private List<String> moderators;

    public CommunityDto() {
    }

    public CommunityDto(Community community) {
        this.name = community.getName();
        this.description = community.getDescription();
        this.rule = community.getRule();
        this.isPublic = community.isPublic();
        this.owner = community.getOwner();
        this.image = community.getImage();
        this.moderators = community.getModerators();
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
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    // Getter and Setter methods for members
    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getModerators() {
        return moderators;
    }

    // setter method for moderators
    public void setModerators(List<String> moderators) {
        this.moderators = moderators;
    }

    public Map<String, Object> getCommunityPublicInfo() {
        Map<String, Object> community = new HashMap<>();
        community.put("name", name);
        community.put("description", description);
        community.put("rules", rule);
        community.put("visibility", isPublic);
        community.put("owner", owner);
        community.put("image", image);
        community.put("moderators", moderators);
        return community;

    }
}
