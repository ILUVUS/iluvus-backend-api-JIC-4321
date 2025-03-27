package iluvus.backend.api.dto;
import iluvus.backend.api.model.ChatMessage;


public class ChatMessageDto { 
    private String id; 
    private String roomId; 
    private String senderId; 
    private String message; 
    private String time;


    public ChatMessageDto() {
    }

    public ChatMessageDto(String roomId, String senderId, String message, String time) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
        this.time = time;
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
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
