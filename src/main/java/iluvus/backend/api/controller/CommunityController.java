package iluvus.backend.api.controller;

import iluvus.backend.api.service.CommunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String userId = data.get("userId");
        String communityId = data.get("communityId");
        boolean userJoined = communityService.joinCommunity(userId, communityId);
        if (userJoined) {
            boolean isPublic = communityService.isCommunityPublic(communityId);
            if (!isPublic) {
                return ResponseEntity.ok().body("Join request sent successfully");
            } else {
                return ResponseEntity.ok().body("User joined the community successfully");
            }
        } else {
            return ResponseEntity.badRequest().body("Failed to join the community");
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> searchCommunity(@RequestParam String filter) {
        Map<String, String> communityList = communityService.getCommunityInfo();

        // Convert the filter to lowercase for case-insensitive comparison
        String lowercaseFilter = filter.toLowerCase();

        // Filter the communityList based on the specified filter (case-insensitive)
        Map<String, String> filteredCommunityList = communityList.entrySet()
                .stream()
                .filter(entry -> entry.getValue().toLowerCase().contains(lowercaseFilter))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return ResponseEntity.ok().body(filteredCommunityList);
    }

    @GetMapping(value = "/getInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> setCommunityInformation(@RequestParam String id) {
        Map<String, String> communityInfo = communityService.getCommunityInformation(id);
        return ResponseEntity.ok().body(communityInfo);
    }

    @GetMapping(value = "/getVisibility", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getVisibility(@RequestParam String id) {
        boolean isPublic = communityService.isCommunityPublic(id);
        return ResponseEntity.ok().body(String.valueOf(isPublic));
    }

    @PostMapping(value = "/approveRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approveJoinRequest(@RequestBody Map<String, String> data) {
        String userId = data.get("userId");
        String communityId = data.get("communityId");
        boolean requestApproved = communityService.approveJoinRequest(userId, communityId);
        if (requestApproved) {
            return ResponseEntity.ok().body("Join request approved successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to approve join request");
        }
    }

    @PostMapping(value = "/rejectRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> rejectJoinRequest(@RequestBody Map<String, String> data) {
        String userId = data.get("userId");
        String communityId = data.get("communityId");
        boolean requestRejected = communityService.rejectJoinRequest(userId, communityId);
        if (requestRejected) {
            return ResponseEntity.ok().body("Join request rejected successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to reject join request");
        }
    }

    @GetMapping(value = "/getPendingRequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getPendingRequests(@RequestParam String communityId) {
        List<String> requestList = communityService.getPendingRequests(communityId);
        return ResponseEntity.ok().body(requestList);
    }


}