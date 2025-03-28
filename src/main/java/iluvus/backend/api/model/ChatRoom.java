package iluvus.backend.api.model;

//need to add dto here
import iluvus.backend.api.dto.ChatRoomDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.util.List;

//import java.time.LocalDateTime;

@Document(collection = "chat_room")
@CompoundIndex(name = "groupName_index", def = "{groupName': 1} 1}")
@CompoundIndex(name = "userIndex", def = "{'participants': 1, 'isGroup': 1}")
public class ChatRoom {
    @Id
    private String id;
    
    private String groupName;
    
    private boolean isGroup;

    //participantIds
    private List<String> participants;
    private String createdBy;

    @Field("updatedAt")
    private String updatedAt;


    public ChatRoom() {
    }

    public ChatRoom(ChatRoomDto chatRoomDto) {
        this.groupName = chatRoomDto.getGroupName();
        this.isGroup = chatRoomDto.getIsGroup();
        this.participants = chatRoomDto.getParticipants();
        this.createdBy = chatRoomDto.getCreatedBy();
    }

    public String getId() {
        return id;
    }

    public boolean getIsGroup() {
        return this.isGroup;
    }


    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

