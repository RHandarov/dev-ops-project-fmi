package fmi.sports.tournament.organizer.backend.dtos;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchResultsDTO {
    @Positive(message = "Team 1 points must be a positive number")
    private Integer team1Points;

    @Positive(message = "Team 2 points must be a positive number")
    private Integer team2Points;
}
