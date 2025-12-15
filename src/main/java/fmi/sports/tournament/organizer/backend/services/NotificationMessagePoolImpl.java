package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.entities.notification.NotificationMessage;
import fmi.sports.tournament.organizer.backend.repositories.notification.NotificationMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationMessagePoolImpl implements NotificationMessagePool {
    private final NotificationMessagesRepository notificationMessagesRepository;

    @Autowired
    public NotificationMessagePoolImpl(NotificationMessagesRepository notificationMessagesRepository) {
        this.notificationMessagesRepository = notificationMessagesRepository;
    }

    @Override
    public NotificationMessage getNotificationMessage(String message) {
        Optional<NotificationMessage> notificationMessage =
                notificationMessagesRepository.findByMessageContent(message);
        return notificationMessage.orElseGet(() -> notificationMessagesRepository.save(new NotificationMessage(message)));
    }
}
