package scathies.tennis.repository;

import scathies.tennis.model.Player;

import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> findByName(String name);

    Player save(Player player);
}
