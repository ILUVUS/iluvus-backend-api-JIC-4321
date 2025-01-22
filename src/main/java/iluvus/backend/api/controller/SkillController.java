package iluvus.backend.api.controller;

import iluvus.backend.api.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/skill")

public class SkillController {
    //finish this

    @Autowired
    private SkillService skillService;

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getSkill() {
        return ResponseEntity.ok().body(skillService.getSkill());
    }

    @GetMapping(value = "/getByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getSkill(@RequestParam String name) {
        return ResponseEntity.ok().body(skillService.filterSkillTopic(name));
    }

    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, String>> getSkillById(@RequestParam int id) {
        return ResponseEntity.ok().body(skillService.getSkillById(id));
    }


}