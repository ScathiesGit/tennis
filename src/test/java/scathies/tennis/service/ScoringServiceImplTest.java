package scathies.tennis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import scathies.tennis.model.Match;
import scathies.tennis.model.Player;
import scathies.tennis.repository.MatchRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ScoringServiceImplTest {

    @Mock
    private MatchService matchService;
    @Mock MatchRepository matchRepository;
    @InjectMocks
    private ScoringServiceImpl scoringService;

    private final Player player1 = new Player(1, "P1");
    private final Player player2 = new Player(2, "P2");
    private final Match match = Match.builder()
            .player1(player1)
            .player2(player2)
            .build();

    @Test
    void whenNotTiebreakAndDiffPointsTwoAndMinPointsGreaterFourThenSetIsWon() {
        match.setSetScorePlayer1(0);
        match.setSetScorePlayer2(0);
        match.setGameScorePlayer1(5);
        match.setGameScorePlayer2(6);
        doReturn(match).when(matchService).get(any());

        scoringService.processMatch(UUID.randomUUID(), player2.getId());

        assertAll(
                () -> assertThat(match).extracting("GameScorePlayer1").isEqualTo(0),
                () -> assertThat(match).extracting("GameScorePlayer2").isEqualTo(0),
                () -> assertThat(match).extracting("setScorePlayer2").isEqualTo(1)
        );
    }

    @Test
    void whenTiebreakAndDiffPointsTwoThenSetIsWon() {
        match.setSetScorePlayer1(0);
        match.setSetScorePlayer2(0);
        match.setGameScorePlayer1(7);
        match.setGameScorePlayer2(8);
        match.setTiebreak(true);
        doReturn(match).when(matchService).get(any());

        scoringService.processMatch(UUID.randomUUID(), player2.getId());

        assertAll(
                () -> assertThat(match).extracting("GameScorePlayer1").isEqualTo(0),
                () -> assertThat(match).extracting("GameScorePlayer2").isEqualTo(0),
                () -> assertThat(match).extracting("setScorePlayer2").isEqualTo(1),
                () -> assertFalse(match.isTiebreak())
        );
    }

    @Test
    void whenGoesToTiebreak() {
        match.setSetScorePlayer1(0);
        match.setSetScorePlayer2(0);
        match.setGameScorePlayer1(5);
        match.setGameScorePlayer2(6);
        doReturn(match).when(matchService).get(any());

        scoringService.processMatch(UUID.randomUUID(), player1.getId());

        assertAll(
                () -> assertTrue(match.isTiebreak()),
                () -> assertThat(match).extracting("setScorePlayer1").isEqualTo(0),
                () -> assertThat(match).extracting("setScorePlayer2").isEqualTo(0)
        );
    }

    @Test
    void whenDiffPointsLessTwoThenGameIsContinue() {
        match.setSetScorePlayer1(0);
        match.setSetScorePlayer2(0);
        match.setGameScorePlayer1(7);
        match.setGameScorePlayer2(7);
        doReturn(match).when(matchService).get(any());

        scoringService.processMatch(UUID.randomUUID(), player1.getId());

        assertAll(
                () -> assertThat(match).extracting("setScorePlayer1").isEqualTo(0),
                () -> assertThat(match).extracting("setScorePlayer2").isEqualTo(0),
                () -> assertThat(match).extracting("idWinner").isNull()
        );
    }

    @Test
    void whenTwoSetsWonThenWinnerFound() {
        match.setSetScorePlayer1(1);
        match.setSetScorePlayer2(1);
        match.setGameScorePlayer1(5);
        match.setGameScorePlayer2(6);
        doReturn(match).when(matchService).get(any());

        scoringService.processMatch(UUID.randomUUID(), player2.getId());

        assertThat(match).extracting("idWinner").isEqualTo(player2.getId());
    }
}