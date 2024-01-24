package iluvus.backend.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.util.UserDataCheck;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Map<String, String> createUser(Map<String, String> data) {

        try {

            UserDataCheck userDataCheck = new UserDataCheck();

            Map<String, String> newUserCheckResult =
                    userDataCheck.newUserCheck(data, userRepository);

            if (newUserCheckResult.get("error") != null || newUserCheckResult.get("error") != "") {
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
            userDto.setPosts(new ArrayList<>());
            userDto.setFriends(new ArrayList<>());
            userDto.setGroups(new ArrayList<>());
            User user = new User(userDto);
            userRepository.insert(user);
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
            User user = userRepository.findUserbyUsername(data.get("username"));

            if (user.isVerified()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loginUser(Map<String, String> data) {
        try {
            User user = userRepository.findUserbyUsername(data.get("username"));

            // simple checking for now
            if (user.getPassword().equals(data.get("password"))) {
                // return true;
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
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

                String[] genericDomains = {"gmail", "outlook", "yahoo", "hotmail", "aol"};

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

                String[] result = {username, domainName, domainExtension};
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
}
