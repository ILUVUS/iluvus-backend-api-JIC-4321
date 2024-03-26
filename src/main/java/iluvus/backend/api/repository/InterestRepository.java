package iluvus.backend.api.repository;

import iluvus.backend.api.model.InterestTopic;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface InterestRepository extends MongoRepository<InterestTopic, String> {

}
