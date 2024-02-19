package scathies.tennis.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scathies.tennis.service.MatchServiceImpl;
import scathies.tennis.service.ScoringService;
import scathies.tennis.service.ScoringServiceImpl;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/match-score")
public class MatchScoreController {

    private final ScoringService scoringService;

    @GetMapping
    public String getMatchScore(@RequestParam(name = "UUID") UUID id) {
        return "match-score";
    }

    @PostMapping
    public String updateMatch(UUID matchId, Integer playerId, Model model) {
        model.addAttribute("match", scoringService.processMatch(matchId, playerId));
        return "match-score";
    }
}
