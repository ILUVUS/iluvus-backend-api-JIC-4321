package iluvus.backend.api.controller;

import iluvus.backend.api.service.InterestTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/interest")
public class InterestTopicController {

    @Autowired
    private InterestTopicService interestTopicService;

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getInterestTopic() {
        return ResponseEntity.ok().body(interestTopicService.getInterestTopic());
    }

    @GetMapping(value = "/getByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getInterestTopic(@RequestParam String name) {
        return ResponseEntity.ok().body(interestTopicService.filterInterestTopic(name));
    }

    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getInterestTopicById(@RequestParam int id) {
        return ResponseEntity.ok().body(interestTopicService.getInterestTopicById(id));
    }

}
