package iluvus.backend.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import iluvus.backend.api.repository.*;
import iluvus.backend.api.dto.*;
import iluvus.backend.api.model.*;


@Component
public class Startup implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommunityRepository communityRepository;

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
    }

    private void createSampleUsers() {

        // userRepository.deleteAll();

        // UserDto userDto = new UserDto();
        // userDto.setUsername("username");
        // userDto.setEmail("email");
        // userDto.setPassword("password");
        // userDto.setVerified(false);
        // userDto.setFname("fname");
        // userDto.setLname("lname");
        // userDto.setGender("gender");
        // userDto.setDob("01/01/2001");
        // userDto.setRace("race");
        // userDto.setLocation(new LocationDto());
        // userDto.setInterests(new ArrayList<>());
        // userDto.setEducation(new ArrayList<>());
        // userDto.setWork(new ArrayList<>());
        // userDto.setSkills(new ArrayList<>());
        // userDto.setHobbies(new ArrayList<>());
        // userDto.setPosts(new ArrayList<>());
        // userDto.setFriends(new ArrayList<>());
        // userDto.setGroups(new ArrayList<>());

        // User user = new User(userDto);
        // userRepository.insert(user);

    }
}
