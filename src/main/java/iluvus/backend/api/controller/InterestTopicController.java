package iluvus.backend.api.controller;

import iluvus.backend.api.model.InterestTopic;
import iluvus.backend.api.model.User;
import iluvus.backend.api.service.InterestTopicService;
import iluvus.backend.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interest")
public class InterestTopicController {

    @Autowired
    private InterestTopicService interestTopicService;

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getInterestTopic() {
        return ResponseEntity.ok().body(interestTopicService.getInterestTopic());
    }

}
