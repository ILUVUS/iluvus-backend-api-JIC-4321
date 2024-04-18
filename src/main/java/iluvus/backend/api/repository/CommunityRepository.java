package iluvus.backend.api.repository;

import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommunityRepository extends MongoRepository<Community, String> {
    @Query("{ 'owner' : ?0 }")
    List<Community> findByOwner(String ownerId);
}
