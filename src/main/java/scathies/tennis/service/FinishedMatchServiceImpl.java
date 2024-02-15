package scathies.tennis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scathies.tennis.model.Match;
import scathies.tennis.repository.MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinishedMatchServiceImpl {

    private final MatchRepository matchRepository;

    public List<Match> find(Integer page, Integer pageSize, String playerName) {
        //если playerName ! (empty | null)
        //сделать запрос findAll и найти все матчи на page странице

        //если playerName empty | null
        //сделать запрос по игроку и найти матчи на page странице
        return playerName == null ? matchRepository.findAll(page, pageSize) :
                matchRepository.findAllByPlayerName(page, pageSize, playerName);
    }
}
