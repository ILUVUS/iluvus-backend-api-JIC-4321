package iluvus.backend.api.dto;
import iluvus.backend.api.model.ChatRoom;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

public class ChatRoomDto { 
    private String id;
    private String groupName;
    private boolean isGroup;
    private List<String> participants;
    private String createdBy;
    private Map<String, String> participantIdToUsername;

        
    public ChatRoomDto() {
    }

    public ChatRoomDto(ChatRoom chatRoom, Map<String, String> userMap) {
        this.groupName = chatRoom.getGroupName();
        this.isGroup = chatRoom.getIsGroup();
        this.participants = chatRoom.getParticipants();
        this.participantIdToUsername = userMap;
        this.createdBy = chatRoom.getCreatedBy();
    }

    public String getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public Map<String, String> getParticipantIdToUsername() {
        return participantIdToUsername;
    }
    
    public void setParticipantIdToUsername(Map<String, String> participantIdToUsername) {
        this.participantIdToUsername = participantIdToUsername;
    }
    
    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public boolean getIsGroup() {
        return isGroup;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}