package iluvus.backend.api.service;

import iluvus.backend.api.repository.InterestTopicRepository;
import iluvus.backend.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

}
