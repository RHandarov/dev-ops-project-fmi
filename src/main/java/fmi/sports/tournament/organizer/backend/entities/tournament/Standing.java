package fmi.sports.tournament.organizer.backend.entities.tournament;

import fmi.sports.tournament.organizer.backend.entities.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "standings")
@IdClass(StandingId.class)
public class Standing {
    @Id
    private Long tournamentId;

    @Id
    private Long teamId;

    @ManyToOne
    @MapsId("tournamentId")
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private Team team;
    private Integer points;
}
