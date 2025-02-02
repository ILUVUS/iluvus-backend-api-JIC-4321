package iluvus.backend.api.repository;

import iluvus.backend.api.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'username' : ?0 }")
    User findUserbyUsername(String username);

    @Query("{ 'username' : { $regex: '^?0', $options: 'i' } }")
    List<User> findUsersByUsernameStartingWith(String username);
}
