package iluvus.backend.api.service;

import iluvus.backend.api.repository.InterestRepository;
import iluvus.backend.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class InterestTopicService {

    @Autowired
    private InterestRepository  interestRepository;

    public HashMap<Integer, String> getInterestTopic() {
        // get all interest topics
        HashMap<Integer, String> interestTopicMap = new HashMap<>();
        interestRepository.findAll().forEach(interestTopic -> {
            interestTopicMap.put(interestTopic.getId(), interestTopic.getName());
        });
        return interestTopicMap;
    }

}
