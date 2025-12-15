package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.NewNotificationDTO;
import fmi.sports.tournament.organizer.backend.dtos.NotificationDTO;
import fmi.sports.tournament.organizer.backend.entities.notification.Notification;
import fmi.sports.tournament.organizer.backend.entities.notification.NotificationMessage;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.exceptions.user.NoUserWithSuchIdException;
import fmi.sports.tournament.organizer.backend.repositories.notification.NotificationsRepository;
import fmi.sports.tournament.organizer.backend.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationsRepository notificationsRepository;
    private final NotificationMessagePool notificationMessagePool;
    private final UsersRepository usersRepository;

    @Autowired
    public NotificationServiceImpl(NotificationsRepository notificationsRepository,
                                   NotificationMessagePool notificationMessagePool,
                                   UsersRepository usersRepository) {
        this.notificationsRepository = notificationsRepository;
        this.notificationMessagePool = notificationMessagePool;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public List<NotificationDTO> getAllUnreadNotificationsByUserId(Long userId) {
        List<NotificationDTO> unreadNotifications = notificationsRepository
                .findAllByUserId(userId)
                .stream()
                .filter(notification -> !notification.isRead())
                .sorted((lhs, rhs) -> lhs.getCreationTime().compareTo(rhs.getCreationTime()) * -1)
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
        notificationsRepository.markAllAsRead(userId);
        return unreadNotifications;
    }

    @Override
    @Transactional
    public List<NotificationDTO> getAllNotificationsByUserId(Long userId) {
        notificationsRepository.markAllAsRead(userId);
        return notificationsRepository
                .findAllByUserId(userId)
                .stream()
                .sorted((lhs, rhs) -> lhs.getCreationTime().compareTo(rhs.getCreationTime()) * -1)
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllNotificationsByUserId(Long userId) {
        notificationsRepository.deleteAllByUserId(userId);
    }

    @Override
    public void sendNotification(NewNotificationDTO newNotificationDTO) {
        NotificationMessage notificationMessage =
                notificationMessagePool.getNotificationMessage(newNotificationDTO.getMessage());
        User receiver = findUserById(newNotificationDTO.getReceiverId());
        notificationsRepository.save(new Notification(receiver, notificationMessage));
    }

    private User findUserById(Long userId) {
        return usersRepository
                .findById(userId)
                .orElseThrow(() -> new NoUserWithSuchIdException(userId));
    }
}
