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

    /**
     * 
     * @param data JSON object with the following keys:
     *             text: String
     *             dateTime: String
     *             authorId: String
     *             communityId: String
     * 
     * @return
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPost(@RequestBody Map<String, String> data) {

        boolean newPost = postService.createPost(data);

        if (newPost) {
            return ResponseEntity.ok().body("Post created successfully");
        } else {
            return ResponseEntity.badRequest().body("Post creation failed");
        }

    }

    /**
     * write a comment
     * 
     * @param data JSON object with the following keys:
     *             postId: String
     *             authorId: String
     *             text: String
     *             dateTime: String
     * 
     * @return
     */
    @PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> commentPost(@RequestBody Map<String, String> data) {
        boolean newComment = postService.writeComment(data);

        if (newComment) {
            return ResponseEntity.ok().body("Comment created successfully");
        } else {
            return ResponseEntity.badRequest().body("Comment creation failed");
        }
    }

    @PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> likePost(@RequestBody Map<String, String> data) {
        boolean isLiked = postService.likePost(data);
        if (isLiked) {
            return ResponseEntity.ok().body("Successfully Liked a post");
        } else {
            return ResponseEntity.badRequest().body("Like Function Unsuccessful");
        }

    }
}
