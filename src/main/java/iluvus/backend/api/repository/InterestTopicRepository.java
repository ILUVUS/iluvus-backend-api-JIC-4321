package iluvus.backend.api.repository;

import iluvus.backend.api.model.InterestTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface InterestTopicRepository extends MongoRepository<InterestTopic, String> {
    // filter interest topic if input matches any part of the name
    @Query("{ 'name' : { $regex: '^.*(?0).*$', $options: 'i' } }")
    List<InterestTopic> findInterestTopicsByName(String name);

    @Query("{ 'id' : ?0 }")
    InterestTopic findInterestTopicById(int id);
}
