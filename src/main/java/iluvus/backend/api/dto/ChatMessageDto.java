package iluvus.backend.api.dto;
import iluvus.backend.api.model.ChatMessage;


public class ChatMessageDto { 
    private String id; 
    private String roomId; 
    private String receiverId;
    private String senderId; 
    private String message; 
    private String timestamp;
    private boolean isDeleted;


    public ChatMessageDto() {
    }

    public ChatMessageDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.senderId = chatMessage.getSenderId();
        this.message = chatMessage.getMessage();
        this.timestamp = chatMessage.getTimestamp();
        this.isDeleted = chatMessage.getisDeleted();
        this.receiverId = chatMessage.getReceiverId();
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTime(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
