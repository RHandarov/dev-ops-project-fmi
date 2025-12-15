package fmi.sports.tournament.organizer.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fmi.sports.tournament.organizer.backend.entities.team.ParticipantCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeParticipantCategoryDTO {
    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Category is required")
    @JsonProperty(value = "category")
    private ParticipantCategory participantCategory;
}
