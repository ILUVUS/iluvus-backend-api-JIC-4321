package iluvus.backend.api.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository {
    void updateUserGroups(String userId, String communityId);
}
