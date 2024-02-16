package scathies.tennis.service;

import java.util.UUID;

public interface ScoringService {

    void processMatch(UUID matchId, Integer playerId);
}
