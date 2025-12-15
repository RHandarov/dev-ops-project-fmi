package fmi.sports.tournament.organizer.backend.response;

import fmi.sports.tournament.organizer.backend.dtos.StandingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandingResponse {
    public static StandingResponseBuilder fromDTO(StandingDTO standingDTO) {
        return StandingResponse
                .builder()
                .teamId(standingDTO.getTeamId())
                .points(standingDTO.getPoints());
    }

    private Long teamId;
    private Integer points;
    private ResponseResult responseResult;
    private String message;
}
