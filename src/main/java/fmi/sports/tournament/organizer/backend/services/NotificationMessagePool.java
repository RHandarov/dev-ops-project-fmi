package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.entities.notification.NotificationMessage;
import org.springframework.stereotype.Service;

@Service
public interface NotificationMessagePool {
    NotificationMessage getNotificationMessage(String message);
}
