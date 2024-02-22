package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.model.Match;
import scathies.tennis.model.Player;
import scathies.tennis.repository.PlayerRepository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final PlayerRepository playerRepository;

    private final ConcurrentHashMap<UUID, Match> matches = new ConcurrentHashMap<>();

    @Override
    public Match get(UUID id) {
        return matches.get(id);
    }

    @Override
    public UUID create(String playerName1, String playerName2) {
        var id = UUID.randomUUID();
        matches.put(
                id,
                Match.builder()
                        .player1(handlePlayer(playerName1))
                        .player2(handlePlayer(playerName2))
                        .build()
        );
        return id;
    }

    @Override
    public void delete(UUID id) {
        matches.remove(id);
    }

    private Player handlePlayer(String name) {
        return playerRepository.findByName(name)
                .orElseGet(() -> playerRepository.save(name));
    }
}
