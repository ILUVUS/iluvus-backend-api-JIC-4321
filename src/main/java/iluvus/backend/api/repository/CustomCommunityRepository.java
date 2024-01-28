package iluvus.backend.api.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomCommunityRepository {
    void updateCommunityMembers(String userId, String communityId);
}
