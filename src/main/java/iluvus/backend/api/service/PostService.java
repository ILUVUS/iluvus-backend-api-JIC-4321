package iluvus.backend.api.service;

import iluvus.backend.api.dto.PostDto;
import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.CommunityUser;
import iluvus.backend.api.resources.NotificationType;
import iluvus.backend.api.model.Post;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.CommunityUserRepository;
import iluvus.backend.api.repository.InterestRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.service.InterestTopicService;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// New Imports...
import java.util.stream.Collectors;

// create post service class
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private InterestTopicService interestTopicService;


    public List<Post> createPost(Map<String, String> data) {
        try {
            String text = data.get("text");
            String dateTime = data.get("dateTime");
            String author_id = data.get("authorId");
            String community_id = data.get("communityId");

            String raw_media = data.get("medias");
            String tagged = data.get("tagged");

            String raw_topicId = data.get("topicId");

            String sourceLink = data.get("sourceLink");

            List<String> taggedList = new ArrayList<>();
            if (tagged != null && !tagged.isBlank()) {
                taggedList = List.of(tagged.split(","));
            }

            List<String> medias = processMedia(raw_media);

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

            Integer topicId = null;

            if (raw_topicId == null || raw_topicId.isBlank()) {
                raw_topicId = String.valueOf(interestRepository.findAll().size() - 1);
                topicId = Integer.parseInt(raw_topicId);
            } else {
                topicId = Integer.parseInt(raw_topicId);
            }

            PostDto postDto = new PostDto();
            postDto.setText(text);
            postDto.setDateTime(dateTime);
            postDto.setAuthor_id(author_id);
            postDto.setCommunity_id(community_id);
            postDto.setMedias(medias);
            postDto.setTagged(taggedList);
            postDto.setTopicId(topicId);
            postDto.setSourceLink(sourceLink);

            Post post = new Post(postDto);

            String senderId = author.getId();
            List<String> receiverIds = taggedList;
            if (receiverIds != null && !receiverIds.isEmpty()) {
                String message = String.format("%s tagged you in a post in %s", author.getFname(), community.getName());
                for (String receiverId : receiverIds) {
                    NotificationService.addNotification(senderId, receiverId, NotificationType.TAG, message, dateTime);
                }
            }

            postRepository.insert(post);

            return getPostsByCommunityId(community_id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Post> getPostsByCommunityId(String id) {
        if (id == null || id.isBlank()) {
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

            if (comment == null || comment.isBlank()) {
                return null;
            }

            if (post != null) {
                User user = userRepository.findById(authorId).orElse(null);
                if (user == null) {
                    return null;
                }
                if (comment.isBlank()) {
                    return null;
                }
                post.writeComment(id, comment, authorId, dateTime);

                Community community = communityRepository.findById(post.getCommunity_id()).orElse(null);
                if (community == null) {
                    return null;
                }
                String senderId = authorId;
                String receiverId = post.getAuthor_id();
                String message = String.format("%s commented on your post in %s", user.getFname(), community.getName());
                NotificationService.addNotification(senderId, receiverId, NotificationType.COMMENT, message, dateTime);

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


    //reposting method: display - @userId (hyperlink to the profile where shared posts displayed) shared
   //other_userId's post (hyperlink to the post)
   public int sharePost(Map<String, String> data) {
    try {
        boolean shareSignal = false;
        Post post = postRepository.findById(data.get("postId")).orElse(null);
        String userId = data.get("userId");

        if (post == null) {
            return 0; // No post found
        }

        List<String> sharedBy = post.getSharedBy();
        if (sharedBy == null) {
            sharedBy = new ArrayList<>();
        }

        // Toggle sharing
        if (sharedBy.contains(userId)) {
            sharedBy.remove(userId);
        } else {
            sharedBy.add(userId);
            shareSignal = true;
        }

        post.setSharedBy(sharedBy);

        if (shareSignal) {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return 0;
            }

            // Check if the post belongs to a community
            if (post.getCommunity_id() != null && !post.getCommunity_id().isBlank()) {
                Community community = communityRepository.findById(post.getCommunity_id()).orElse(null);
                if (community != null) {
                    String senderId = userId;
                    String receiverId = post.getAuthor_id();
                    String message = String.format("%s shared your post in %s", user.getFname(), community.getName());
                    String dateTime = java.time.OffsetDateTime.now().toString();
                    NotificationService.addNotification(senderId, receiverId, NotificationType.SHARE, message, dateTime);
                }
            }
        }

        postRepository.save(post);
        return post.getSharedBy().size();

    } catch (Exception e) {
        e.printStackTrace();
        return -1;
    }
}

    public int likePost(Map<String, String> data) {
        try {
            boolean upliftSignal = false;
            Post post = postRepository.findById(data.get("postId")).orElse(null);
            String userId = data.get("userId");
            if (post == null) {
                return 0;
            }
            List<String> likedBy = post.getLikedBy();

            if (likedBy.size() == 0) {
                likedBy.add(userId);
                post.setLikedBy(likedBy);
                upliftSignal = true;
            } else if (likedBy.contains(userId)) {
                likedBy.remove(userId);
                post.setLikedBy(likedBy);
            } else {
                likedBy.add(userId);
                post.setLikedBy(likedBy);
                upliftSignal = true;
            }

            if (upliftSignal) {
                User user = userRepository.findById(userId).orElse(null);
                if (user == null) {
                    return 0;
                }
                Community community = communityRepository.findById(post.getCommunity_id()).orElse(null);
                if (community == null) {
                    return 0;
                }
                String senderId = userId;
                String receiverId = post.getAuthor_id();
                String message = String.format("%s uplifted your post in %s", user.getFname(), community.getName());
                String dateTime = java.time.OffsetDateTime.now().toString();
                NotificationService.addNotification(senderId, receiverId, NotificationType.UPLIFT, message, dateTime);
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

            if (reporter.getId().equals(community.getOwner())) {
                postRepository.delete(post);
                return true;
            }
            List<String> reportedBy = post.getReportedBy();
            if (reportedBy.contains(user)) {
                postRepository.save(post);
            } else {
                reportedBy.add(user);

                post.setReportedBy(reportedBy);

                String senderId = reporter.getId();
                String receiverId = post.getAuthor_id();
                String message = String.format("%s reported your post in %s", reporter.getFname(),
                        community.getName());
                String dateTime = java.time.OffsetDateTime.now().toString();
                NotificationService.addNotification(senderId, receiverId, NotificationType.REPORT, message,
                        dateTime);

                postRepository.save(post);

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> processMedia(String raw_media) {
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
        return medias;
    }

    public List<Post> getPostForHomePage(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        // List<String> groups = user.getGroups();

        List<String> groups = new ArrayList<>();
        List<CommunityUser> communityUsers = communityUserRepository.findByMemberId(userId);
        for (CommunityUser communityUser : communityUsers) {
            groups.add(communityUser.getCommunityId());
        }

        List<Post> posts = new ArrayList<>();
        // posts.add(postRepository.findById("65ef8ebb476b065552d2c618").orElse(null));
        for (String group : groups) {
            List<Post> groupPosts = postRepository.findPostByCommunity_id(group);
            posts.addAll(groupPosts);
        }
        List<Post> returningPost = new ArrayList<>();

        for (Post post : posts) {

            Community community = communityRepository.findById(post.getCommunity_id()).orElse(null);
            if (community == null) {
                postRepository.deleteById(post.getId());
                continue;
            }

            List<Integer> userInterest = user.getInterests();

            for (Integer interest : userInterest) {
                // System.out.println(interest);
                if (post.getTopicId() == interest) {
                    returningPost.add(post);
                    break;
                }
            }
        }

        HashMap<String, String> authorIdName = new HashMap<>();
        for (Post post : returningPost) {
            String authorId = post.getAuthor_id();
            if (authorIdName.containsKey(authorId)) {
                post.setAuthor_id(authorIdName.get(authorId));
            } else {
                User theuser = userRepository.findById(authorId).orElse(null);
                String fname = theuser.getFname();
                String lname = theuser.getLname();
                post.setAuthor_id(fname, lname);
                authorIdName.put(authorId, post.getAuthor_id());
            }
        }

        for (int i = 0; i < returningPost.size(); i++) {
            for (int j = i + 1; j < returningPost.size(); j++) {
                if (returningPost.get(i).getDateTime().compareTo(returningPost.get(j).getDateTime()) > 0) {
                    Post temp = returningPost.get(i);
                    returningPost.set(i, returningPost.get(j));
                    returningPost.set(j, temp);
                }
            }
        }

        return returningPost;
    }

    // method to get all posts with 5 or more reports
    public List<Post> getReportedPosts(String communityId) {
        List<Post> posts = postRepository.findPostByCommunity_id(communityId);
        List<Post> reportedPosts = new ArrayList<>();
        for (Post post : posts) {
            HashMap<String, String> authorIdName = new HashMap<>();
            String authorId = post.getAuthor_id();
            if (authorIdName.containsKey(authorId)) {
                post.setAuthor_id(authorIdName.get(authorId));
            } else {
                User theuser = userRepository.findById(authorId).orElse(null);
                String fname = theuser.getFname();
                String lname = theuser.getLname();
                post.setAuthor_id(fname, lname);
                authorIdName.put(authorId, post.getAuthor_id());
            }
            if (post.getReportedBy().size() >= 5) {
                reportedPosts.add(post);
            }
        }
        return reportedPosts;
    }
    public List<Post> getSharedPostsByUser(String userId) {
        if (userId == null || userId.isBlank()) {
            return Collections.emptyList();
        }
    
        List<Post> sharedPosts = postRepository.findPostsSharedByUser(userId);
    
        // Ensure that it also includes posts shared from global feed
        List<Post> allSharedPosts = new ArrayList<>();
        for (Post post : sharedPosts) {
            // Make sure the original author is correctly resolved
            User originalAuthor = userRepository.findById(post.getAuthor_id()).orElse(null);
            if (originalAuthor != null) {
                post.setAuthor_id(originalAuthor.getFname() + " " + originalAuthor.getLname());
            }
    
            allSharedPosts.add(post);
        }
    
        return allSharedPosts;
    }
    

    public boolean deletePost(Map<String, String> data) {
        try {
            String postId = data.get("postId");
            Post post = postRepository.findById(postId).orElse(null);
            if (post == null) {
                return false;
            }
            if (post.getReportedBy().size() < 5) {
                return false;
            }

            // add notification
            Community community = communityRepository.findById(post.getCommunity_id()).orElse(null);
            String dateTime = java.time.OffsetDateTime.now().toString();
            String receiverId = post.getAuthor_id();
            String message = String.format("Your post is removed from %s", community.getName());
            NotificationService.addNotification(community.getOwner(), receiverId,
                    NotificationType.MODERATOR_REMOVE_POST, message, dateTime);

            postRepository.delete(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean keepPost(Map<String, String> data) {
        try {
            String postId = data.get("postId");
            Post post = postRepository.findById(postId).orElse(null);
            if (post == null) {
                return false;
            }
            List<String> reportedBy = post.getReportedBy();
       
            reportedBy.clear();
            post.setReportedBy(reportedBy);

            Community community = communityRepository.findById(post.getCommunity_id()).orElse(null);
            String dateTime = java.time.OffsetDateTime.now().toString();
            String receiverId = post.getAuthor_id();
            String message = String.format("Your post is kept in %s", community.getName());
            NotificationService.addNotification(community.getOwner(), receiverId,
                    NotificationType.MODERATOR_KEEP_POST, message, dateTime);

            postRepository.save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
public List<Post> searchPosts(String userId, String searchTerm) {
        //System.out.println("Checkpoint 0");
        /*
        User user = userRepository.findById(userId).orElse(null); // NOTE: this line is the slowness bottleneck...
        if (user == null) {
            System.out.println("checkpoint 0.5");
            return Collections.emptyList();
        }
        */
        //System.out.println("checkpoint 1");
        List<CommunityUser> communityUsers = communityUserRepository.findByMemberId(userId);
        List<String> communityIds = new ArrayList<>();
        for (CommunityUser cu : communityUsers) {
            communityIds.add(cu.getCommunityId());
        }
        //System.out.println("Checkpoint 2");
        List<Post> posts = postRepository.searchByTermAndCommunities(searchTerm, communityIds);
        // ADDED START.
        /*
        System.out.println("Checkpoint 3");
        // Step 1: Collect unique author IDs from posts
        Set<String> authorIds = posts.stream()
                .map(Post::getAuthor_id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        System.out.println("Checkpoint 4");
        // Step 2: Fetch all users in one DB call
        List<User> users = userRepository.findAllById(authorIds);
        System.out.println("Checkpoint 5");
        // Step 3: Create a map for quick lookup
        Map<String, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user);
        }
        System.out.println("Checkpoint 6");
        // Step 4: Assign author name to each post
        for (Post post : posts) {
            User author = userMap.get(post.getAuthor_id());
            if (author != null) {
                post.setAuthor_id(author.getFname(), author.getLname());
            }
        }
        System.out.println("Checkpoint 7");

         */
        HashMap<String, String> authorIdName = new HashMap<>();
        for (Post post : posts) {
            //System.out.println("Checkpoint 3");
            String authorId = post.getAuthor_id();
            if (authorIdName.containsKey(authorId)) {
                //System.out.println("Checkpoint 4");
                post.setAuthor_id(authorIdName.get(authorId));
                //System.out.println("Checkpoint 5");
            } else {
                User theuser = userRepository.findById(authorId).orElse(null); // NOTE: this line is the slowness bottleneck...
                String fname = theuser.getFname();
                String lname = theuser.getLname();
                post.setAuthor_id(fname, lname);
                authorIdName.put(authorId, post.getAuthor_id());
            }
        }
        System.out.println("posts size: " + posts.size() + ", search: " + searchTerm); // size 0 indicates problem w/ searchByTermAndCommunities...
        // ADDED END.
        //System.out.println("Checkpoint 3");
        //System.out.println("p1: " + p1.getDateTime());
        //posts.sort((p1, p2) -> p2.getDateTime().compareTo(p1.getDateTime()));
        //System.out.println("Checkpoint 4");
        return posts;
    }
    public List<Post> searchPostsInCommunity(String communityId, String searchTerm) {
        
        List<Post> posts = postRepository.searchByTermInCommunity(searchTerm, communityId);
        
        posts.sort((p1, p2) -> p2.getDateTime().compareTo(p1.getDateTime()));

    return posts;
    }

    public List<Map<String, Object>> getTopicsOfTheDay() {
        List<Post> todayPosts = postRepository.findAll().stream()
                .filter(post -> {
                    String date = post.getDateTime();
                    return date != null && date.startsWith(LocalDate.now().toString()); // e.g., "2025-03-26"
                })
                .collect(Collectors.toList());

        // Count posts per topic
        Map<Integer, Long> topicCountMap = todayPosts.stream()
                .collect(Collectors.groupingBy(Post::getTopicId, Collectors.counting()));

        // Get topic names
        HashMap<Integer, String> topicNames = interestTopicService.getInterestTopic();

        // Convert to response list
        return topicCountMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())) // descending
                .limit(5)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("topicId", entry.getKey());
                    result.put("name", topicNames.getOrDefault(entry.getKey(), "Other"));
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getPopularTopics() {
        List<Post> allPosts = postRepository.findAll().stream().collect(Collectors.toList());

        // Count likes per topic
        // NOTE: likes are a measure of popularity.
        Map<Integer, Long> likesCountMap = allPosts.stream().collect(Collectors.groupingBy(Post::getTopicId, Collectors.summingLong(post -> post.getLikedBy().size())));

        // Get topic names
        HashMap<Integer, String> topicNames = interestTopicService.getInterestTopic();

        // Convert to response list
        return likesCountMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())) // descending
                .limit(3)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("topicId", entry.getKey());
                    result.put("name", topicNames.getOrDefault(entry.getKey(), "Other"));
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<Post> filterPosts(String sharedBy, String likedBy, String communityName) {
        List<Post> allPosts = postRepository.findAll();
    
        List<Post> filtered = allPosts.stream()
            .filter(p -> {
                boolean matchesAny = false;
    
                // If no filters applied, include all posts
                boolean anyFilterApplied = (sharedBy != null && !sharedBy.isEmpty())
                    || (likedBy != null && !likedBy.isEmpty())
                    || (communityName != null && !communityName.isEmpty());
    
                if (!anyFilterApplied) return true;
    
                // 1. Match shared posts
                if (sharedBy != null && !sharedBy.isEmpty()) {
                    List<String> sharedByList = p.getSharedBy();
                    if (sharedByList != null && sharedByList.contains(sharedBy)) {
                        matchesAny = true;
                    }
                }
    
                // 2. Match liked posts
                if (likedBy != null && !likedBy.isEmpty()) {
                    List<String> likedByList = p.getLikedBy();
                    if (likedByList != null && likedByList.contains(likedBy)) {
                        matchesAny = true;
                    }
                }
    
                // 3. Match community name
                if (communityName != null && !communityName.isEmpty()) {
                    List<Community> matchedCommunities = communityRepository.findCommunitiesByName(communityName);
    
                    if (!matchedCommunities.isEmpty()) {
                        List<String> matchedCommunityIds = matchedCommunities.stream()
                                .map(Community::getId)
                                .collect(Collectors.toList());
    
                        if (p.getCommunity_id() != null && matchedCommunityIds.contains(p.getCommunity_id())) {
                            matchesAny = true;
                        }
                    }
                }
    
                return matchesAny;
                })
                .collect(Collectors.toList());
    
            Map<String, String> authorIdName = new HashMap<>();
            for (Post post : filtered) {
                String authorId = post.getAuthor_id();
                if (authorIdName.containsKey(authorId)) {
                    post.setAuthor_id(authorIdName.get(authorId));
                } else {
                    User user = userRepository.findById(authorId).orElse(null);
                    if (user != null) {
                        String formattedName = user.getLname() + ", " + user.getFname();
                        post.setAuthor_id(formattedName);
                        authorIdName.put(authorId, formattedName);
                    }
                }
            }   
        return filtered;
    }
    

}
