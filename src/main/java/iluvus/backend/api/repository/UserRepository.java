package iluvus.backend.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import iluvus.backend.api.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
