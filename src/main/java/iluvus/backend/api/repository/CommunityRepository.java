package iluvus.backend.api.repository;

import iluvus.backend.api.model.Community;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommunityRepository extends MongoRepository<Community, String> {
    @Query("{ 'owner' : ?0 }")
    List<Community> findByOwner(String ownerId);

    @Query("{ 'name' : { $regex: '^.*(?0).*$', $options: 'i' } }")
    List<Community> findCommunitiesByName(String name);
}
