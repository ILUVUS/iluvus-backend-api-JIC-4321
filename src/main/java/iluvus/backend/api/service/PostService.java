package iluvus.backend.api.service;

import iluvus.backend.api.dto.PostDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.Post;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
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
            String text = data.get("text");
            String dateTime = data.get("dateTime");
            String author_id = data.get("authorId");
            String community_id = data.get("communityId");
            if (text == null || text.trim().isEmpty() || text.length() > 1000) {
                return false;
            }
            if (dateTime == null) {
                return false;
            }
            if (author_id == null) {
                return false;
            }
            if (community_id == null) {
                return false;
            }

            User author = userRepository.findById(author_id).orElse(null);
            if (author == null) {
                return false;
            }

            Community community = communityRepository.findById(community_id).orElse(null);
            if (community == null) {
                return false;
            }

            PostDto postDto = new PostDto();
            postDto.setText(text);
            postDto.setDateTime(dateTime);
            postDto.setUplift(BigInteger.ZERO);
            postDto.setAuthor_id(author_id);
            postDto.setCommunity_id(community_id);

            Post post = new Post(postDto);

            postRepository.insert(post);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    

    public List<Post> getPostsByAuthor(String author_id) {
        return postRepository.findPostByAuthor_id(author_id);
    }

    public List<Post> getPostsByCommunity(String community_id) {
        return postRepository.findPostByCommunity_id(community_id);
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
