package scathies.tennis.repository;

import configuration.HibernateTestConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scathies.tennis.model.Player;

import static org.assertj.core.api.Assertions.assertThat;

class HibernatePlayerRepositoryTest {

    private HibernateExecutor executor = new HibernateExecutor(new HibernateTestConfiguration().getSessionFactory());
    private PlayerRepository playerRepository = new HibernatePlayerRepository(executor);

    @BeforeEach
    void setUp() {
        var players = executor.executeQuery(
                session -> session.createQuery("select p from Player p", Player.class)
                        .list()
        );
        players.forEach(
                player -> executor.execute(
                        session -> session.remove(player)
                )
        );
    }

    @Test
    void givenExistNameWhenFindByNameThenReturnPlayer() {
        var player = playerRepository.save("P1");

        var actualPlayer = playerRepository.findByName(player.getName()).get();

        assertThat(actualPlayer).isEqualTo(player);
    }

    @Test
    void givenNotExistNameWhenFindByNameThenReturnEmpty() {
        var player = new Player(1, "A");

        var actualPlayer = playerRepository.findByName(player.getName());

        assertThat(actualPlayer).isEmpty();
    }

    @Test
    void whenSavePlayerThenFindById() {
        var player = playerRepository.save("P1");

        var actualPlayer = executor.executeQuery(
                session -> session.createQuery("select p from Player p where id = :id", Player.class)
                        .setParameter("id", player.getId())
                        .uniqueResult()
        );

        assertThat(actualPlayer).isEqualTo(player);
    }
}