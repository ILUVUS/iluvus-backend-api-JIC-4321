package iluvus.backend.api.repository;

import iluvus.backend.api.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface SkillType extends MongoRepository<Skill, Integer>{
    //allows users to find skills by typing if the input matches any part of a skill name
    @Query("{ 'name' : { $regex: '^.*(?0).*$', $options: 'i' } }")
    List<Skill> findSkillByName(String name);

    @Query("{ 'id' : ?0 }")
    Skill findSkillById(int id);
}
