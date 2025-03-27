package iluvus.backend.api.repository;

import iluvus.backend.api.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    @Query(value = "{ 'roomId': ?0 }", sort = "{ 'time': 1 }")
    List<ChatMessage> findChatMessagesByRoomId(String roomId);

}