package fmi.sports.tournament.organizer.backend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewNotificationDTO {
    @NotNull(message = "Receiver id is required")
    private Long receiverId;

    @NotNull(message = "Notification message is required")
    private String message;
}
