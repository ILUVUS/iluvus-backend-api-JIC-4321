package iluvus.backend.api.model;
import iluvus.backend.api.dto.ChatMessageDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.time.LocalDateTime;


@Document(collection = "chat_messages") 
//for efficient querying
@CompoundIndex(name = "chatroomSender_index", def = "{'roomId': 1, 'senderId: 1'}")
public class ChatMessage {
    @Id
    private String id;

    private String roomId;
    private String senderId;
    private String message;

    @Field("timestamp")
    private String timestamp;

    private boolean isDeleted;

    public ChatMessage() {
    }

    public ChatMessage(ChatMessageDto chatMessageDto) {
        this.roomId = chatMessageDto.getRoomId();
        this.senderId = chatMessageDto.getSenderId();
        this.message = chatMessageDto.getMessage();
        this.timestamp = chatMessageDto.getTimestamp();
        this.isDeleted = chatMessageDto.getIsDeleted();
    }

    public String getId() {
        return id;
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

    public String getTimestamp() {
        return timestamp;
    }

    //this wasn't in the model before so it was causing an error
    public void setTimestamp(String time) {
        this.timestamp = time;
    }

    public boolean getisDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}