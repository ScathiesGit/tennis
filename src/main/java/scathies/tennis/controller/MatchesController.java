package scathies.tennis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scathies.tennis.dto.MatchesPage;
import scathies.tennis.service.FinishedMatchService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchesController {

    private final FinishedMatchService matchesService;

    @GetMapping
    public String matches(Model model) {
        model.addAttribute("matchesPage", new MatchesPage());
        return "matches";
    }

    @PostMapping
    public String findMatches(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "8") Integer pageSize,
                              @RequestParam(required = false) String name,
                              Model model) {

        var matchesPage = (name == null || name.isEmpty())
                ? matchesService.findAll(page, pageSize)
                : matchesService.findAllByName(page, pageSize, name);

        model.addAttribute("matchesPage", matchesPage);
        model.addAttribute("name", name);
        return "matches";
    }
}
