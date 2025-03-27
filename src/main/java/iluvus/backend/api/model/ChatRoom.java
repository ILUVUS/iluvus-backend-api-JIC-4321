package iluvus.backend.api.model;

//need to add dto here
import iluvus.backend.api.dto.ChatRoom.Dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.util.List;

//import java.time.LocalDateTime;

@Document(collection = "chat_room")
public class ChatRoom {
    @Id
    private String id;
    private String type;
    private List<String> participants;
    private String lastMessage; //not sure if needed

    public ChatRoom() {
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

