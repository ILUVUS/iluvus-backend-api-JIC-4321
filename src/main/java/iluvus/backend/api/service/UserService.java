package iluvus.backend.api.service;

// import iluvus.backend.api.dto.LocationDto;
import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUser(Map<String, String> data) {
        try {
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
            //we have the professional emailID
            //1) we can call a function for email validity
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
            return true;
        } catch (Exception e) {
            return false;
        }

    }
  
    public boolean loginUser(Map<String, String> data) {
        try {
            User user = userRepository.findUserbyUsername(data.get("username"));

            // simple checking for now
            if (user.getPassword().equals(data.get("password"))) {
                return true;
            } 
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean validateEmail(String proemail) {
        if (proemail == null) {
            return false;
        }

        String[] parts = proemail.split("@"); // Split at the "@" character

        final String[] GENERIC_DOMAINS = { "gmail", "outlook", "yahoo", "hotmail", "aol" };
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)$";
        final String DOMAIN_REGEX = "^[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (proEmail == null || !proEmail.matches(EMAIL_REGEX)) {
            return false;
        }

        String[] parts = proEmail.split("@");
        if (parts.length != 2) {
            return false;
        }
        String domain = parts[1];
        if (!domain.matches(DOMAIN_REGEX)) {
            return false;
        }
        if (isGenericDomain(domain, GENERIC_DOMAINS)) {
            return false;
        }
        if (domain.endsWith(".edu")) {
            return true;
        }
        Pattern.matches("[A-Za-z]+\\.[A-Za-z]+", parts[0]);
        //need more criteria to identify fake emailIDs
        return true;
    }

    private boolean isGenericDomain(String domain, String[] GENERIC_DOMAINS) {
        String domainName = domain.split("\\.")[0];
        for (String generic : GENERIC_DOMAINS) {
            if (domainName.equalsIgnoreCase(generic)) {
                return true;
            }
        }
        return false;
    }
}
