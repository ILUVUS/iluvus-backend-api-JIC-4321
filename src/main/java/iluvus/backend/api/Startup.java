package iluvus.backend.api;

import iluvus.backend.api.model.*;
import iluvus.backend.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Startup implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private InterestRepository interestRepository;

    @Override
    public void run(String... args) {
        initDatabaseIfEmpty();
    }

    private void initDatabaseIfEmpty() {
        User user = new User();
        userRepository.insert(user);
        userRepository.deleteById(user.getId());

        Post post = new Post();
        postRepository.insert(post);
        postRepository.deleteById(post.getId());

        Community community = new Community();
        communityRepository.insert(community);
        communityRepository.deleteById(community.getId());

        CommunityUser communityUser = new CommunityUser();
        communityUserRepository.insert(communityUser);
        communityUserRepository.deleteById(communityUser.getId());

//        InterestTopic interestTopic = new InterestTopic();
//        interestRepository.insert(interestTopic);
//        interestRepository.deleteById(String.valueOf(interestTopic.getId()));

        // use with care
//        insertInterestTopics();

    }

    private void insertInterestTopics() {
        // remove all interests
        interestRepository.deleteAll();

        List<String> topics = InterestTopic.topicList;
        for (int i = 0; i < topics.size(); i++) {
            InterestTopic interestTopic = new InterestTopic();
            interestTopic.setId(i);
            interestTopic.setName(topics.get(i));
            interestRepository.insert(interestTopic);
        }
    }

}
