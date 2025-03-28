package iluvus.backend.api.service;

import iluvus.backend.api.dto.ChatMessageDto;
import iluvus.backend.api.model.ChatMessage;
import iluvus.backend.api.repository.ChatMessageRepository;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.repository.ChatRoomRepository;
import iluvus.backend.api.model.ChatRoom;
import iluvus.backend.api.resources.NotificationType;
import iluvus.backend.api.model.User;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
// New Imports...
import java.util.stream.Collectors;


@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    //------------Direct One On One Message Method --------------------
    public ChatMessage sendDirectMessage(Map<String, String> data) {
        try {
            //extracting and verifying data
            String roomId = data.get("roomId");
            String senderId = data.get("senderId");
            String message = data.get("message");
            String timestamp = data.get("timestamp");
            String receiverId = data.get("receiverId");

            if (message == null || roomId == null || senderId == null || timestamp == null || receiverId == null) {
                return null;
            }

            //this will most likely be a bottleneck need to restructure later
            if (!userRepository.existsById(senderId) || !userRepository.existsById(receiverId)) {
                return null;
            }

            User sender = userRepository.findById(senderId).orElse(null);

            if (!chatRoomRepository.existsById(roomId)) {
                return null;
            } 

            //if either one is not in the chat, should return null for the controller
            ChatRoom chatroom = chatRoomRepository.findById(roomId).orElse(null);
            if (chatroom.getIsGroup()) {
                return null;
            }
            if (!chatroom.getParticipants().contains(senderId) || !chatroom.getParticipants().contains(receiverId)) {
                return null;
            }

            //creating new chatmessage with isdeleted flag = false
            ChatMessageDto chatMessageDto = new ChatMessageDto();
            chatMessageDto.setRoomId(roomId);
            chatMessageDto.setSenderId(senderId);
            chatMessageDto.setReceiverId(receiverId);
            chatMessageDto.setMessage(message);
            chatMessageDto.setTime(timestamp);
            chatMessageDto.setIsDeleted(false);

            ChatMessage chatMessage = new ChatMessage(chatMessageDto);


            if (receiverId != null && !receiverId.isEmpty()) {
                String notification = String.format("%s just messaged you.", sender.getFname());
                NotificationService.addNotification(senderId, receiverId, NotificationType.NEW_DIRECT_MESSAGE, notification, timestamp);
            }

            chatMessageRepository.insert(chatMessage);

            //maybe modify this to return a list of messages instead based on need of frontend
            return chatMessage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //---------------New Send Group message Method-----------------
    public ChatMessage sendGroupMessage(Map<String, String> data) {
        try {
            //extracting and verifying data
            String roomId = data.get("roomId");
            String senderId = data.get("senderId");
            String message = data.get("message");
            String timestamp = data.get("timestamp");
            String receiverId = data.get("receiverId");

            if (message == null || timestamp == null || roomId == null || senderId == null || timestamp == null) {
                return null;
            }

            //this will most likely be a bottleneck need to restructure later
            if (!userRepository.existsById(senderId)) {
                return null;
            }

            User sender = userRepository.findById(senderId).orElse(null);

            if (!chatRoomRepository.existsById(roomId)) {
                return null;
            } 

            //if this isn't a group, don't send a group message
            ChatRoom chatroom = chatRoomRepository.findById(roomId).orElse(null);
            if (receiverId != null || !chatroom.getIsGroup() || chatroom.getParticipants().isEmpty() || !chatroom.getParticipants().contains(senderId)) {
                return null;
            }

            List<String> participants = chatroom.getParticipants();

            ChatMessageDto chatMessageDto = new ChatMessageDto();
            chatMessageDto.setRoomId(roomId);
            chatMessageDto.setSenderId(senderId);
            chatMessageDto.setReceiverId(null);
            chatMessageDto.setMessage(message);
            chatMessageDto.setTime(timestamp);
            chatMessageDto.setIsDeleted(false);

            ChatMessage chatmessage = new ChatMessage(chatMessageDto);


            String notification = String.format("%s just messaged you in %s.", sender.getFname(), chatroom.getGroupName());

            for (String participantId : participants) {
                if (participantId.equals(senderId)) {
                    NotificationService.addNotification(senderId, participantId, NotificationType.NEW_DIRECT_MESSAGE, notification, timestamp);
                }
            }

            chatMessageRepository.insert(chatmessage);
            return chatmessage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
