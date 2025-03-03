package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findNotificationById(Long id);
}
