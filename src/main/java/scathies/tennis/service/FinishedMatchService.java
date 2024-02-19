package scathies.tennis.service;

import scathies.tennis.dto.MatchesPage;
import scathies.tennis.model.Match;

import java.util.List;

public interface FinishedMatchService {

    MatchesPage find(Integer page, Integer pageSize, String playerName);
}
