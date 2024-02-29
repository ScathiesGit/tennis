package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.dto.MatchesPage;
import scathies.tennis.repository.MatchRepository;

@Service
@RequiredArgsConstructor
public class FinishedMatchServiceImpl implements FinishedMatchService {

    private final MatchRepository matchRepository;

    public MatchesPage findAll(Integer page, Integer pageSize) {
        return MatchesPage.builder()
                .page(page)
                .pageSize(pageSize)
                .totalSize(matchRepository.numberMatches())
                .matches(matchRepository.findAll(page, pageSize))
                .build();
    }

    @Override
    public MatchesPage findAllByName(Integer page, Integer pageSize, String playerName) {
        return MatchesPage.builder()
                .page(page)
                .pageSize(pageSize)
                .totalSize(matchRepository.numberMatchesByName(playerName))
                .matches(matchRepository.findAllByPlayerName(page, pageSize, playerName))
                .build();
    }
}
