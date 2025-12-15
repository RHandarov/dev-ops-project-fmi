package fmi.sports.tournament.organizer.backend.repositories.notification;

import fmi.sports.tournament.organizer.backend.entities.notification.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationMessagesRepository extends JpaRepository<NotificationMessage, Long> {
    Optional<NotificationMessage> findByMessageContent(String messageContent);
}
