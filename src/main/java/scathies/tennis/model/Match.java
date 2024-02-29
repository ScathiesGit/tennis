package scathies.tennis.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_player_1")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "id_player_2")
    private Player player2;

    @Column(name = "id_winner")
    private Integer idWinner;

    @Transient
    private int gameScorePlayer1;

    @Transient
    private int gameScorePlayer2;

    @Transient
    private int setScorePlayer1;

    @Transient
    private int setScorePlayer2;

    @Transient
    private boolean isTiebreak;
}
