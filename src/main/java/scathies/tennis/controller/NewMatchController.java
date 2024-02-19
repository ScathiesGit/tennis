package scathies.tennis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import scathies.tennis.service.MatchService;
import scathies.tennis.service.MatchServiceImpl;

@Controller
@RequiredArgsConstructor
@RequestMapping("/new-match")
public class NewMatchController {

    private final MatchService matchService;

    @GetMapping
    public String getNewMatch() {
        return "new-match";
    }

    @PostMapping
    public String createNewMatch(String player1, String player2) {
        return "redirect:/match-score?UUID=%s".formatted(
                matchService.create(player1, player2)
        );
    }
}
