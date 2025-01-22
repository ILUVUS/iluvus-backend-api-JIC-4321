package iluvus.backend.api.repository;

import iluvus.backend.api.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SkillRepository extends MongoRepository<Skill, Integer> {
    @Query("{ 'name' : ?0 }")
    Skill findSkillByName(String name);

    @Query("{ 'id' : ?0 }")
    Skill findSkillById(Integer id);
}