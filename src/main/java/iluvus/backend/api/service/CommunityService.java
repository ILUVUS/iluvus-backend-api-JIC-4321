package iluvus.backend.api.service;

import iluvus.backend.api.dto.CommunityDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean createCommunity(Map<String, String> data) {
        try {
            CommunityDto communityDto = new CommunityDto();

            communityDto.setName(data.get("name"));
            communityDto.setDescription(data.get("description"));
            communityDto.setRule(data.get("rule"));
            communityDto.setPublic(Boolean.valueOf(data.get("isPublic")));

            User owner = userRepository.findById(data.get("ownerId")).get();
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
}
