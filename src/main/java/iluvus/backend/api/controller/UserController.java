package iluvus.backend.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import iluvus.backend.api.model.InterestTopic;
import iluvus.backend.api.model.SkillTopic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import iluvus.backend.api.model.User;
import iluvus.backend.api.model.User.UserReport;
import iluvus.backend.api.repository.UserRepository;
import iluvus.backend.api.service.UserService;
import iluvus.backend.api.dto.ReportedUserDto;
import iluvus.backend.api.dto.ReportUserRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
@Autowired
private UserRepository userRepo;

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

    @PostMapping("/moderator/dismissReport")
public ResponseEntity<String> dismissUserReports(@RequestBody Map<String, String> data) {
    String userId = data.get("reportedUserId");
    String communityId = data.get("communityId");

    User user = userRepo.findById(userId).orElse(null);
    if (user == null) return ResponseEntity.badRequest().body("User not found");

    List<User.UserReport> updatedReports = user.getReports().stream()
        .filter(r -> !r.getCommunityId().equals(communityId))
        .collect(Collectors.toList());

    user.setReports(updatedReports);
    userRepo.save(user);
    return ResponseEntity.ok("Reports dismissed");
}
@PostMapping("/moderator/approveReport")
public ResponseEntity<String> approveAndRemoveUser(@RequestBody Map<String, String> data) {
    String userId = data.get("reportedUserId");
    String communityId = data.get("communityId");

    User user = userRepo.findById(userId).orElse(null);
    if (user == null) return ResponseEntity.badRequest().body("User not found");

    // Remove from group
    List<String> updatedGroups = user.getGroups().stream()
        .filter(gid -> !gid.equals(communityId))
        .collect(Collectors.toList());
    user.setGroups(updatedGroups);

    // Remove reports for this community
    List<User.UserReport> updatedReports = user.getReports().stream()
        .filter(r -> !r.getCommunityId().equals(communityId))
        .collect(Collectors.toList());
    user.setReports(updatedReports);

    userRepo.save(user);
    return ResponseEntity.ok("User removed from community and reports approved");
}

    @GetMapping("/moderator/reportedUsers")
    public ResponseEntity<List<ReportedUserDto>> getCommunityReports(@RequestParam String communityId) {
        List<ReportedUserDto> results = userRepo.findAll().stream()
            .filter(u -> u.getReports() != null && u.getReports().stream()
                .anyMatch(r -> r.getCommunityId().equals(communityId)))
            .map(u -> new ReportedUserDto(
                u.getId(),
                u.getUsername(),
                u.getReports().stream()
                    .filter(r -> r.getCommunityId().equals(communityId))
                    .collect(Collectors.toList())))
            .collect(Collectors.toList());
    
        return ResponseEntity.ok(results);
    }
    


@PostMapping("/reportUserOnPost")
public ResponseEntity<String> reportUserOnPost(@RequestBody ReportUserRequest request) {
    User reported = userRepo.findById(request.getReportedUserId()).orElse(null);
    if (reported == null) return ResponseEntity.badRequest().body("User not found");

    List<User.UserReport> reports = reported.getReports();
    if (reports == null) reports = new ArrayList<>();

    boolean alreadyReported = reports.stream().anyMatch(r ->
        r.getReporterId().equals(request.getReporterId()) &&
        r.getCommunityId().equals(request.getCommunityId())
    );
    if (alreadyReported) return ResponseEntity.status(409).body("Already reported");

    reports.add(new User.UserReport(
        request.getReporterId(),
        request.getReason(),
        request.getCommunityId()
    ));

    reported.setReports(reports);
    userRepo.save(reported);
    return ResponseEntity.ok("Report submitted");
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

    @PostMapping(value = "/editProfileImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editProfileImage(@RequestBody Map<String, String> data) {
        boolean isSet = userService.editProfileImage(data);
        if (isSet) {
            return ResponseEntity.ok().body("Profile Image Set Successfully");
        } else {
            return ResponseEntity.badRequest().body("Profile Image Set Failed");
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
// UserController.java
@PostMapping("/blockUser")
public ResponseEntity<String> blockUser(@RequestBody Map<String, String> data) {
    System.out.println("[Backend] Block Request Data: " + data);
    String blockingUserId = data.get("blockingUserId");
    String userToBlockId = data.get("userToBlockId");
    
    System.out.println("[Backend] Blocking User ID: " + blockingUserId);
    System.out.println("[Backend] User to Block ID: " + userToBlockId);

    boolean success = userService.blockUser(blockingUserId, userToBlockId);
    return success 
        ? ResponseEntity.ok("Blocked") 
        : ResponseEntity.badRequest().body("Failed");
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
