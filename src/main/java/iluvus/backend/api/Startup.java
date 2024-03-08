package iluvus.backend.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.CommunityUser;
import iluvus.backend.api.model.Post;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.CommunityUserRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;

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

    }

}
