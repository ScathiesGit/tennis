package scathies.tennis.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;
import scathies.tennis.model.Match;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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
    public int save(Match match) {
        executor.execute(session -> session.persist(match));
        return match.getId();
    }

    @Override
    public long numberMatches() {
        return executor.executeQuery(
                session -> session.createQuery("select COUNT(m) from Match m", Long.class)
                        .uniqueResult()
        );
    }

    @Override
    public long numberMatchesByName(String name) {
        return executor.executeQuery(
                session -> session.createQuery("select COUNT(*) from Match m join m.player1 join m.player2"
                                + " where m.player1.name = :playerName or m.player2.name = :playerName", Long.class
                        ).setParameter("playerName", name)
                        .uniqueResult()
        );
    }
}
