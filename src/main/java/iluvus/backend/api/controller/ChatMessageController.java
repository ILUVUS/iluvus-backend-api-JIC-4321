package iluvus.backend.api.controller;

import iluvus.backend.api.model.ChatMessage;
import iluvus.backend.api.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat_message")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * 
     * @param data JSON object with the following keys:
     *             roomId: String
     *             senderId: String
                   message: String
                   timestamp: String
                   receiverId: String
     *             
     * 
     * @return
     */
    @PostMapping(value = "/direct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendDirectMessage(@RequestBody Map<String, String> data) {
        try {
            ChatMessage chatMessage = chatMessageService.sendDirectMessage(data);
    
            if (chatMessage != null) {
                return ResponseEntity.ok(chatMessage);
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Failed to send message"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    
    /**
     * 
     * @param data JSON object with the following keys:
     *             roomId: String
     *             senderId: String
                   message: String
                   timestamp: String
                   receiverId: String
     *             
     * 
     * @return
     */
    @PostMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendGroupMessage(@RequestBody Map<String, String> data) {
        ChatMessage chatMessage = chatMessageService.sendGroupMessage(data);
    
        if (chatMessage != null) {
            return ResponseEntity.ok(chatMessage);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send group message"));
        }
    }
    
    

}
