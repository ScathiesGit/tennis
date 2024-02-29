package scathies.tennis.service;

import scathies.tennis.model.Match;

import java.util.UUID;

public interface MatchService {

    Match get(UUID id);

    UUID create(String playerName1, String playerName2);

    void delete(UUID id);

}
