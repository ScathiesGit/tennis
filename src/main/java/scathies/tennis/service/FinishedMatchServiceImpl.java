package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.dto.MatchesPage;
import scathies.tennis.model.Match;
import scathies.tennis.repository.MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinishedMatchServiceImpl implements FinishedMatchService {

    private final MatchRepository matchRepository;

    @Override
    public MatchesPage find(Integer page, Integer pageSize, String playerName) {
        var matches = playerName == null || playerName.isEmpty()
                ? matchRepository.findAll(page, pageSize)
                : matchRepository.findAllByPlayerName(page, pageSize, playerName);

        return MatchesPage.builder()
                .page(page)
                .pageSize(pageSize)
                .totalSize(matchRepository.numberMatches(
                        playerName == null || playerName.isEmpty() ? "" : playerName
                ))
                .matches(matches)
                .build();
    }
}
