package iluvus.backend.api.service;

import iluvus.backend.api.dto.ChatRoomDto;
import iluvus.backend.api.model.ChatMessage;
import iluvus.backend.api.model.ChatRoom;
import iluvus.backend.api.repository.ChatMessageRepository;
import iluvus.backend.api.repository.ChatRoomRepository;
import iluvus.backend.api.repository.UserRepository;
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


    //-----------------CHAT CREATION--------------------------
    public ChatRoom createChatRoom(Map<String, String> data) {
        try {
            String groupName = data.get("groupName");
            String participants = data.get("participants");
            String createdBy = data.get("creator");
    
            List<String> participantList = Arrays.stream(participants.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
    
            if (participantList.isEmpty() || participantList.size() <= 1 || createdBy == null) {
                return null;
            }
    
            boolean isGroup = participantList.size() > 2;
            if (isGroup) {
                if (groupName == null || groupName.trim().isEmpty() || participantList.size() > 5) {
                    return null;
                }
            }
    
            if (!userRepository.existsById(createdBy)) {
                return null;
            }
    
            for (String participant : participantList) {
                if (!userRepository.existsById(participant)) {
                    return null;
                }
            }
    
            ChatRoomDto chatroomDto = new ChatRoomDto();
            chatroomDto.setGroupName(isGroup ? groupName : null);
            chatroomDto.setCreatedBy(createdBy);
            chatroomDto.setParticipants(participantList);
            chatroomDto.setIsGroup(isGroup);
    
            ChatRoom chatroom = new ChatRoom(chatroomDto);
            chatRoomRepository.save(chatroom); // This assigns the chatroom an ID
    
            String notificationMessage = isGroup
                    ? String.format("%s created a new group with you: %s", createdBy, groupName)
                    : String.format("%s sent you a direct message.", createdBy);
    
            for (String participantId : participantList) {
                if (!participantId.equals(createdBy)) {
                    NotificationService.addNotification(
                            createdBy,
                            participantId,
                            NotificationType.NEW_DIRECT_MESSAGE,
                            notificationMessage,
                            String.valueOf(System.currentTimeMillis())
                    );
                }
            }
    
            return chatroom;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
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
