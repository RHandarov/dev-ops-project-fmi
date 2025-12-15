package fmi.sports.tournament.organizer.backend.response;

import fmi.sports.tournament.organizer.backend.dtos.TournamentDTO;
import fmi.sports.tournament.organizer.backend.entities.tournament.match.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentResponse {

    private Long id;
    private String name;
    private String location;
    private String sportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double registrationFee;
    private Integer maxTeams;
    private ResponseResult responseResult;
    private String message;

    public static TournamentResponseBuilder fromDTO(TournamentDTO dto) {
        return TournamentResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .location(dto.getLocation())
                .sportType(dto.getSportType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .registrationFee(dto.getRegistrationFee())
                .maxTeams(dto.getMaxTeams());
    }
}
