package scathies.tennis.model;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RealtimeMatches {

    private final ConcurrentHashMap<UUID, Match> matches = new ConcurrentHashMap<>();

    public Match get(UUID id) {
        return matches.get(id);
    }

    public void add(Match match) {
        matches.put(match.getId(), match);
    }

    public boolean delete(UUID id) {
        return matches.remove(id) != null;
    }
}
