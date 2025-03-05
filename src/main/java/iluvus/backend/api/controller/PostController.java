package iluvus.backend.api.controller;

import iluvus.backend.api.model.Post;
import iluvus.backend.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<List<Post>> createPost(@RequestBody Map<String, String> data) {

        List<Post> newPost = postService.createPost(data);

        if (newPost != null) {
            return ResponseEntity.ok().body(newPost);
        } else {
            return ResponseEntity.badRequest().body(null);
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
    public ResponseEntity<List<HashMap<String, String>>> commentPost(@RequestBody Map<String, String> data) {
        List<HashMap<String, String>> newComment = postService.writeComment(data);

        if (newComment != null) {
            return ResponseEntity.ok().body(newComment);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> likePost(@RequestBody Map<String, String> data) {
        int upLifeNumber = postService.likePost(data);
        if (upLifeNumber != -1) {
            return ResponseEntity.ok().body(upLifeNumber);
        } else {
            return ResponseEntity.badRequest().body(0);
        }
    }

    @PostMapping(value = "/share", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> sharePost(@RequestBody Map<String, String> data) {
       int shareNumber = postService.sharePost(data);
       if (shareNumber != -1) {
           return ResponseEntity.ok().body(shareNumber);
       } else {
           return ResponseEntity.ok().body(0);
       }
    }

    @PostMapping(value = "/getAllComments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, String>>> getAllComments(@RequestBody Map<String, String> data) {
        List<HashMap<String, String>> allComments = postService.getAllComments(data);

        if (allComments != null) {
            return ResponseEntity.ok().body(allComments);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * /post/getPostsByCommunityID?id=
     * 
     * @return
     */
    @GetMapping(value = "/getPostsByCommunityID", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getAllCommunity(@RequestParam String id) {
        List<Post> posts = postService.getPostsByCommunityId(id);
        if (posts != null && !posts.isEmpty()) {
            return ResponseEntity.ok().body(posts);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reportPost(@RequestBody Map<String, String> data) {
        boolean isReported = postService.reportPost(data);
        if (isReported) {
            return ResponseEntity.ok().body("Post reported successfully");
        } else {
            return ResponseEntity.badRequest().body("Post reporting failed");
        }
    }

    @GetMapping(value = "/getPostForHomePage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getPostForHomePage(@RequestParam String userId) {
        List<Post> posts = postService.getPostForHomePage(userId);
        if (posts != null && !posts.isEmpty()) {
            return ResponseEntity.ok().body(posts);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get method to get all the posts with 5 or more reports
    @GetMapping(value = "/getReportedPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getReportedPosts(@RequestParam String communityId) {
        List<Post> reportedPosts = postService.getReportedPosts(communityId);
        if (reportedPosts != null) {
            return ResponseEntity.ok().body(reportedPosts);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    // Get method to get shared posts on Profile Page
    @GetMapping(value = "/getSharedPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getSharedPosts(@RequestParam String userId) {
        List<Post> posts = postService.getSharedPostsByUser(userId);
        if (posts != null) {
            return ResponseEntity.ok().body(posts);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // moderator decides to delete the reported post
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePost(@RequestBody Map<String, String> data) {
        boolean isDeleted = postService.deletePost(data);
        if (isDeleted) {
            return ResponseEntity.ok().body("Post deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Post deletion failed");
        }
    }

    // moderator decides to keep the reported post
    @PostMapping(value = "/keep", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> keepPost(@RequestBody Map<String, String> data) {
        boolean isKept = postService.keepPost(data);
        if (isKept) {
            return ResponseEntity.ok().body("Post kept successfully");
        } else {
            return ResponseEntity.badRequest().body("Post keeping failed");
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> searchPosts(
        @RequestParam String userId,
        @RequestParam String searchTerm
    ) {
        List<Post> foundPosts = postService.searchPosts(userId, searchTerm);
        return ResponseEntity.ok().body(foundPosts);
    }
}
