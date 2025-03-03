package com.example.api_recrutement.services;

import com.example.api_recrutement.models.*;
import com.example.api_recrutement.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CandidatureRepository candidatureRepository;
    private final CandidatRepository candidatRepository;
    private final AdminRepository adminRepository;

    public NotificationService(NotificationRepository notificationRepository, AdminRepository adminRepository, CandidatureRepository candidatureRepository, CandidatRepository candidatRepository) {
        this.adminRepository = adminRepository;
        this.candidatureRepository = candidatureRepository;
        this.candidatRepository = candidatRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification createNotification(Long adminId, Long candidatId, Long candidatureId, String titre, String description, String contenu) {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvé"));

        Notification notification = new Notification();
        notification.setTitre(titre);
        notification.setDescription(description);
        notification.setContenu(contenu);

        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long id, Notification notification) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (!notificationOptional.isPresent()) {
            throw new RuntimeException("Notification non trouvée");
        }

        Notification existingNotification = notificationOptional.get();
        existingNotification.setCandidat(notification.getCandidat());
        existingNotification.setAdmin(notification.getAdmin());
        existingNotification.setCandidature(notification.getCandidature());
        existingNotification.setTitre(notification.getTitre());
        existingNotification.setDescription(notification.getDescription());
        existingNotification.setContenu(notification.getContenu());

        return notificationRepository.save(existingNotification);
    }

    public void deleteNotification(Long id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (!notificationOptional.isPresent()) {
            throw new RuntimeException("Notification non trouvée");
        }

        notificationRepository.delete(notificationOptional.get());
    }

}
