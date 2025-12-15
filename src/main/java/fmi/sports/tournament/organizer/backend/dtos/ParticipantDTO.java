package fmi.sports.tournament.organizer.backend.dtos;

import fmi.sports.tournament.organizer.backend.entities.team.Participant;
import fmi.sports.tournament.organizer.backend.entities.team.ParticipantCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDTO {
    public static ParticipantDTO fromEntity(Participant participant) {
        return new ParticipantDTO(participant.getUser().getId(),
                participant.getTeam().getId(),
                participant.getCategory());
    }

    private Long userId;
    private Long teamId;
    private ParticipantCategory category;
}
