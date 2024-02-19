package iluvus.backend.api.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import iluvus.backend.api.model.User;
import iluvus.backend.api.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody Map<String, String> data) {

        Map<String, String> newUser = userService.createUser(data);

        String newUserRes = newUser.get("error");

        // if all the error field is empty, then the user is created successfully
        if (newUserRes == null || newUserRes.strip() == "") {
            return ResponseEntity.ok().body("User created successfully");
        } else {
            return ResponseEntity.badRequest()
                    .body("Please check the following fields: \n\n" + newUserRes);
        }

    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkLogin(@RequestBody Map<String, String> data) {
        User userExists = userService.loginUser(data);
        if (userExists != null) {
            return ResponseEntity.ok().body(userExists.getId());
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

    @PostMapping(value = "/sendEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        int verificationCode = Integer.parseInt(data.get("verificationCode"));
        boolean isSent = userService.sendVerificationEmail(email, verificationCode);

        if (isSent) {
            return ResponseEntity.ok().body("Successfully sent Verification Email");
        } else {
            return ResponseEntity.badRequest().body("Unsuccessful to send Email.");
        }
    }

}
