package iluvus.backend.api.repository;

import iluvus.backend.api.model.Community;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CommunityRepository extends MongoRepository<Community, String> {

}
