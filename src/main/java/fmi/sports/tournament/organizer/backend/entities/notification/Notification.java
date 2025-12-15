package fmi.sports.tournament.organizer.backend.entities.notification;

import fmi.sports.tournament.organizer.backend.entities.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notifications")
@IdClass(NotificationId.class)
public class Notification {
    @Id
    private Long userId;

    @Id
    private Long messageId;

    @MapsId("userId")
    @ManyToOne
    private User user;

    @MapsId("messageId")
    @ManyToOne
    private NotificationMessage message;

    @Id
    private LocalDateTime creationTime;
    private boolean isRead;

    public Notification(User user, NotificationMessage message) {
        this.user = user;
        this.message = message;
        this.creationTime = LocalDateTime.now();
        this.isRead = false;
    }
}
