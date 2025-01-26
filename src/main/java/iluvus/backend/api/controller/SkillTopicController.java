package iluvus.backend.api.controller;

import iluvus.backend.api.service.SkillTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/skill")
public class SkillTopicController {

    @Autowired
    private SkillTopicService skillTopicService;

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getSkillTopic() {
        return ResponseEntity.ok().body(skillTopicService.getSkillTopic());
    }

    @GetMapping(value = "/getByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getSkillTopic(@RequestParam String name) {
        return ResponseEntity.ok().body(skillTopicService.filterSkillTopic(name));
    }

    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getSkillTopicById(@RequestParam int id) {
        return ResponseEntity.ok().body(skillTopicService.getSkillTopicById(id));
    }
}
