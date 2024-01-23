package iluvus.backend.api.controller;

import iluvus.backend.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody Map<String, String> data) {
        
        boolean newUser = userService.createUser(data);
        
        if (newUser) {
            return ResponseEntity.ok().body("User created successfully");
        } else {
            return ResponseEntity.badRequest().body("User creation failed");
        }

    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkLogin(@RequestBody Map<String, String> data) {
        boolean userExists = userService.loginUser(data);
        if (userExists) {
            // String userId = userService.getUserId(data.get("username"));
            return ResponseEntity.ok().body(data.get("username"));
        } else {
            return ResponseEntity.badRequest().body("User logined failed");
        }
    }

    @PostMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkVerification(@RequestBody Map<String, String> data) {
        boolean isVerified = userService.verify(data);

        if (isVerified) {
            return ResponseEntity.ok().body("Verified");
        } else {
            return ResponseEntity.badRequest().body("Not Verified");
        }
    }
    
}
