package fmi.sports.tournament.organizer.backend.dtos;

import fmi.sports.tournament.organizer.backend.entities.team.ParticipantCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParticipantRegisterDTO {
    @NotNull(message = "Category is required")
    private ParticipantCategory category;

    @NotNull(message = "Secret code is required")
    private String secretCode;
}
