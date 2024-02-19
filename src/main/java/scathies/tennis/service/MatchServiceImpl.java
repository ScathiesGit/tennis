package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.model.Match;
import scathies.tennis.model.Player;
import scathies.tennis.model.RealtimeMatches;
import scathies.tennis.repository.PlayerRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final PlayerRepository playerRepository;

    private final RealtimeMatches realtimeMatches;

    @Override
    public Match get(UUID id) {
        return realtimeMatches.get(id);
    }

    @Override
    public UUID create(String playerName1, String playerName2) {
        var id = UUID.randomUUID();
        realtimeMatches.add(
                Match.builder()
                        .id(id)
                        .player1(handlePlayer(playerName1))
                        .player2(handlePlayer(playerName2))
                        .build()
        );
        return id;
    }

    private Player handlePlayer(String name) {
        return playerRepository.findByName(name)
                .orElseGet(() -> playerRepository.save(name));
    }
}
