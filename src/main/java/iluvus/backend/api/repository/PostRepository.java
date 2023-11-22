package iluvus.backend.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iluvus.backend.api.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {

    
} 
