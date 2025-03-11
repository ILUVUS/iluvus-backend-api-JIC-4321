package iluvus.backend.api.service;

import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.CommunityUser;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.CommunityUserRepository;
import iluvus.backend.api.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.InterestTopic;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.util.UserDataCheck;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import javax.mail.*;
import javax.mail.internet.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityUserRepository communityUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${iluvus.email.passwordtoken}")
    private String iluvusEmailPassword;

    public Map<String, String> createUser(Map<String, String> data) {
        try {
            UserDataCheck userDataCheck = new UserDataCheck();
            Map<String, String> newUserCheckResult = userDataCheck.newUserCheck(data, userRepository);

            if (newUserCheckResult.get("error").strip() != "") {
                return newUserCheckResult;
            }

            UserDto userDto = new UserDto();
            userDto.setUsername(data.get("username"));
            userDto.setEmail(data.get("email"));

            String hashedPassword = passwordEncoder.encode(data.get("password"));
            userDto.setPassword(hashedPassword);

            userDto.setFname(data.get("fname"));
            userDto.setLname(data.get("lname"));
            userDto.setGender(data.get("gender"));
            userDto.setDob(data.get("dob"));
            userDto.setRace(data.get("race"));

            userDto.setVerified(Boolean.parseBoolean(data.get("isPro"))
                    && validateEmail(userDto.getEmail()));

            // Initialize empty arrays/lists
            userDto.setNotification(new ArrayList<>());
            userDto.setInterests(new ArrayList<>());
            userDto.setEducation(new ArrayList<>());
            userDto.setWork(new ArrayList<>());
            userDto.setSkills(new ArrayList<>());
            userDto.setHobbies(new ArrayList<>());
            userDto.setFriends(new ArrayList<>());
            userDto.setGroups(new ArrayList<>());

            User user = new User(userDto);
            userRepository.insert(user);
            return newUserCheckResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>() {
                {
                    put("error", "");
                }
            };
        }
    }

    public boolean verify(Map<String, String> data) {
        try {
            User user = userRepository.findById(data.get("userId")).orElse(null);
            return user.isVerified();
        } catch (Exception e) {
            return false;
        }
    }

    public User loginUser(Map<String, String> data) {
        try {
            User user = userRepository.findUserbyUsername(data.get("username"));
            if (passwordEncoder.matches(data.get("password"), user.getPassword())) {
                return user;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String getUserId(String username) {
        try {
            User user = userRepository.findUserbyUsername(username);
            return user.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public User getUserByID(String userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            return optionalUser.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> getUser(String userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return null;
            }

            UserDto userDto = new UserDto(user);
            Map<String, Object> userMap = userDto.getPublicUserInfo();

            // Convert user's interest IDs -> {id -> name}
            Map<Integer, String> interestMap = new HashMap<>();
            for (Integer interestId : user.getInterests()) {
                InterestTopic interestTopic = interestRepository.findInterestTopicById(interestId);
                if (interestTopic != null) {
                    interestMap.put(interestTopic.getId(), interestTopic.getName());
                }
            }
            userMap.put("interest", interestMap);

            // ----------------------------------------------------------
            // ADD this line to include skills in the returned userMap:
            // ----------------------------------------------------------
            userMap.put("skills", user.getSkills());
            userMap.put("image", user.getImage() != null ? user.getImage() : "");


            return userMap;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean validateEmail(String proemail) {
        if (proemail == null) {
            return false;
        }
        String[] parts = proemail.split("@"); // Split at "@"
        if (parts.length == 2) {
            String domain = parts[1];
            String[] domainParts = domain.split("\\.");
            if (domainParts.length == 2) {
                String domainName = domainParts[0];
                String[] genericDomains = { "gmail", "outlook", "yahoo", "hotmail", "aol" };
                boolean isGeneric = false;
                for (String generic : genericDomains) {
                    if (domainName.equalsIgnoreCase(generic)) {
                        isGeneric = true;
                        break;
                    }
                }
                if (isGeneric) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean sendVerificationEmail(String userEmail, int verificationCode) {
        final String sender = "iluvusdonotreply@gmail.com";
        final String password = iluvusEmailPassword;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // 465 for SSL

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Email Verification");
            message.setText("Your verification code is: " + verificationCode);
            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

    public List<HashMap<String, Object>> getNotification(Map<String, String> data) {
        try {
            User user = userRepository.findById(data.get("userId")).orElse(null);
            List<HashMap<String, Object>> notification = user.getNotification();
            return notification;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HashMap<String, Object>> getNotificationByUserId(String userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            List<HashMap<String, Object>> userNotification = user.getNotification();
            return userNotification;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HashMap<String, Object>> getMatchedUser(String filter) {
        try {
            List<User> users = userRepository.findUsersByString(filter);
            List<HashMap<String, Object>> userMapList = new ArrayList<>();
    
            for (User user : users) {
                HashMap<String, Object> userMap = new HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("username", user.getUsername());
                userMap.put("fname", user.getFname());
                userMap.put("lname", user.getLname());
                userMap.put("image", user.getImage());
                userMapList.add(userMap);
            }
            return userMapList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HashMap<String, Object>> getCommunityUsers(String filter, String communityId) {
        try {
            List<User> userList = userRepository.findUsersByUsernameStartingWith(filter);
            List<User> communityUsers = new ArrayList<>();
            for (User user : userList) {
                if (user.getGroups().contains(communityId)) {
                    communityUsers.add(user);
                }
            }
            List<HashMap<String, Object>> userMapList = new ArrayList<>();
            for (User user : communityUsers) {
                UserDto userDto = new UserDto(user);
                HashMap<String, Object> userMap = userDto.getPublicUserInfo();
                userMapList.add(userMap);
            }
            return userMapList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean editBio(Map<String, String> data) {
        try {
            User user = userRepository.findById(data.get("userId")).orElse(null);
            String bio = data.get("bio");
            if (bio == null) {
                throw new IllegalArgumentException("Invalid bio: cannot be null");
            }
            user.setBio(bio);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editInterest(Map<String, String> data) {
        try {
            User user = userRepository.findById(data.get("userId")).orElse(null);
            user.getInterests().clear();

            String interestListRaw = data.get("selectedTopic");
            String[] interestList = interestListRaw.split(",");

            ArrayList<Integer> interestListInt = new ArrayList<>();
            for (String interest : interestList) {
                interestListInt.add(Integer.valueOf(interest));
            }
            user.setInterests(interestListInt);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editProfileImage(Map<String, String> data) {
        try {
            User user = userRepository.findById(data.get("userId")).orElse(null);
            String pic = data.get("image");
            if (pic == null) {
                throw new IllegalArgumentException("Invalid image: cannot be null");
            }
            user.setImage(pic);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> searchUsers(String filter) {
        //Map<String, String> userList = this.getUserInfo();
        // Convert the filter to lowercase for case-insensitive comparison
        //String lowercaseFilter = filter.toLowerCase();


        List<User> users = userRepository.findUsersByString(filter);


        HashMap<String, Object> filteredUserList = new HashMap<>();

        
        //getting the public user info
        for (User user: users) {
            UserDto userDto = new UserDto(user);
            filteredUserList.put(user.getId(), userDto.getPublicUserInfo());
        }

        // for (Community community : communities) {
        //     CommunityDto communityDto = new CommunityDto(community);
        //     filteredCommunityList.put(community.getId(), communityDto.getCommunityPublicInfo());
        // }

        return filteredUserList;
    }


    //add method for adding and removing friends
    
    

    // private Map<String, String> getUserInfo() {
    //     return null;
    // }

    public Map<String, String> getUserFollowingGroups(String userId) {
        try {
            Map<String, String> communityMap = new HashMap<>();
            User user = userRepository.findById(userId).orElse(null);

            List<CommunityUser> communityUsers = communityUserRepository.findByMemberId(user.getId());
            List<String> groups = new ArrayList<>();

            for (CommunityUser communityUser : communityUsers) {
                groups.add(communityUser.getCommunityId());
            }

            if (user != null) {
                for (String communityId : groups) {
                    Community community = communityRepository.findById(communityId).orElse(null);
                    if (community != null) {
                        communityMap.put(community.getId(), community.getName());
                    }
                }
            }
            return communityMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------
    // NEW Method: editSkills
    // --------------------------------------------------
    public boolean editSkills(Map<String, String> data) {
        try {
            // 1. Load the user
            String userId = data.get("userId");
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return false;
            }

            // 2. Clear old skills
            user.getSkills().clear();

            // 3. Parse selected skills
            String skillsRaw = data.get("selectedSkills");
            if (skillsRaw == null || skillsRaw.trim().isEmpty()) {
                // If no skills provided, set an empty list
                user.setSkills(new ArrayList<>());
            } else {
                String[] skillsArray = skillsRaw.split(",");
                List<String> skillList = new ArrayList<>();
                for (String skill : skillsArray) {
                    skillList.add(skill.trim());
                }
                user.setSkills(skillList);
            }

            // 4. Save updated user
            userRepository.save(user);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
