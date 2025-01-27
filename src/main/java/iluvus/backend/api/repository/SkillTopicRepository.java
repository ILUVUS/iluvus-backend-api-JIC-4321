package iluvus.backend.api.repository;

import iluvus.backend.api.model.SkillTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SkillTopicRepository extends MongoRepository<SkillTopic, Integer> {

    /**
     * Finds all SkillTopic documents whose 'name' field contains the given string
     * (case-insensitive).
     */
    @Query("{ 'name' : { $regex: '^.*(?0).*$', $options: 'i' } }")
    List<SkillTopic> findSkillTopicsByName(String name);

    /**
     * Finds a SkillTopic by its numeric ID.
     */
    @Query("{ 'id' : ?0 }")
    SkillTopic findSkillTopicById(int id);
}
