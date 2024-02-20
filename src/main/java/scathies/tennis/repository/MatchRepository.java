package scathies.tennis.repository;

import scathies.tennis.model.Match;

import java.util.List;
import java.util.UUID;

public interface MatchRepository {

    List<Match> findAll(Integer page, Integer pageSize);

    List<Match> findAllByPlayerName(Integer page, Integer pageSize, String playerName);

    int save(Match match);

    long numberMatches(String name);
}
