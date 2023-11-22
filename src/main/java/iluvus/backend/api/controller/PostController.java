package iluvus.backend.api.controller;

import iluvus.backend.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    
    @Autowired
    private PostService postService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPost(@RequestBody Map<String, String> data) {
        
        boolean newPost = postService.createPost(data);
        
        if (newPost) {
            return ResponseEntity.ok().body("Post created successfully");
        } else {
            return ResponseEntity.badRequest().body("Post creation failed");
        }

    }
}
