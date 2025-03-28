package iluvus.backend.api.service;

import iluvus.backend.api.dto.ChatRoomDto;
import iluvus.backend.api.model.ChatMessage;
import iluvus.backend.api.model.ChatRoom;
import iluvus.backend.api.repository.ChatMessageRepository;
import iluvus.backend.api.repository.ChatRoomRepository;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.resources.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;


    public boolean createChatRoom(Map<String, String> data) {

        try {
            String groupName = data.get("groupName");

        String participants = data.get("participants");
        String createdBy = data.get("creator");

        ArrayList<String> participantList = new ArrayList<>();
        if (participants != null && !participants.isBlank()) {
            String[] participantsArray = participants.split(",");
            for (String participant : participantsArray) {
                participantList.add(participant);
            }
        }
        
        
        if (participantList.isEmpty() || participantList.size() <= 1 || createdBy == null) {
            return false;
        }


        boolean isGroup = participantList.size() > 2;
        if (isGroup) {
            if (groupName == null || groupName.trim().isEmpty()) {
                //need to prompt user to enter group name
                return false;
            }
        }

        if (!userRepository.existsById(createdBy)) {
            return false;
        }

        for (String participant : participantList) {
            if (!userRepository.existsById(participant)) {
                return false;
            }
        }

        ChatRoomDto chatroomDto = new ChatRoomDto();
        chatroomDto.setGroupName(isGroup ? groupName : null);
        chatroomDto.setCreatedBy(createdBy);
        chatroomDto.setParticipants(participantList);
        chatroomDto.setIsGroup(isGroup);
        ChatRoom chatroom = new ChatRoom(chatroomDto);

        chatRoomRepository.save(chatroom);

        // Send notifications to participants
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

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
