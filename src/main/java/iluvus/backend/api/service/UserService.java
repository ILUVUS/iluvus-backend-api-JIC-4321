package iluvus.backend.api.service;

import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.util.UserDataCheck;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
            userDto.setPassword(data.get("password"));
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

            // simple checking for now
            if (user.getPassword().equals(data.get("password"))) {
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
            System.out.println("Verification email sent successfully...");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
}
