package fmi.sports.tournament.organizer.backend.entities.notification;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class NotificationId implements Serializable {
    private Long userId;
    private Long messageId;
    private LocalDateTime creationTime;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NotificationId that = (NotificationId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(messageId, that.messageId) && Objects.equals(creationTime, that.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, messageId, creationTime);
    }
}
