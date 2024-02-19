package scathies.tennis.repository;

import configuration.HibernateConfigTest;
import configuration.LiquibaseConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import scathies.tennis.model.Match;
import scathies.tennis.model.Player;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class HibernateMatchRepositoryTest {

    private HibernateExecutor executor = new HibernateExecutor(new HibernateConfigTest().getSessionFactory());
    private HibernateMatchRepository matchRepository = new HibernateMatchRepository(executor);
    private HibernatePlayerRepository playerRepository = new HibernatePlayerRepository(executor);

    private Player player1;
    private Player player2;
    private Player player3;
    private Match match1 = Match.builder()
            .id(UUID.randomUUID())
            .build();
    private Match match2 = Match.builder()
            .id(UUID.randomUUID())
            .build();

    @BeforeEach
    void setUp() {
        LiquibaseConfigTest.executeSqlScripts();
        var matches = executor.executeQuery(
                session -> session.createQuery("select m from Match m", Match.class).list()
        );

        matches.forEach(
                match -> executor.execute(
                        session -> session.remove(match)
                )
        );
    }

    @Test
    void whenFindAllThenReturnAllMatches() {
        player1 = playerRepository.save("P1");
        player2 = playerRepository.save("P2");
        player3 = playerRepository.save("P3");
        match1.setPlayer1(player1);
        match1.setPlayer2(player2);
        match1.setIdWinner(player1.getId());
        match2.setPlayer1(player1);
        match2.setPlayer2(player3);
        match2.setIdWinner(player1.getId());
        matchRepository.save(match1);
        matchRepository.save(match2);

        var matches = matchRepository.findAll(1, 5);

        assertThat(matches)
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(match1, match2));
    }

    @Test
    void whenFindAllByPlayerNameThenReturnAllMatchesByName() {
        player1 = playerRepository.save("P1");
        player2 = playerRepository.save("P2");
        player3 = playerRepository.save("P3");
        match1.setPlayer1(player1);
        match1.setPlayer2(player2);
        match1.setIdWinner(player1.getId());
        match2.setPlayer1(player1);
        match2.setPlayer2(player2);
        match2.setIdWinner(player2.getId());
        var match3 = Match.builder()
                .id(UUID.randomUUID())
                .player1(player2)
                .player2(player3)
                .idWinner(player3.getId())
                .build();
        matchRepository.save(match1);
        matchRepository.save(match2);
        matchRepository.save(match3);

        var matches = matchRepository.findAllByPlayerName(1, 5, player1.getName());

        assertThat(matches)
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(match1, match2));
    }

    @Test
    void givenExistPlayersWhenSaveMatchThenFindById() {
        player1 = playerRepository.save("P1");
        player2 = playerRepository.save("P2");
        match1.setIdWinner(player1.getId());
        match1.setPlayer1(player1);
        match1.setPlayer2(player2);

        var id = matchRepository.save(match1);

        var actualMatch = executor.executeQuery(
                session -> session.createQuery(
                                "select m from Match m where id = :id", Match.class
                        ).setParameter("id", id)
                        .uniqueResult()
        );
        assertThat(actualMatch)
                .usingRecursiveComparison()
                .isEqualTo(match1);
    }

    @Test
    void givenNotExistPlayersWhenSaveMatchThenThrowRuntimeException() {
        match1.setIdWinner(1);

        assertThatThrownBy(
                () -> matchRepository.save(match1)
        ).isInstanceOf(RuntimeException.class);
    }
}