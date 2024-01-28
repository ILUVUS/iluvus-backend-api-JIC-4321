package iluvus.backend.api.repository;

import iluvus.backend.api.model.Community;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommunityRepository extends MongoRepository<Community, String>, CustomCommunityRepository {

}
