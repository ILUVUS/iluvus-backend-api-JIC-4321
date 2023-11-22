package iluvus.backend.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iluvus.backend.api.model.Community;

public interface CommunityRepository extends MongoRepository<Community, String>{

}
