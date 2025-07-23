package za.co.simplitate.hotelbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.simplitate.hotelbooking.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Define methods for notification-related operations here
    // For example:
    // List<Notification> findNotificationsByUserId(Long userId);
    // Optional<Notification> findNotificationById(Long notificationId);
    // boolean existsByUserIdAndType(Long userId, NotificationType type);

    // Add custom queries as needed
}
