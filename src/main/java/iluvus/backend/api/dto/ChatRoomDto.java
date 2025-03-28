package iluvus.backend.api.dto;
import iluvus.backend.api.model.ChatRoom;
import java.util.List;

public class ChatRoomDto { 
    private String id;
    private String groupName;
    private boolean isGroup;
    private List<String> participants;
    private String createdBy;
        
    public ChatRoomDto() {
    }

    public ChatRoomDto(ChatRoom chatRoom) {
        this.groupName = chatRoom.getGroupName();
        this.isGroup = chatRoom.getIsGroup();
        this.participants = chatRoom.getParticipants();
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