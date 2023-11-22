package iluvus.backend.api.service;

import iluvus.backend.api.dto.LocationDto;
import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

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
             userDto.setVerified(false);
             userDto.setFname(data.get("fname"));
             userDto.setLname(data.get("lname"));
             userDto.setGender(data.get("gender"));
             userDto.setDob(data.get("dob"));
             userDto.setRace(data.get("race"));

             LocationDto locationDto = new LocationDto(data.get("location"));
             userDto.setLocation(locationDto);
            
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

}
