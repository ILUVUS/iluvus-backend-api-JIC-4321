package iluvus.backend.api.service;

import iluvus.backend.api.model.SkillTopic;
import iluvus.backend.api.repository.SkillTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SkillTopicService {

    @Autowired
    private SkillTopicRepository skillTopicRepository;

    /**
     * Returns a map of all skill topics in the DB,
     * where the key is the skillTopic ID and the value is the skillTopic name.
     */
    public HashMap<Integer, String> getSkillTopic() {
        HashMap<Integer, String> skillTopicMap = new HashMap<>();
        // fetch all documents from the "skills" collection
        List<SkillTopic> skillTopics = skillTopicRepository.findAll();
        skillTopics.forEach(skillTopic -> {
            skillTopicMap.put(skillTopic.getId(), skillTopic.getName());
        });
        return skillTopicMap;
    }

    /**
     * If `name` is empty, returns all skill topics;
     * otherwise, returns skill topics that match the name filter.
     */
    public HashMap<Integer, String> filterSkillTopic(String name) {
        if (name == null || name.isEmpty()) {
            // return all
            return getSkillTopic();
        }
        // search using regex
        List<SkillTopic> skillTopics = skillTopicRepository.findSkillTopicsByName(name);
        HashMap<Integer, String> skillTopicMap = new HashMap<>();
        skillTopics.forEach(skillTopic -> {
            skillTopicMap.put(skillTopic.getId(), skillTopic.getName());
        });
        return skillTopicMap;
    }

    /**
     * Returns a map with the single skill topic that has the given ID.
     */
    public HashMap<Integer, String> getSkillTopicById(int id) {
        SkillTopic skillTopic = skillTopicRepository.findSkillTopicById(id);
        return (skillTopic != null) ? skillTopic.getSkillTopic() : new HashMap<>();
    }
}
