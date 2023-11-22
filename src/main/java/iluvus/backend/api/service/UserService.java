package iluvus.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.model.*;
import iluvus.backend.api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserDto userDto) {
        User user = new User(userDto);
        userRepository.insert(user);
    }

}
