package fmi.sports.tournament.organizer.backend.dtos;

import fmi.sports.tournament.organizer.backend.entities.tournament.Standing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandingDTO {
    public static StandingDTO fromEntity(Standing standing) {
        return StandingDTO
                .builder()
                .teamId(standing.getTeamId())
                .points(standing.getPoints())
                .build();
    }

    private Long teamId;
    private Integer points;
}
