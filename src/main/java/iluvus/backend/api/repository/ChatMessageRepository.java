package iluvus.backend.api.repository;

import iluvus.backend.api.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    //no query needed because there should be an underlying query already
    List<ChatMessage> findByRoomId(String roomId);

    List<ChatMessage> findByRoomIdAndSenderId(String chatroomId, String senderId);

}