package iluvus.backend.api.dto;
import iluvus.backend.api.model.ChatRoom;
import java.util.List;

public class ChatRoomDto { 
    private String id;
    private String type; 
    private List<String> participants;
        
    public ChatRoomDto() {
    }

    public ChatRoomDto(String type, List<String> participants, String lastMessage) {
        this.type = type;
        this.participants = participants;
        //this isn't correct but will correct it later
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}