package iluvus.backend.api.repository;

import iluvus.backend.api.model.CommunityUser;
import java.util.List;
import iluvus.backend.api.resources.CommunityUserStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CommunityUserRepository extends MongoRepository<CommunityUser, String> {
    @Query("{ 'communityId' : ?0 }")
    List<CommunityUser> findByCommunityId(String communityId);

    @Query("{ 'memberId' : ?0 }")
    List<CommunityUser> findByMemberId(String memberId);

    @Query("{ 'communityId' : ?0, 'memberId' : ?1 }")
    CommunityUser findByCommunityIdAndMemberId(String communityId, String memberId);

    @Query("{ 'communityId' : ?0, 'status' : ?1 }")
    List<CommunityUser> findByCommunityIdAndStatus(String communityId, CommunityUserStatus status);

    @Query(value = "{ 'memberId': ?0 }", fields = "{ 'communityId': 1, '_id': 0 }")
    List<String> findCommunityIdsByMemberId(String memberId);

}
