package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.NewNotificationDTO;
import fmi.sports.tournament.organizer.backend.dtos.NotificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    List<NotificationDTO> getAllUnreadNotificationsByUserId(Long userId);
    List<NotificationDTO> getAllNotificationsByUserId(Long userId);
    void deleteAllNotificationsByUserId(Long userId);
    void sendNotification(NewNotificationDTO newNotificationDTO);
}
