package iluvus.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iluvus.backend.api.dto.*;
import iluvus.backend.api.model.*;
import iluvus.backend.api.repository.*;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    public void createCommunity(CommunityDto communityDto) {
        Community community = new Community(communityDto);
        communityRepository.insert(community);
    }
}
