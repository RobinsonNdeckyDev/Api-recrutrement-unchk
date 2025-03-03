package com.example.api_recrutement.controllers;

import com.example.api_recrutement.dtos.NotificationDTO;
import com.example.api_recrutement.mappers.NotificationMapper;
import com.example.api_recrutement.models.*;
import com.example.api_recrutement.repository.NotificationRepository;
import com.example.api_recrutement.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    public NotificationController(NotificationRepository notificationRepository, NotificationService notificationService) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestParam("titre") String titre,
            @RequestParam("description") String description,
            @RequestParam("contenu") String contenu,
            @RequestParam("adminId") Long adminId,
            @RequestParam("candidatId") Long candidatId,
            @RequestParam("candidatureId") Long candidatureId
    ) {

        try {

            Notification notification = notificationService.createNotification(adminId, candidatId, candidatureId, titre, description, contenu);

            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
        Notification notification = NotificationMapper.INSTANCE.toNotification(notificationDTO);
        Notification updateNotification = notificationService.updateNotification(id, notification);
        return ResponseEntity.ok(updateNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

}
