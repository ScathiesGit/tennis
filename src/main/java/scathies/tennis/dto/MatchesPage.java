package scathies.tennis.dto;

import lombok.*;
import scathies.tennis.model.Match;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MatchesPage {

    private Integer page;
    private Integer pageSize;
    private Long totalSize;
    private List<Match> matches;
}
