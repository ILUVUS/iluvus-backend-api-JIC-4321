package iluvus.backend.api.service;

import iluvus.backend.api.repository.InterestTopicRepository;
import iluvus.backend.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class InterestTopicService {

    @Autowired
    private InterestTopicRepository interestTopicRepository;

    public HashMap<Integer, String> getInterestTopic() {
        // get all interest topics
        HashMap<Integer, String> interestTopicMap = new HashMap<>();
        interestTopicRepository.findAll().forEach(interestTopic -> {
            interestTopicMap.put(interestTopic.getId(), interestTopic.getName());
        });
        return interestTopicMap;
    }

    public HashMap<Integer, String> filterInterestTopic(String name) {
        if (name == null || name.isEmpty()) {
            return getInterestTopic();
        }
        // get all interest topics
        List<InterestTopic> interestTopics = interestTopicRepository.findInterestTopicsByName(name);
        HashMap<Integer, String> interestTopicMap = new HashMap<>();
        interestTopics.forEach(interestTopic -> {
            interestTopicMap.put(interestTopic.getId(), interestTopic.getName());
        });
        return interestTopicMap;
    }

}
