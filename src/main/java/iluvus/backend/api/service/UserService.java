package iluvus.backend.api.service;

import iluvus.backend.api.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import iluvus.backend.api.util.SecurityConfig;
import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.InterestTopic;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.util.UserDataCheck;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // get the password from application.properties

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
            userDto.setProEmail(data.get("proEmail"));
            // we have the professional emailID
            // 1) we can call a function for email validity
            userDto.setVerified(validateEmail(userDto.getProEmail()));
            Random random = new Random();
            userDto.setVerifyCode(100000 + random.nextInt(900000));

            // LocationDto locationDto = new LocationDto(data.get("location"));
            // userDto.setLocation(locationDto);

            // need to fix this
            // we need a way to put List in Frontend into a String seperated by commas
            // then we can split the String into a List in Backend
            // FOR NOW, we will just create an empty List
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
            sendVerificationEmail(userDto.getProEmail(), userDto.getVerifyCode());
            return newUserCheckResult;
        } catch (Exception e) {
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
            // Map<String, String> userMap = new HashMap<>();
            // userMap.put("username", user.getUsername());
            // userMap.put("email", user.getEmail());
            // userMap.put("fname", user.getFname());
            // userMap.put("lname", user.getLname());
            // userMap.put("interest", user.getInterests().toString());
            // userMap.put("dob", user.getDob().toString());
            // return userMap;

            UserDto userDto = new UserDto(user);
            Map<String, Object> userMap = userDto.getPublicUserInfo();

            Map<Integer, String> interestMap = new HashMap<>();
            for (Integer interestId : user.getInterests()) {
                InterestTopic interestTopic = interestRepository.findInterestTopicById(interestId);
                if (interestTopic != null) {
                    interestMap.put(interestTopic.getId(), interestTopic.getName());
                }
            }
            userMap.put("interest", interestMap);

            return userMap;

        } catch (Exception e) {
            return null;
        }
    }

    private boolean validateEmail(String proemail) {
        if (proemail == null) {
            return false;
        }

        String[] parts = proemail.split("@"); // Split at the "@" character

        if (parts.length == 2) {
            String username = parts[0];
            String domain = parts[1];
            String[] domainParts = domain.split("\\."); // Split the domain at "."

            if (domainParts.length == 2) {
                String domainName = domainParts[0];
                String domainExtension = domainParts[1];

                String[] genericDomains = { "gmail", "outlook", "yahoo", "hotmail", "aol" };

                // Check if the domain name is generic
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

                String[] result = { username, domainName, domainExtension };
                String verificationToken = UUID.randomUUID().toString(); // random token to verify
                // here I want to verify if the email ID exists. Can I do this by sending a
                // verification code?
                // or is there a method I can use to find out?
                // we can build an email and send a verification link, but how do we know if the
                // link was clicked
                // and where do we direct them
                // api call etc.
                return true;
            }
        }
        return false;
    }

    public boolean sendVerificationEmail(String userEmail, int verificationCode) {
        final String sender = "iluvusapp@gmail.com";

        final String password = iluvusEmailPassword;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Use this line for TLS
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Use 465 for SSL

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
            // get all users matched
            List<User> userList = userRepository.findUsersByUsernameStartingWith(filter);
            List<HashMap<String, Object>> userMapList = new ArrayList<>();
            for (User user : userList) {
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
}
