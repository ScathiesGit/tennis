package scathies.tennis.repository;

import scathies.tennis.model.Match;

import java.util.List;

public interface MatchRepository {

    List<Match> findAll(Integer page, Integer pageSize);

    List<Match> findAllByPlayerName(Integer page, Integer pageSize, String playerName);

    void save(Match match);
}
