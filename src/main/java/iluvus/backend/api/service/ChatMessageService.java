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

    public ChatMessage sendMessage(Map<String, String> data) {
        try {
            //extracting and verifying data
            String roomId = data.get("roomId");
            String senderId = data.get("senderId");
            String message = data.get("message");
            String timestamp = data.get("timestamp");
            String receiverId = data.get("receiverId");

            if (message == null) {
                return null;
            }

            if (roomId == null) {
                return null;
            }

            if (senderId == null) {
                return null;
            }

            if (timestamp == null) {
                return null;
            }

            //this will most likely be a bottleneck need to restructure later
            if (!userRepository.existsById(senderId) || !userRepository.existsById(receiverId)) {
                return null;
            }

            if (!chatRoomRepository.existsById(roomId)) {
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

            chatMessageRepository.save(chatMessage);


            //maybe modify this to return a list of messages instead
            return chatMessage;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    
}
