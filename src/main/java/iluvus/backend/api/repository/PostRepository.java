package iluvus.backend.api.repository;

import iluvus.backend.api.model.InterestTopic;
import iluvus.backend.api.model.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    //make sure these queries are not too expensive
    @Query("{'author_id': ?0}")
    List<Post> findPostByAuthor_id(String author_id);

    @Query("{'community_id': ?0}")
    List<Post> findPostByCommunity_id(String community_id);

    @Query("{ 'community_id': { $in: ?0 } }")
    Page<Post> findPostsByCommunityandTopicIds(List<String> communityIds, List<Integer> topicIds, Pageable pageable);
    //added new import pageable to return page of posts instead of full list

    @Query("{ 'sharedBy': { $in: [?0] } }")
    List<Post> findPostsSharedByUser(String userId);

    @Query("{" +
            "  'community_id': { $in: ?1 }, " +
            "  $or: [ " +
            "    { 'text': { $regex: ?0, $options: 'i' } }, " +
            "    { 'author_id': { $regex: ?0, $options: 'i' } } " +
            "  ] " +
            "}")
    // SELECT * FROM posts WHERE text = ?
    List<Post> searchByTermAndCommunities(String searchTerm, List<String> communityIds);

    @Query("{" +
        "  $and: [" +
        "    {'community_id': ?1}," +
        "    {" +
        "      $or: [" +
        "        {'text': { $regex: ?0, $options: 'i' }}, " +
        "        {'author_id': { $regex: ?0, $options: 'i' }}" +
        "      ]" +
        "    }" +
        "  ]" +
        "}")
    List<Post> searchByTermInCommunity(String searchTerm, String communityId);
}
