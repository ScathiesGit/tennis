package scathies.tennis.service;

import scathies.tennis.model.Match;

import java.util.List;

public interface FinishedMatchService {

    List<Match> find(Integer page, Integer pageSize, String playerName);
}
