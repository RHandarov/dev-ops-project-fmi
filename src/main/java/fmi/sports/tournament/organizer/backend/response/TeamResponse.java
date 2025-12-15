package fmi.sports.tournament.organizer.backend.response;

import fmi.sports.tournament.organizer.backend.dtos.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {

    private Long id;
    private String name;
    private String email;
    private Double budget;
    private Integer size;
    private ResponseResult responseResult;
    private String message;

    public static TeamResponseBuilder fromDTO(TeamDTO dto) {
        return TeamResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .budget(dto.getBudget())
                .size(dto.getSize());
    }
}
