package scathies.tennis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scathies.tennis.service.FinishedMatchServiceImpl;

@Controller
@RequiredArgsConstructor
public class MatchesController {

    private final FinishedMatchServiceImpl matchesService;

    @GetMapping("/matches")
    public String matches(@RequestParam(required = false, defaultValue = "1") Integer page,
                          @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                          @RequestParam(required = false) String name) {
        matchesService.find(page, pageSize, name);
        return "matches";
    }
}
