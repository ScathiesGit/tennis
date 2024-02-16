package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.model.Match;
import scathies.tennis.repository.MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinishedMatchServiceImpl implements FinishedMatchService {

    private final MatchRepository matchRepository;

    public List<Match> find(Integer page, Integer pageSize, String playerName) {
        return playerName == null ? matchRepository.findAll(page, pageSize)
                : matchRepository.findAllByPlayerName(page, pageSize, playerName);
    }
}
