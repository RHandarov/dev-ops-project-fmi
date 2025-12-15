package fmi.sports.tournament.organizer.backend.entities.notification;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "messages")
public class NotificationMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String messageContent;

    public NotificationMessage(String messageContent) {
        this.messageContent = messageContent;
    }
}
