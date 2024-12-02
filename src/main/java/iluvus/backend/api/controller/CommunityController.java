package iluvus.backend.api.controller;

import iluvus.backend.api.service.CommunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCommunity(@RequestBody Map<String, String> data) {

        boolean newCommunity = communityService.createCommunity(data);

        if (newCommunity) {
            return ResponseEntity.ok().body("Community created successfully");
        } else {
            return ResponseEntity.badRequest().body("Community creation failed");
        }

    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getAllCommunity() {
        return ResponseEntity.ok().body(communityService.getAllCommunity());
    }

    @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> joinCommunity(@RequestBody Map<String, String> data) {
        boolean userJoined = communityService.joinCommunity(data);
        if (userJoined) {
            boolean isPublic = communityService.isCommunityPublic(data.get("communityId"));
            if (!isPublic) {
                return ResponseEntity.ok().body("Join request sent successfully");
            } else {
                return ResponseEntity.ok().body("User joined the community successfully");
            }
        } else {
            return ResponseEntity.badRequest().body("Failed to join the community");
        }
    }

    //DeleteMapping for leave community
    @DeleteMapping(value = "/leave")
    public ResponseEntity<String> leaveCommunity(@RequestBody Map<String, String> data) {
        boolean userLeft = communityService.leaveCommunity(data);
        if (userLeft) {
            return ResponseEntity.ok().body("User left the community successfully");
        }
        return ResponseEntity.badRequest().body("Failed to leave the community");

    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> searchCommunity(@RequestParam String filter) {
        Map<String, Object> filteredCommunityList = communityService.searchCommunity(filter);
        return ResponseEntity.ok().body(filteredCommunityList);
    }

    @GetMapping(value = "/getInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> setCommunityInformation(@RequestParam String id) {
        Map<String, Object> communityInfo = communityService.getCommunityInformation(id);
        return ResponseEntity.ok().body(communityInfo);
    }

    @GetMapping(value = "/getVisibility", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getVisibility(@RequestParam String id) {
        boolean isPublic = communityService.isCommunityPublic(id);
        return ResponseEntity.ok().body(isPublic);
    }

    @PostMapping(value = "/approveRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> approveJoinRequest(@RequestBody Map<String, String> data) {
        List<HashMap<String, Object>> requestApproved = communityService.approveJoinRequest(data);
        if (requestApproved != null) {
            return ResponseEntity.ok().body(requestApproved);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/rejectRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> rejectJoinRequest(@RequestBody Map<String, String> data) {
        List<HashMap<String, Object>> requestRejected = communityService.rejectJoinRequest(data);
        if (requestRejected != null) {
            return ResponseEntity.ok().body(requestRejected);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/getPendingRequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> getPendingRequests(@RequestParam String communityId) {
        List<HashMap<String, Object>> pendingRequests = communityService.getPendingJoinRequests(communityId);
        if (pendingRequests != null) {
            return ResponseEntity.ok().body(pendingRequests);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/myCreatedGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> myCreatedGroup(@RequestParam String userId) {
        Map<String, String> myCreatedGroup = communityService.getMyCreatedGroup(userId);
        return ResponseEntity.ok().body(myCreatedGroup);
    }

}