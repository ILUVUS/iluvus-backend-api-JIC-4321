package iluvus.backend.api.repository;

import iluvus.backend.api.model.ChatRoom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository; 
import org.springframework.data.mongodb.repository.Query; 
import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    // Find chatrooms by member and group type (for pagination)
    //no query needed cuz underlying query already used
    Page<ChatRoom> findByParticipantContainsAndIsGroup(String participant, boolean isGroup, Pageable pageable);

    List<ChatRoom> findByGroupName(String groupName);

    @Query("{'participants': ?0 }")
    Page<ChatRoom> findByMember(String memberId, Pageable pageable);
}