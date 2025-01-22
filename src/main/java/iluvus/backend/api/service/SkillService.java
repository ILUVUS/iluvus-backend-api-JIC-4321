package iluvus.backend.api.service;

import iluvus.backend.api.model.Skill;
import iluvus.backend.api.repository.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillType skillTypeRepository;

    // public HashMap<Integer, String> getInterestTopic() {
    //     // get all interest topics
    //     HashMap<Integer, String> interestTopicMap = new HashMap<>();
    //     interestTopicRepository.findAll().forEach(interestTopic -> {
    //         interestTopicMap.put(interestTopic.getId(), interestTopic.getName());
    //     });
    //     return interestTopicMap;
    // }

    // public HashMap<Integer, String> filterInterestTopic(String name) {
    //     if (name == null || name.isEmpty()) {
    //         return getInterestTopic();
    //     }
    //     // get all interest topics
    //     List<InterestTopic> interestTopics = interestTopicRepository.findInterestTopicsByName(name);
    //     HashMap<Integer, String> interestTopicMap = new HashMap<>();
    //     interestTopics.forEach(interestTopic -> {
    //         interestTopicMap.put(interestTopic.getId(), interestTopic.getName());
    //     });
    //     return interestTopicMap;
    // }

    // public HashMap<Integer, String> getInterestTopicById(int id) {
    //     InterestTopic interestTopic = interestTopicRepository.findInterestTopicById(id);
    //     HashMap<Integer, String> interestTopicMap = interestTopic.getInterestTopic();
    //     return interestTopicMap;
    // }


    //check all of this later this evening
    public HashMap<Integer, String> getSkill() {
        HashMap<Integer, String> skillTypeMap = new HashMap<>();
        skillTypeRepository.findAll().forEach(skill -> { skillTypeMap.put(skill.getId(), skill.getName());
        });
        return skillTypeMap;
    }

    public HashMap<Integer, String> filterSkillTopic(String name) {
        if (name == null || name.isEmpty()) {
            return getSkill();
        }

        List<Skill> skillTypes = skillTypeRepository.findSkillByName(name);
        HashMap<Integer, String> skillTypeMap = new  HashMap<>();
        skillTypes.forEach(skill -> {
            skillTypeMap.put(skill.getId(), skill.getName());
        });
        return skillTypeMap;
    }

    //get SkillTopicbyID do this
    public HashMap<Integer, String> getSkillById(int id) {
        Skill skill = skillTypeRepository.findSkillById(id);
        HashMap<Integer, String> skillTypeMap = skill.getSkillType();
        return skillTypeMap;
    }
    
}
