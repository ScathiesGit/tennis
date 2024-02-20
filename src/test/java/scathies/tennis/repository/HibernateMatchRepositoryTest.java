package scathies.tennis.repository;

import configuration.HibernateTestConfiguration;
import configuration.DatabaseSchemaCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import scathies.tennis.model.Match;
import scathies.tennis.model.Player;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class HibernateMatchRepositoryTest {

    private final HibernateExecutor executor = new HibernateExecutor(new HibernateTestConfiguration().getSessionFactory());

    private final HibernateMatchRepository matchRepository = new HibernateMatchRepository(executor);

    private final HibernatePlayerRepository playerRepository = new HibernatePlayerRepository(executor);

    private Player player1;

    private Player player2;

    private Player player3;

    private Match match1 = new Match();

    private Match match2 = new Match();

    private Match match3 = new Match();

//    @BeforeAll
//    static void createDatabase() {
//        DatabaseSchemaCreator.executeSqlScripts();
//    }

    @BeforeEach
    void setUp() {
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
        saveMatch(match1, player1, player2, player1.getId());
        saveMatch(match2, player2, player3, player3.getId());

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
        saveMatch(match1, player1, player2, player1.getId());
        saveMatch(match2, player2, player3, player3.getId());
        saveMatch(match3, player1, player3, player3.getId());

        var matches = matchRepository.findAllByPlayerName(1, 5, player1.getName());

        assertThat(matches)
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(match1, match3));
    }

    @Test
    void givenExistPlayersWhenSaveMatchThenFindById() {
        player1 = playerRepository.save("P1");
        player2 = playerRepository.save("P2");
        match1.setPlayer1(player1);
        match1.setPlayer2(player2);
        match1.setIdWinner(player1.getId());

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

    @Test
    void givenNotEmptyNameWhenNumberMatchesThenReturnNumberMatchesForPlayerName() {
        player1 = playerRepository.save("P1");
        player2 = playerRepository.save("P2");
        player3 = playerRepository.save("P3");
        saveMatch(match1, player1, player2, player1.getId());
        saveMatch(match2, player1, player3, player3.getId());

        var numberMatches = matchRepository.numberMatches("P1");

        assertThat(numberMatches).isEqualTo(2);
    }

    @Test
    void givenEmptyNameWhenNumberMatchesThenReturnNumberAllMatches() {
        player1 = playerRepository.save("P1");
        player2 = playerRepository.save("P2");
        player3 = playerRepository.save("P3");
        saveMatch(match1, player1, player2, player1.getId());
        saveMatch(match2, player2, player3, player3.getId());
        saveMatch(match3, player1, player3, player3.getId());

        var numberMatches = matchRepository.numberMatches("");

        assertThat(numberMatches).isEqualTo(3);
    }

    private void saveMatch(Match match, Player player1, Player player2, int idWinner) {
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setIdWinner(idWinner);
        matchRepository.save(match);
    }
}