package iluvus.backend.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import iluvus.backend.api.model.InterestTopic;
import iluvus.backend.api.model.SkillTopic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import iluvus.backend.api.model.User;
import iluvus.backend.api.service.UserService;
import org.springframework.web.multipart.MultipartFile;


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

    @GetMapping(value = "/getBlockedUsers", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Map<String, Object>>> getBlockedUsers(@RequestParam String userId) {
    List<Map<String, Object>> blocked = userService.getBlockedUsers(userId);
    return ResponseEntity.ok().body(blocked);
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

  @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Map<String, Object>> getUser(
    @RequestParam String userId,
    @RequestParam String viewerId
) {
    Map<String, Object> result = userService.getUser(userId, viewerId);
    if (result == null) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    return ResponseEntity.ok(result);
}

    @PostMapping(value = "/getNotification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> getNotification(@RequestBody Map<String, String> data) {
        List<HashMap<String, Object>> notifications = userService.getNotification(data);
        if (notifications != null) {
            return ResponseEntity.ok().body(notifications);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/getNotificationByUserId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> getNotificationByUserId(@RequestParam String userId) {
        List<HashMap<String, Object>> userNotifications = userService.getNotificationByUserId(userId);
        if (userNotifications != null && !userNotifications.isEmpty()) {
            return ResponseEntity.ok().body(userNotifications);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> searchUser(@RequestParam String filter) {
        List<HashMap<String, Object>> userList = userService.getMatchedUser(filter);
        if (userList != null) {
            for (HashMap<String, Object> user : userList) {
                user.put("avatar", user.get("image") != null ? user.get("image") : "");
            }
            return ResponseEntity.ok().body(userList);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/searchUsersInCommunity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> searchUser(@RequestParam String filter,
            @RequestParam String communityId) {
        List<HashMap<String, Object>> userList = userService.getCommunityUsers(filter, communityId);
        if (userList != null) {
            return ResponseEntity.ok().body(userList);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/user/search", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<HashMap<String, Object>>> searchUsers(@RequestParam String filter) {
    List<HashMap<String, Object>> userList = userService.getMatchedUser(filter);
    if (userList != null) {
        return ResponseEntity.ok().body(userList);
    } else {
        return ResponseEntity.badRequest().body(null);
    }
}


    @GetMapping(value = "/interestList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getInterestTopic() {
        List<String> interestTopic = InterestTopic.topicList;
        return ResponseEntity.ok().body(interestTopic);
    }

    @GetMapping(value = "/skillList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getSkillTopic() {
        List<String> skillTopic = SkillTopic.skillList;
        return ResponseEntity.ok().body(skillTopic);
    }

    @PostMapping(value = "/editBio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editBio(@RequestBody Map<String, String> data) {
        boolean isSet = userService.editBio(data);
        if (isSet) {
            return ResponseEntity.ok().body("Profile Image Set Successfully");
        } else {
            return ResponseEntity.badRequest().body("Profile Image Set Failed");
        }
    }

    @PostMapping(value = "/editInterest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editInterest(@RequestBody Map<String, String> data) {
        boolean isSet = userService.editInterest(data);
        if (isSet) {
            return ResponseEntity.ok().body("Interest Set Successfully");
        } else {
            return ResponseEntity.badRequest().body("Interest Set Failed");
        }
    }

    //addFriend, removeFriend, approve friend mappings

    //add method block users


    @PostMapping(value = "/editSkill", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editSkill(@RequestBody Map<String, String> data) {
        boolean isSet = userService.editSkills(data);
        if(isSet) {
            return ResponseEntity.ok().body("Skill Set Successfully");
        } else {
            return ResponseEntity.badRequest().body("Skill Set Failed");
        }
    }

    //Will need to add editInterest and interestList later tomorrow potentially
    //Also add method descriptions


    @PostMapping(value = "/editProfileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> editProfileImage(
            @RequestParam("userId") String userId,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            String imageUrl = userService.uploadProfileImage(userId, imageFile);
            return ResponseEntity.ok().body(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload image");
        }
    }
    
   
    @GetMapping(value = "/getMyFollowingGroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getMyFollowingGroups(@RequestParam String userId) {
        Map<String, String> followingGroups = userService.getUserFollowingGroups(userId);
        if (followingGroups != null) {
            return ResponseEntity.ok().body(followingGroups);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


    //--------NEW: Post Mapping Blocking User---------
    //might need to change this to be @PathVariable instead
    @PostMapping(value = "/blockUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> blockUser(@RequestBody Map<String, String> data) {
        String blockingUserId = data.get("blockingUserId");
        String userToBlockId = data.get("userToBlockId");
    
        boolean successfullyBlockedUser = userService.blockUser(blockingUserId, userToBlockId);
        if (successfullyBlockedUser) {
            return ResponseEntity.ok().body("Successfully blocked user");
        } else {
            return ResponseEntity.badRequest().body("Failed to block user");
        }
    }
    

    //-------NEW: Post Mapping Unblocking User----------
    //might need to change this to be @PathVariable instead
    @PostMapping(value = "/unblockUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> unblockUser(@RequestParam String unblockingUser, @RequestParam String userToUnblock) {

        boolean successfullyUnblocked = userService.unblockUser(unblockingUser, userToUnblock);
        if (successfullyUnblocked) {
            return ResponseEntity.ok().body("Successfully unblocked user");
        } else {
            return ResponseEntity.badRequest().body("Failed to unblock user");
        }
    }
    
}
