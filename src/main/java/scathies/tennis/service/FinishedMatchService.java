package scathies.tennis.service;

import scathies.tennis.dto.MatchesPage;

public interface FinishedMatchService {

    MatchesPage findAll(Integer page, Integer pageSize);

    MatchesPage findAllByName(Integer page, Integer pageSize, String playerName);
}
