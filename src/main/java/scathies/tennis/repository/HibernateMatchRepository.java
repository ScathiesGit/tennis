package scathies.tennis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import scathies.tennis.model.Match;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HibernateMatchRepository implements MatchRepository {

    private final HibernateExecutor executor;

    @Override
    public List<Match> findAll(Integer page, Integer pageSize) {
        return executor.executeQuery(
                session -> session.createQuery("select Match from Match", Match.class)
                        .setFirstResult((page - 1) * pageSize + 1)
                        .setMaxResults(pageSize)
                        .list()
        );
    }

    @Override
    public List<Match> findAllByPlayerName(Integer page, Integer pageSize, String playerName) {
        return executor.executeQuery(
                session -> session.createQuery(
                                "select m from Match m join m.player1 join m.player2 " +
                                        "where m.player1 = :playerName1 or m.player2 = :playerName2", Match.class
                        ).setParameter("playerName1", playerName)
                        .setParameter("playerName2", playerName)
                        .setFirstResult((page - 1) * pageSize + 1)
                        .setMaxResults(pageSize)
                        .list()
        );
    }

    @Override
    public void save(Match match) {
        executor.execute(session -> session.persist(match));
    }
}
