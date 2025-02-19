package iluvus.backend.api.repository;

import iluvus.backend.api.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    @Query("{'author_id': ?0}")
    List<Post> findPostByAuthor_id(String author_id);

    @Query("{'community_id': ?0}")
    List<Post> findPostByCommunity_id(String community_id);

    @Query("{ 'sharedBy': { $in: [?0] } }")
    List<Post> findPostsSharedByUser(String userId);
    
    
}
