package iluvus.backend.api.service;

import iluvus.backend.api.dto.CommunityDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public boolean createCommunity(Map<String, String> data) {
        try {
            CommunityDto communityDto = new CommunityDto();

            communityDto.setName(data.get("name"));
            communityDto.setDescription(data.get("description"));
            communityDto.setRule(data.get("rules"));
            communityDto.setPublic(Boolean.valueOf(data.get("visibility")));

            User owner = userRepository.findUserbyUsername(data.get("ownerId"));
            communityDto.setOwner(owner);

            communityDto.setMembers(new HashSet<>());

            Community community = new Community(communityDto);

            communityRepository.insert(community);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<String> getAllCommunity() {
        ArrayList<String> communityList = new ArrayList<>();
        for (Community community : communityRepository.findAll()) {
            communityList.add(community.getName());
        }
        return communityList;
    }

    public Community getCommunityByID(String id) {
        return communityRepository.findById(id).orElse(null);
    }

    public boolean joinCommunity(String userId, String communityId) {
        try {
            User user = userService.getUserByID(userId);
            Community community = getCommunityByID(communityId);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
            if (community == null) {
                throw new IllegalArgumentException("Community not found");
            }
            if (user.getGroups().contains(communityId)) {
                throw new IllegalArgumentException("User is already a member");
            }

            user.getGroups().add(communityId);
            community.getMembers().add(user); // do we also want Community to have list of user ids

            userRepository.save(user);
            communityRepository.save(community);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
