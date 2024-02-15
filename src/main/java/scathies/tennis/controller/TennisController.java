package scathies.tennis.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TennisController {

    @GetMapping("/")
    public String rootPage() {
        return "tennis";
    }
}
