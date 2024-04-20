package iluvus.backend.api.service;

import iluvus.backend.api.dto.CommunityDto;
import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.CommunityUser;
import iluvus.backend.api.resources.CommunityUserStatus;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.CommunityUserRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.resources.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;

    public boolean createCommunity(Map<String, String> data) {
        try {
            CommunityDto communityDto = new CommunityDto();

            communityDto.setName(data.get("name"));
            communityDto.setDescription(data.get("description"));
            communityDto.setRule(data.get("rules"));
            communityDto.setPublic(data.get("visibility").equals("Public"));

            String moderators = data.get("moderators");

            ArrayList<String> moderatorList = new ArrayList<>();
            if (moderators != null || !moderators.isBlank()) {
                String[] moderatorArray = moderators.split(",");
                for (String moderator : moderatorArray) {
                    moderatorList.add(moderator);
                }
            }

            communityDto.setModerators(moderatorList);

            User owner = userRepository.findById(data.get("ownerId")).orElse(null);

            communityDto.setOwner(owner.getId());
            communityDto.setMembers(new ArrayList<>());
            communityDto.setImage(data.get("image"));
            Community community = new Community(communityDto);

            List<String> receiverIds = community.getModerators();
            String dateTime = java.time.OffsetDateTime.now().toString();
            if (receiverIds != null && !receiverIds.isEmpty()) {
                String message = String.format("%s added you as moderator in %s", owner.getFname(),
                        community.getName());
                for (String receiverId : receiverIds) {
                    NotificationService.addNotification(owner.getId(), receiverId,
                            NotificationType.MODERATOR_ADD, message, dateTime);
                }
            }

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

    public Map<String, String> searchCommunity(String filter) {
        Map<String, String> communityList = this.getCommunityInfo();
        // Convert the filter to lowercase for case-insensitive comparison
        String lowercaseFilter = filter.toLowerCase();

        // Filter the communityList based on the specified filter (case-insensitive)
        Map<String, String> filteredCommunityList = communityList.entrySet()
                .stream()
                .filter(entry -> entry.getValue().toLowerCase().contains(lowercaseFilter))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return filteredCommunityList;
    }

    public int getPostNumber(String communityId) {
        return postRepository.findPostByCommunity_id(communityId).size();
    }

    public boolean joinCommunity(Map<String, String> data) {
        try {
            String userId = data.get("userId");
            String communityId = data.get("communityId");

            User user = userRepository.findById(userId).orElse(null);
            Community community = communityRepository.findById(communityId).orElse(null);

            List<CommunityUser> communityUsers = communityUserRepository.findByCommunityId(communityId);

            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
            if (community == null) {
                throw new IllegalArgumentException("Community not found");
            }

            // check if user is already a member
            for (CommunityUser communityUser : communityUsers) {
                if (communityUser.getMemberId().equals(userId)) {
                    throw new IllegalArgumentException("User is already a member");
                }
            }
            CommunityUser communityUser = new CommunityUser();
            communityUser.setCommunityId(communityId);
            communityUser.setMemberId(userId);

            if (community.isPublic()) {
                user.addGroup(communityId);
                userRepository.save(user);
                communityUser.setStatus(CommunityUserStatus.APPROVED);
                communityUserRepository.insert(communityUser);
            } else {
                String requestDateTime = data.get("requestDateTime");
                return sendJoinRequest(userId, communityId, requestDateTime);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> getCommunityInformation(String communityId) {
        try {
            Community community = communityRepository.findById(communityId).orElse(null);

            if (community == null) {
                throw new IllegalArgumentException("Community not found");
            }

            CommunityDto communityDto = new CommunityDto(community);
            Map<String, Object> communityInfo = communityDto.getCommunityPublicInfo();

            List<CommunityUser> communityUsers = communityUserRepository.findByCommunityId(communityId);
            communityInfo.put("members", communityUsers);

            return communityInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getCommunityMembers(String communityId) {
        List<User> members = new ArrayList<>();
        List<CommunityUser> communityUsers = communityUserRepository.findByCommunityId(communityId);
        for (CommunityUser communityUser : communityUsers) {
            User user = userRepository.findById(communityUser.getMemberId()).orElse(null);
            if (user != null) {
                members.add(user);
            }
        }
        return members;
    }

    public boolean isCommunityPublic(String communityId) {
        try {
            Community community = communityRepository.findById(communityId).orElse(null);
            if (community != null) {
                return community.isPublic();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean sendJoinRequest(String userId, String communityId, String requestDateTime) {
        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null && !community.isPublic()) {
            CommunityUser communityUser = new CommunityUser();
            communityUser.setCommunityId(communityId);
            communityUser.setMemberId(userId);
            communityUser.setStatus(CommunityUserStatus.PENDING);
            communityUser.setRequestDateTime(requestDateTime);
            communityUserRepository.save(communityUser);
            return true;
        }
        return false;
    }

    public List<CommunityUser> findCommunityUserByStatus(String communityId, CommunityUserStatus status) {
        return communityUserRepository
                .findByCommunityIdAndStatus(communityId, status);
    }

    public List<HashMap<String, Object>> getPendingJoinRequests(String communityId) {
        List<CommunityUser> communityUsers = findCommunityUserByStatus(communityId, CommunityUserStatus.PENDING);
        List<HashMap<String, Object>> pendingJoinRequests = new ArrayList<>();
        for (CommunityUser communityUser : communityUsers) {
            User user = userRepository.findById(communityUser.getMemberId()).orElse(null);
            UserDto userDto = new UserDto(user);
            HashMap<String, Object> userMap = userDto.getPublicUserInfo();
            userMap.put("requestDateTime", communityUser.getRequestDateTime());
            userMap.put("communityId", communityId);
            pendingJoinRequests.add(userMap);
        }
        return pendingJoinRequests;
    }

    public List<HashMap<String, Object>> approveJoinRequest(Map<String, String> data) {
        String userId = data.get("userId");
        String communityId = data.get("communityId");
        CommunityUser communityUser = communityUserRepository.findByCommunityIdAndMemberId(communityId, userId);
        User user = userRepository.findById(userId).orElse(null);
        if (communityUser != null && communityUser.getStatus() == CommunityUserStatus.PENDING) {
            user.addGroup(communityId);
            userRepository.save(user);
            communityUser.setStatus(CommunityUserStatus.APPROVED);
            communityUserRepository.save(communityUser);
        }
        return getPendingJoinRequests(communityId);
    }

    public List<HashMap<String, Object>> rejectJoinRequest(Map<String, String> data) {
        String userId = data.get("userId");
        String communityId = data.get("communityId");
        CommunityUser communityUser = communityUserRepository.findByCommunityIdAndMemberId(communityId, userId);
        if (communityUser != null && communityUser.getStatus() == CommunityUserStatus.PENDING) {
            communityUserRepository.delete(communityUser);
        }
        return getPendingJoinRequests(communityId);
    }

    public Map<String, String> getMyCreatedGroup(String userId) {
        Map<String, String> myCreatedGroup = new HashMap<>();
        List<Community> communities = communityRepository.findByOwner(userId);
        for (Community community : communities) {
            myCreatedGroup.put(community.getId(), community.getName());
        }
        return myCreatedGroup;
    }
}
