package scathies.tennis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import scathies.tennis.service.MatchService;
import scathies.tennis.service.MatchServiceImpl;

@Controller
@RequiredArgsConstructor
public class NewMatchController {

    private final MatchService matchService;

    @GetMapping("/new-match")
    public String getNewMatch() {
        return "new-match";
    }

    @PostMapping("/new-match")
    public String createNewMatch(String player1, String player2) {
        return "redirect:/match-score?UUID=%s".formatted(
                matchService.create(player1, player2)
        );
    }
}
