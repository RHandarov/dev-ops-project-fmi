package fmi.sports.tournament.organizer.backend.responses;

import fmi.sports.tournament.organizer.backend.dtos.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    public static NotificationResponseBuilder fromDTO(NotificationDTO notificationDTO) {
        return NotificationResponse
                .builder()
                .message(notificationDTO.getMessage())
                .creationTime(notificationDTO.getCreationTime());
    }

    private String message;
    private LocalDateTime creationTime;
    private ResponseResult responseResult;
}
