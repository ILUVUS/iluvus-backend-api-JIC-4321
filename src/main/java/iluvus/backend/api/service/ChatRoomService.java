package iluvus.backend.api.service;

import iluvus.backend.api.dto.ChatRoomDto;
import iluvus.backend.api.model.ChatMessage;
import iluvus.backend.api.model.ChatRoom;
import iluvus.backend.api.repository.ChatMessageRepository;
import iluvus.backend.api.repository.ChatRoomRepository;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.model.User;
import iluvus.backend.api.resources.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User user;

    //-----------------CHAT CREATION--------------------------
    public ChatRoom createChatRoom(Map<String, String> data) {
        try {
            String groupName = data.get("groupName");
            String participantsStr = data.get("participants");
            String creatorId = data.get("creator");
    
            if (participantsStr == null || creatorId == null) {
                System.out.println("Missing participants or creator in request");
                return null;
            }
    
            List<String> participantList = Arrays.stream(participantsStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
    
            if (participantList.size() < 2) {
                System.out.println("At least two participants are required");
                return null;
            }
    
            // Validate users exist
            for (String userId : participantList) {
                if (!userRepository.existsById(userId)) {
                    System.out.println("User ID does not exist: " + userId);
                    return null;
                }
            }

           
            
    
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setGroupName(groupName);
            chatRoom.setIsGroup(participantList.size() > 2 || (groupName != null && !groupName.isBlank()));
            chatRoom.setParticipants(participantList);
            chatRoom.setCreatedBy(creatorId);
            for (int i = 0; i < participantList.size(); i++) {
                for (int j = i + 1; j < participantList.size(); j++) {
                    String userA = participantList.get(i);
                    String userB = participantList.get(j);
                    User ua = userRepository.findById(userA).orElse(null);
                    User ub = userRepository.findById(userB).orElse(null);
                    if (ua.getBlockedUsers().contains(userB) || ub.getBlockedUsers().contains(userA)) {
                        System.out.println("Blocked: Cannot create chat between users with block relationship");
                        return null;
                    }
                }
            }
            
            ChatRoom saved = chatRoomRepository.save(chatRoom);
            System.out.println("ChatRoom created with ID: " + saved.getId());
            return saved;
    
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //-------------------RECENT CHATS--------------------------//
    public List<ChatRoomDto> getChatsWithUsernames(String userId) {
        List<ChatRoom> chats = chatRoomRepository.findByParticipantsContaining(userId);
        
        // Get all participant IDs
        Set<String> allUserIds = chats.stream()
            .flatMap(chat -> chat.getParticipants().stream())
            .collect(Collectors.toSet());
    
        // Map of userId -> username
        Map<String, String> idToUsername = new HashMap<>();
        userRepository.findAllById(allUserIds).forEach(user -> {
            idToUsername.put(user.getId(), user.getUsername());
        });
    
        // Build DTOs with participant ID to username map
        return chats.stream()
            .map(chat -> new ChatRoomDto(chat, idToUsername))
            .collect(Collectors.toList());
    }    
    
    //----------------RECENT MESSAGES---------------------------
    public Page<ChatMessage> getRecentMessages(String roomId, int page, int size) {
        try {
            if (page < 0 || size <= 0) {
                throw new IllegalArgumentException("Page index must be non-negative and size must be positive");
            }
    
            //check if the chat room exists
            if (!chatRoomRepository.existsById(roomId)) {
                System.err.println("Chat room not found for ID: " + roomId);
                throw new IllegalArgumentException("Chat room not found");
            }
            

            //fetch recent messages ordered by timestamp descending
            PageRequest pageRequest = PageRequest.of(page, size);
            return chatMessageRepository.findByRoomIdOrderByTimestampDesc(roomId, pageRequest);
        
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
