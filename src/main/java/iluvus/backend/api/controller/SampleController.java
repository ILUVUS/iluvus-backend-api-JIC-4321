package iluvus.backend.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    
    @GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }
}
