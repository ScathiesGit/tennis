package scathies.tennis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import scathies.tennis.model.Match;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HibernateMatchRepository implements MatchRepository {

    private final HibernateExecutor executor;

    @Override
    public List<Match> findAll(Integer page, Integer pageSize) {
        return executor.executeQuery(
                session -> session.createQuery("select m from Match m", Match.class)
                        .setFirstResult((page - 1) * pageSize)
                        .setMaxResults(pageSize)
                        .list()
        );
    }

    @Override
    public List<Match> findAllByPlayerName(Integer page, Integer pageSize, String playerName) {
        return executor.executeQuery(
                session -> session.createQuery(
                                "select m from Match m join m.player1 join m.player2 "
                                        + "where m.player1.name = :playerName1 or m.player2.name = :playerName2", Match.class
                        ).setParameter("playerName1", playerName)
                        .setParameter("playerName2", playerName)
                        .setFirstResult((page - 1) * pageSize)
                        .setMaxResults(pageSize)
                        .list()
        );
    }

    @Override
    public UUID save(Match match) {
        executor.execute(session -> session.persist(match));
        return match.getId();
    }

    @Override
    public long numberMatches(String name) {
        var count = 0L;
        if (name == null || name.isEmpty()) {
            count = executor.executeQuery(
                    session -> session.createQuery(
                            "select COUNT(m) from Match m", Long.class
                    ).uniqueResult()
            );
        } else {
            count = executor.executeQuery(
                    session -> session.createQuery(
                                    "select COUNT(*) from Match m join m.player1 join m.player2 "
                                            + "where m.player1.name = :playerName or m.player2.name = :playerName", Long.class
                            ).setParameter("playerName", name)
                            .uniqueResult()
            );
        }
        return count;
    }
}
