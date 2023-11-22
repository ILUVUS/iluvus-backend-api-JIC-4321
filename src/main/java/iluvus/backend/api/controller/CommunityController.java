package iluvus.backend.api.controller;

import iluvus.backend.api.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
