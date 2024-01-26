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

import java.util.ArrayList;
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
    public ResponseEntity<ArrayList<String>> getAllCommunity() {
        return ResponseEntity.ok().body(communityService.getAllCommunity());
    }

    @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> joinCommunity(@RequestBody Map<String, String> userData,
                                                @RequestBody Map<String, String> communityData) {
        try {
            String userId = userData.get("userId");
            String communityId = communityData.get("communityId");
            communityService.joinCommunity(userId, communityId);
            return ResponseEntity.ok().body("User join the community successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User failed to join the community");
        }
    }
}
