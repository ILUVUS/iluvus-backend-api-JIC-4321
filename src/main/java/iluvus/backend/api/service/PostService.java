package iluvus.backend.api.service;

import iluvus.backend.api.dto.PostDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.Post;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// create post service class
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;

    public List<Post> createPost(Map<String, String> data) {
        try {
            String text = data.get("text");
            String dateTime = data.get("dateTime");
            String author_id = data.get("authorId");
            String community_id = data.get("communityId");

            String raw_media = data.get("medias");

            List<String> medias = new ArrayList<>();

            if (raw_media != null && raw_media.strip().length() != 0) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, List<String>> media = objectMapper.readValue(raw_media,
                            new TypeReference<Map<String, List<String>>>() {
                            });
                    medias = media.get("urls");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            if (text == null || text.trim().isEmpty() || text.length() > 1000) {
                return null;
            }
            if (dateTime == null) {
                return null;
            }
            if (author_id == null) {
                return null;
            }
            if (community_id == null) {
                return null;
            }

            User author = userRepository.findById(author_id).orElse(null);
            if (author == null) {
                return null;
            }

            Community community = communityRepository.findById(community_id).orElse(null);
            if (community == null) {
                return null;
            }

            PostDto postDto = new PostDto();
            postDto.setText(text);
            postDto.setDateTime(dateTime);
            postDto.setAuthor_id(author_id);
            postDto.setCommunity_id(community_id);
            postDto.setMedias(medias);

            Post post = new Post(postDto);
            postRepository.insert(post);

            return getPostsByCommunityId(community_id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Post> getPostsByAuthorId(String id) {
        return postRepository.findPostByAuthor_id(id);
    }

    public List<Post> getPostsByCommunityId(String id) {
        if (id == null || id.strip().length() == 0) {
            return null;
        }
        List<Post> posts = postRepository.findPostByCommunity_id(id);
        HashMap<String, String> authorIdName = new HashMap<>();
        for (Post post : posts) {
            String authorId = post.getAuthor_id();
            if (authorIdName.containsKey(authorId)) {
                post.setAuthor_id(authorIdName.get(authorId));
            } else {
                User user = userRepository.findById(authorId).orElse(null);
                String fname = user.getFname();
                String lname = user.getLname();
                post.setAuthor_id(fname, lname);
                authorIdName.put(authorId, post.getAuthor_id());
            }
        }
        return posts;
    }

    public List<HashMap<String, String>> writeComment(Map<String, String> data) {
        try {
            String id = UUID.randomUUID().toString();
            String postId = data.get("postId");
            String authorId = data.get("authorId");
            String comment = data.get("text");
            String dateTime = data.get("dateTime");

            Post post = postRepository.findById(postId).orElse(null);

            if (comment == null || comment.strip().length() == 0) {
                return null;
            }

            if (post != null) {
                User user = userRepository.findById(authorId).orElse(null);
                if (user == null) {
                    return null;
                }
                if (comment.strip().length() == 0) {
                    return null;
                }
                post.writeComment(id, comment, authorId, dateTime);
                postRepository.save(post);

                return this.getCommentsWithAuthorName(postId);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HashMap<String, String>> getCommentsWithAuthorName(String postId) {
        try {
            Post post = postRepository.findById(postId).orElse(null);
            if (post == null) {
                return null;
            }
            List<HashMap<String, String>> comments = post.getComments();
            for (HashMap<String, String> comment : comments) {
                String authorId = comment.get("author_id");
                User user = userRepository.findById(authorId).orElse(null);
                if (user != null) {
                    comment.put("author_id", user.getLname() + ", " + user.getFname());
                }
            }
            return comments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getLikeNumber(String postId) {
        try {
            Post post = postRepository.findById(postId).orElse(null);
            if (post == null) {
                return -1;
            }
            return post.getLikedBy().size() - 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<HashMap<String, String>> getAllComments(Map<String, String> data) {
        try {
            String postId = data.get("postId");
            Post post = postRepository.findById(postId).orElse(null);
            if (post == null) {
                return null;
            }
            List<HashMap<String, String>> comments = post.getComments();
            for (HashMap<String, String> comment : comments) {
                String authorId = comment.get("author_id");
                User user = userRepository.findById(authorId).orElse(null);
                if (user != null) {
                    comment.put("author_id", user.getLname() + ", " + user.getFname());
                }
            }
            return comments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int likePost(Map<String, String> data) {
        try {
            Post post = postRepository.findById(data.get("postId")).orElse(null);
            String user = data.get("userId");
            if (post == null) {
                return 0;
            }
            List<String> likedBy = post.getLikedBy();

            if (likedBy.size() == 0) {
                likedBy.add(user);
                post.setLikedBy(likedBy);
            } else if (likedBy.contains(user)) {
                likedBy.remove(user);
                post.setLikedBy(likedBy);
            } else {
                likedBy.add(user);
                post.setLikedBy(likedBy);
            }

            postRepository.save(post);

            return post.getLikedBy().size();

            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean reportPost(Map<String, String> data) {
        try {
            Post post = postRepository.findById(data.get("postId")).orElse(null);
            String user = data.get("userId");
            if (post == null) {
                return false;
            }
            Community community = communityRepository.findById(post.getCommunity_id()).orElse(null);
            if (community == null) {
                return false;
            }

            User reporter = userRepository.findById(data.get("userId")).orElse(null);
            if (reporter == null) {
                return false;
            }

            if (reporter.equals(community.getOwner())) {
                System.out.println("Already reported by the user!");
                postRepository.delete(post);
                return true;
            }
            List<String> reportedBy = post.getReportedBy();
            if (reportedBy.contains(user)) {
                postRepository.save(post);
            } else {
                reportedBy.add(user);
                if (reportedBy.size() >= 5) {
                    postRepository.delete(post);
                } else {
                    post.setReportedBy(reportedBy);
                    postRepository.save(post);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
