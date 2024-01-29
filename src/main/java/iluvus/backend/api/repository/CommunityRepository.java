package iluvus.backend.api.repository;

import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.User;
import java.util.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface CommunityRepository extends MongoRepository<Community, String> {

    @Query("{ 'id' : ?0 }")
    @Update("{ '$set': { members: ?1 } }")
    Integer updateMember(String communityId, List<String> userIdList);

}
