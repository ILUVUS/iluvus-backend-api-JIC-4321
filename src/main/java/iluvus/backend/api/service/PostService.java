package iluvus.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iluvus.backend.api.dto.*;
import iluvus.backend.api.model.*;
import iluvus.backend.api.repository.*;

// create post service class
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // create post method
    public void createPost(PostDto postDto) {
        Post post = new Post(postDto);
        postRepository.insert(post);
    }
    
}
