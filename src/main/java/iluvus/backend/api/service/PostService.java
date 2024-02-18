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
import java.util.HashMap;
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
            postDto.setReport_count(BigInteger.ZERO);

            Post post = new Post(postDto);

            postRepository.insert(post);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
            return post.getUplift().intValue();
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

    public boolean likePost(Map<String, String> data) {
        try {
            Post post = postRepository.findById(data.get("postId")).orElse(null);
            if (post == null) {
                return false;
            }

            BigInteger addedBigInteger = post.getUplift().add(BigInteger.ONE);
            post.setUplift(addedBigInteger);
            postRepository.save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reportPost(Map<String, String> data) {
        try {
            Post post = postRepository.findById(data.get("postId")).orElse(null);
            if (post == null) {
                return false;
            }

            BigInteger addedBigInteger = post.getReport_count().add(BigInteger.ONE);
            post.setReport_count(addedBigInteger);
            if (addedBigInteger.compareTo(BigInteger.valueOf(5)) >= 0) {
                postRepository.delete(post);
            } else {
                postRepository.save(post);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
