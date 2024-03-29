package scathies.tennis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scathies.tennis.service.MatchService;
import scathies.tennis.service.ScoringService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/match-score")
public class MatchScoreController {

    private final ScoringService scoringService;

    private final MatchService matchService;

    @GetMapping
    public String getMatchScore(@RequestParam(name = "UUID") UUID id,
                                Model model) {

        model.addAttribute("uuid", id);
        model.addAttribute("match", matchService.get(id));
        return "match-score";
    }

    @PostMapping
    public String updateMatch(UUID matchId, Integer playerId, Model model) {
        model.addAttribute("uuid", matchId);
        model.addAttribute("match", scoringService.processMatch(matchId, playerId));
        return "match-score";
    }
}
