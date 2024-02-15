package scathies.tennis.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scathies.tennis.service.MatchServiceImpl;
import scathies.tennis.service.ScoringServiceImpl;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MatchScoreController {

    private final ScoringServiceImpl scoringService;

    @GetMapping("/match-score")
    public String getMatchScore(@RequestParam(name = "UUID") UUID id) {
        return "match-score";
    }

    @PostMapping("/match-score")
    public String updateMatch(UUID matchId, Integer playerId) {
        scoringService.processMatch(matchId, playerId);
        return "match-score";
    }
}
