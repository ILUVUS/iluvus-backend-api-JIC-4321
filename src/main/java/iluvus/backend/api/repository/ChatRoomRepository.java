package iluvus.backend.api.repository;

import iluvus.backend.api.model.ChatRoom; 
import org.springframework.data.mongodb.repository.MongoRepository; 
import org.springframework.data.mongodb.repository.Query; 
import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    
    @Query("{ 'participants': ?0 }")
    List<ChatRoom> findChatRoomsByParticipant(String userId);

    @Query("{ 'type': ?0 }")
    List<ChatRoom> findChatRoomsByType(String type);

}