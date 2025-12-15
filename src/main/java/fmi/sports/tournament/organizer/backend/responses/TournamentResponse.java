package fmi.sports.tournament.organizer.backend.responses;

import fmi.sports.tournament.organizer.backend.dtos.TournamentDTO;
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
}
