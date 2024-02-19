package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.model.Match;
import scathies.tennis.model.RealtimeMatches;
import scathies.tennis.repository.MatchRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final RealtimeMatches realtimeMatches;

    private final MatchRepository matchRepository;

    private static final int DIFFERENCE_POINT_WIN_GAME = 2;
    private static final int NUMBER_POINT_WIN_GAME = 6;
    private static final int NUMBER_POINT_WIN_GAME_TIEBREAK = 7;

    @Override
    public Match processMatch(UUID matchId, Integer playerId) {
        var match = realtimeMatches.get(matchId);
        if (match.getPlayer1().getId().equals(playerId)) {
            match.setGameScorePlayer1(match.getGameScorePlayer1() + 1);
        } else {
            match.setGameScorePlayer2(match.getGameScorePlayer2() + 1);
        }

        if (match.getGameScorePlayer1() == 6 && match.getGameScorePlayer2() == 6) {
            match.setTiebreak(true);
            return match;
        }
        if (isGameContinue(match)) {
            return match;
        }

        processGameResult(match);
        if (match.getSetScorePlayer1() == 2 || match.getSetScorePlayer2() == 2) {
            match.setIdWinner(playerId);
            realtimeMatches.delete(matchId);
            matchRepository.save(match);
        }
        return match;
    }

    private boolean isGameContinue(Match match) {
        var difference = Math.abs(match.getGameScorePlayer2() - match.getGameScorePlayer1());
        var maxScore = Math.max(match.getGameScorePlayer2(), match.getGameScorePlayer1());
        return !(difference >= DIFFERENCE_POINT_WIN_GAME
                && (maxScore >= NUMBER_POINT_WIN_GAME_TIEBREAK && match.isTiebreak()
                || maxScore >= NUMBER_POINT_WIN_GAME));
    }

    private void processGameResult(Match match) {
        match.setTiebreak(false);
        if (match.getGameScorePlayer2() > match.getGameScorePlayer1()) {
            match.setSetScorePlayer2(match.getSetScorePlayer2() + 1);
        } else {
            match.setSetScorePlayer1(match.getSetScorePlayer1() + 1);
        }
        match.setGameScorePlayer1(0);
        match.setGameScorePlayer2(0);
    }
}
