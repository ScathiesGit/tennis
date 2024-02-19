package scathies.tennis.service;

import scathies.tennis.model.Match;

import java.util.UUID;

public interface ScoringService {

    Match processMatch(UUID matchId, Integer playerId);
}
