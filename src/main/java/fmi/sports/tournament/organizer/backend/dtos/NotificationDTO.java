package fmi.sports.tournament.organizer.backend.dtos;

import fmi.sports.tournament.organizer.backend.entities.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    public static NotificationDTO fromEntity(Notification notification) {
        return NotificationDTO
                .builder()
                .message(notification.getMessage().getMessageContent())
                .creationTime(notification.getCreationTime())
                .build();
    }

    private String message;
    private LocalDateTime creationTime;
}
