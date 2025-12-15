package fmi.sports.tournament.organizer.backend.response;

import fmi.sports.tournament.organizer.backend.dtos.ParticipantDTO;
import fmi.sports.tournament.organizer.backend.entities.team.ParticipantCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantResponse {
    public static ParticipantResponseBuilder fromDTO(ParticipantDTO participantDTO) {
        return ParticipantResponse
                .builder()
                .userId(participantDTO.getUserId())
                .teamId(participantDTO.getTeamId())
                .category(participantDTO.getCategory());
    }

    private Long userId;
    private Long teamId;
    private ParticipantCategory category;
    private ResponseResult responseResult;
    private String message;
}
