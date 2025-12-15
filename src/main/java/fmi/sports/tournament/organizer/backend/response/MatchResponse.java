package fmi.sports.tournament.organizer.backend.response;

import fmi.sports.tournament.organizer.backend.dtos.MatchDTO;
import fmi.sports.tournament.organizer.backend.entities.tournament.match.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {
    public static MatchResponseBuilder fromDTO(MatchDTO matchDTO) {
        return MatchResponse.builder()
                .id(matchDTO.getId())
                .tournamentId(matchDTO.getTournamentId())
                .team1Id(matchDTO.getTeam1Id())
                .team1Points(matchDTO.getTeam1Points())
                .team2Id(matchDTO.getTeam2Id())
                .team2Points(matchDTO.getTeam2Points())
                .venue(matchDTO.getVenue())
                .status(matchDTO.getStatus());
    }

    private Long id;
    private Long tournamentId;
    private Long team1Id;
    private Integer team1Points;
    private Long team2Id;
    private Integer team2Points;
    private String venue;
    private MatchStatus status;
    private ResponseResult responseResult;
    private String message;
}
