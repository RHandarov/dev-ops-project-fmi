package fmi.sports.tournament.organizer.backend.repositories.notification;

import fmi.sports.tournament.organizer.backend.entities.notification.Notification;
import fmi.sports.tournament.organizer.backend.entities.notification.NotificationId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, NotificationId> {
  List<Notification> findAllByUserId(Long userId);

  void deleteAllByUserId(Long userId);

  @Modifying
  @Query("UPDATE Notification n SET n.isRead = true WHERE n.userId = :userId")
  void markAllAsRead(@Param("userId") Long userId);
}
