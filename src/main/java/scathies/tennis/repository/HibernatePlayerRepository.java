package scathies.tennis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import scathies.tennis.model.Player;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernatePlayerRepository implements PlayerRepository {

    private final HibernateExecutor executor;

    @Override
    public Optional<Player> findByName(String name) {
        return executor.executeQuery(
                session -> session.createQuery("select p from Player p where p.name = :name", Player.class)
                        .setParameter("name", name)
                        .uniqueResultOptional()
        );
    }

    @Override
    public Player save(Player player) {
        executor.execute(session -> session.persist(player));
        return player;
    }
}
