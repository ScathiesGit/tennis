package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.dto.MatchesPage;
import scathies.tennis.model.Match;
import scathies.tennis.repository.MatchRepository;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class FinishedMatchServiceImpl implements FinishedMatchService {

    private final MatchRepository matchRepository;

    @Override
    public MatchesPage find(Integer page, Integer pageSize, String playerName) {
        MatchesPage matchesPage;
        if (playerName == null || playerName.isEmpty()) {
            matchesPage = MatchesPage.builder()
                    .page(page)
                    .pageSize(pageSize)
                    .totalSize(matchRepository.numberMatches())
                    .matches(matchRepository.findAll(page, pageSize))
                    .build();
        } else {
            matchesPage = MatchesPage.builder()
                    .page(page)
                    .pageSize(pageSize)
                    .totalSize(matchRepository.numberMatchesByName(playerName))
                    .matches(matchRepository.findAllByPlayerName(page, pageSize, playerName))
                    .build();
        }
        return matchesPage;
    }
}
