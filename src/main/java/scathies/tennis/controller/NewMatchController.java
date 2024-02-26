package scathies.tennis.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import scathies.tennis.model.Player;
import scathies.tennis.service.MatchService;
import scathies.tennis.service.MatchServiceImpl;

@Controller
@RequiredArgsConstructor
@RequestMapping("/new-match")
public class NewMatchController {

    private final MatchService matchService;

    @GetMapping
    public String getNewMatchForm() {
        return "new-match-form";
    }

    @PostMapping
    public String createNewMatch(@NotEmpty String player1,
                                 @NotEmpty String player2) {

        if (player1.equals(player2)) {
            throw new IllegalArgumentException("игроки не могут иметь одинаковых имен");
        }

        return "redirect:/match-score?UUID=%s".formatted(
                matchService.create(player1, player2)
        );
    }
}
