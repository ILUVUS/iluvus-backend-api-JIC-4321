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

    /**
     * Finds all Users with userid or username containing a string or fname, or lname containing a string
     * (case-insensitive).
     * Double check this and then make a pr tomorrow morning
     */
    @Query("{ $or: [ { 'username': { $regex: ?0, $options: 'i' } }," + "{ 'id': { $regex: ?0, $options: 'i' } }," + 
    "{ 'fname': { $regex: ?0, $options: 'i' } }," + 
    "{ 'lname': { $regex: ?0, $options: 'i' } } ] }")
    List<User> findUsersByString(String string);

}
