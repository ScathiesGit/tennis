package scathies.tennis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scathies.tennis.service.FinishedMatchService;
import scathies.tennis.service.FinishedMatchServiceImpl;

@Controller
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchesController {

    private final FinishedMatchService matchesService;

    @GetMapping
    public String matches() {
        return "matches";
    }

    @PostMapping
    public String findMatches(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "4") Integer pageSize,
                              @RequestParam(required = false) String name,
                              Model model) {
        model.addAttribute("matches", matchesService.find(page, pageSize, name));
        return "matches";
    }
}
