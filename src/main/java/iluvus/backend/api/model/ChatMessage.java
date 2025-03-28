package iluvus.backend.api.model;
import iluvus.backend.api.dto.ChatMessageDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.time.LocalDateTime;


@Document(collection = "chat_messages") 

public class ChatMessage {
    @Id
    private String id;
    private String roomId;
    private String senderId;
    private String message;
    private String timestamp;

    public ChatMessage() {
    }

    public ChatMessage(String roomId, String senderId, String message, String time) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
        //this isn't in the model
        this.timestamp = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return timestamp;
    }

    //this isn't in the model
    public void setTime(String time) {
        this.timestamp = time;
    }
}