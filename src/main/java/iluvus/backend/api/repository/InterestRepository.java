package iluvus.backend.api.repository;

import iluvus.backend.api.model.InterestTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InterestRepository extends MongoRepository<InterestTopic, Integer> {
    @Query("{ 'name' : ?0 }")
    InterestTopic findInterestTopicByName(String name);

    @Query("{ 'id' : ?0 }")
    InterestTopic findInterestTopicById(Integer id);

}
