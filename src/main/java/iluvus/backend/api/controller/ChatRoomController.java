package iluvus.backend.api.controller;

import iluvus.backend.api.dto.ChatRoomDto;
import iluvus.backend.api.model.ChatMessage;
import iluvus.backend.api.service.ChatMessageService;
import iluvus.backend.api.service.ChatRoomService;
import iluvus.backend.api.model.ChatRoom;
import iluvus.backend.api.repository.ChatRoomRepository;
import iluvus.backend.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat_room")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

     @Autowired
    private ChatRoomRepository chatRoomRepository;

@Autowired
private UserRepository userRepository;


    /**
     * 
     * @param data JSON object with the following keys:
     *          
     *             groupName: String
     *             participants: String
     *             creator: String
     *             
     * 
     * @return
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createChatRoom(@RequestBody Map<String, String> data) {
        try {
            ChatRoom chatroom = chatRoomService.createChatRoom(data);
    
            if (chatroom == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Chat room creation failed"));
            }
    
            List<String> participantIds = chatroom.getParticipants();
            Map<String, String> idToUsername = new HashMap<>();
            userRepository.findAllById(participantIds).forEach(user ->
                idToUsername.put(user.getId(), user.getUsername())
            );
    
            ChatRoomDto dto = new ChatRoomDto(chatroom, idToUsername);
            return ResponseEntity.ok(Map.of("chatId", chatroom.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    
    @GetMapping("/getChats")
    public ResponseEntity<List<ChatRoomDto>> getChats(@RequestParam String userId) {
        List<ChatRoomDto> chats = chatRoomService.getChatsWithUsernames(userId);
        return ResponseEntity.ok(chats);
    }
     /**
     * Endpoint to retrieve recent messages from a chat room.
     * @param roomId ID of the chat room.
     * @param page Page number (zero-based).
     * @param size Number of messages per page.
     * @return ResponseEntity containing a Page of ChatMessage objects.
     */
    @GetMapping("/{roomId}/recent_messages")
    public ResponseEntity<Page<ChatMessage>> getRecentMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ChatMessage> messages = chatRoomService.getRecentMessages(roomId, page, size);
        if (messages != null && messages.hasContent()) {
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.noContent().build();
        }
    }



    
}
