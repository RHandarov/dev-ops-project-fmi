package fmi.sports.tournament.organizer.backend.entities.tournament.match;

import fmi.sports.tournament.organizer.backend.entities.team.Team;
import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_1")
    private Team team1;
    private Integer team1Points;

    @ManyToOne
    @JoinColumn(name = "team_2")
    private Team team2;
    private Integer team2Points;

    private String venue;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    public Match(Tournament tournament,
                 Team team1,
                 Team team2,
                 String venue) {
        this.tournament = tournament;
        this.team1 = team1;
        this.team1Points = 0;
        this.team2 = team2;
        this.team2Points = 0;
        this.venue = venue;
        this.status = MatchStatus.ONGOING;
    }
}
