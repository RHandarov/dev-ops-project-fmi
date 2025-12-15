package fmi.sports.tournament.organizer.backend.entities.tournament;

import fmi.sports.tournament.organizer.backend.dtos.TournamentDTO;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tournaments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tournament {
    public static Tournament fromDTO(TournamentDTO tournamentDTO) {
        return Tournament.builder()
                .id(tournamentDTO.getId())
                .name(tournamentDTO.getName())
                .location(tournamentDTO.getLocation())
                .sportType(tournamentDTO.getSportType())
                .startDate(tournamentDTO.getStartDate())
                .endDate(tournamentDTO.getEndDate())
                .registrationFee(tournamentDTO.getRegistrationFee())
                .maxTeams(tournamentDTO.getMaxTeams())
                .build();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String sportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double registrationFee;
    private Integer maxTeams;

    @ManyToMany
    @JoinTable(name = "tournament_team",
               joinColumns = @JoinColumn(name = "tournament_id"),
               inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams;

    public Tournament(String name,
                      String location,
                      String sportType,
                      LocalDate startDate,
                      LocalDate endDate,
                      Double registrationFee,
                      Integer maxTeams) {
        this.name = name;
        this.location = location;
        this.sportType = sportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationFee = registrationFee;
        this.maxTeams = maxTeams;
    }
}
