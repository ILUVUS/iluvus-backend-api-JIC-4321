package iluvus.backend.api.controller;

import iluvus.backend.api.service.CommunityService;
import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public ResponseEntity<ArrayList<String>> getAllCommunity() {
        return ResponseEntity.ok().body(communityService.getAllCommunity());
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<String>> searchCommunity(@RequestParam String filter) {
        ArrayList<String> communityList = communityService.getAllCommunity();
        ArrayList<String> filteredCommunityList = communityList.stream()
            .filter(community -> community.contains(filter))
            .collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok().body(filteredCommunityList);
    }
}