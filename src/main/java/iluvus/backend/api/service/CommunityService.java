package iluvus.backend.api.service;

import iluvus.backend.api.dto.CommunityDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository  postRepository;
    // @Autowired
    // private UserService userService;

    public boolean createCommunity(Map<String, String> data) {
        try {
            CommunityDto communityDto = new CommunityDto();

            communityDto.setName(data.get("name"));
            communityDto.setDescription(data.get("description"));
            communityDto.setRule(data.get("rules"));
            communityDto.setPublic(data.get("visibility").equals("Public"));

            User owner = userRepository.findUserbyUsername(data.get("ownerId"));
            communityDto.setOwner(owner.getId());

            communityDto.setMembers(new ArrayList<>());

            Community community = new Community(communityDto);

            communityRepository.insert(community);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, String> getAllCommunity() {
        Map<String, String> communityMap = new HashMap<>();
        for (Community community : communityRepository.findAll()) {
            communityMap.put(community.getId(), community.getName());
        }
        return communityMap;
    }

    public Map<String, String> getCommunityInfo() {
        Map<String, String> communityMap = new HashMap<>();
        for (Community community : communityRepository.findAll()) {
            communityMap.put(community.getId(), community.getName());
        }
        return communityMap;
    }

    public int getPostNumber(String communityId) {
        return postRepository.findPostByCommunity_id(communityId).size();
    }

    public boolean joinCommunity(String userId, String communityId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            Community community = communityRepository.findById(communityId).orElse(null);

            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
            if (community == null) {
                throw new IllegalArgumentException("Community not found");
            }
            if (user.getGroups().contains(communityId)) {
                throw new IllegalArgumentException("User is already a member");
            }

            user.addGroup(communityId);
            community.addMember(userId);

            userRepository.save(user);
            communityRepository.save(community);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, String> getCommunityInformation(String communityId) {
        try {
            Community community = communityRepository.findById(communityId).orElse(null);
            if (community == null) {
                throw new IllegalArgumentException("Community not found");
            }

            List<String> memberList = community.getMembers();

            Map<String, String> communityInfo = new HashMap<>();
            communityInfo.put("name", community.getName());
            communityInfo.put("description", community.getDescription());
            communityInfo.put("rules", community.getRule());
            communityInfo.put("members", String.join(", ", memberList));

            communityInfo.put("visibility", String.valueOf(community.isPublic()));
            communityInfo.put("posts", String.valueOf(getPostNumber(communityId)));
            communityInfo.put("owner", community.getOwner());

            return communityInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
