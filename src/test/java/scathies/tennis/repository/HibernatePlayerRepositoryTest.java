package scathies.tennis.repository;

import configuration.HibernateTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import scathies.tennis.model.Match;
import scathies.tennis.model.Player;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HibernatePlayerRepositoryTest {

    private final HibernateExecutor executor = new HibernateExecutor(new HibernateTestConfiguration().getSessionFactory());

    private final PlayerRepository playerRepository = new HibernatePlayerRepository(executor);

    private Player player = Player.builder().name("P1").build();

    @BeforeEach
    void setUp() {
        var matches = executor.executeQuery(
                session -> session.createQuery("select m from Match m", Match.class)
                        .list()
        );
        matches.forEach(
                match -> executor.execute(
                        session -> session.remove(match)
                )
        );

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
        playerRepository.save(player);

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
        playerRepository.save(player);

        var actualPlayer = executor.executeQuery(
                session -> session.createQuery("select p from Player p where id = :id", Player.class)
                        .setParameter("id", player.getId())
                        .uniqueResult()
        );

        assertThat(actualPlayer).isEqualTo(player);
    }
}