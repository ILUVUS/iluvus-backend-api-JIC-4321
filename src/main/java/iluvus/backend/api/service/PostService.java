package iluvus.backend.api.service;

import iluvus.backend.api.dto.PostDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.Post;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// create post service class
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;

    public boolean createPost(Map<String, String> data) {
        try {
            PostDto postDto = new PostDto();

            String text = data.get("text");
            String authorId = data.get("authorId");
            String communityId = data.get("communityId");
            String dateTime = data.get("dateTime");

            User user = userRepository.findById(authorId).orElse(null);
            if (user == null) {
                return false;
            }
            Community community = communityRepository.findById(communityId).orElse(null);
            if (community == null) {
                return false;
            }
            if (text.strip().length() == 0) {
                return false;
            }
            
            postDto.setText(text);
            postDto.setDateTime(dateTime);
            postDto.setUplift(BigInteger.valueOf(0));
            postDto.setAuthor_id(authorId);
            postDto.setCommunity_id(communityId);

            Post post = new Post(postDto);

            postRepository.insert(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeComment(Map<String, String> data) {
        try {
            String id = UUID.randomUUID().toString();
            String postId = data.get("postId");
            String authorId = data.get("authorId");
            String comment = data.get("text");
            String dateTime = data.get("dateTime");

            Post post = postRepository.findById(postId).orElse(null);

            if (post != null) {
                User user = userRepository.findById(authorId).orElse(null);
                if (user == null) {
                    return false;
                }
                if (comment.strip().length() == 0) {
                    return false;
                }
                post.writeComment(id, comment, authorId, dateTime);
                postRepository.save(post);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
