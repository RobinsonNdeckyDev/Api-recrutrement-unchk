package com.example.api_recrutement.services;

import com.example.api_recrutement.models.Admin;
import com.example.api_recrutement.models.Candidat;
import com.example.api_recrutement.models.Role;
import com.example.api_recrutement.models.User;
import com.example.api_recrutement.repository.AdminRepository;
import com.example.api_recrutement.repository.CandidatRepository;
import com.example.api_recrutement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final CandidatRepository candidatRepository;
    private final PlatformTransactionManager transactionManager;

    public UserService(UserRepository userRepository, AdminRepository adminRepository, CandidatRepository candidatRepository, PlatformTransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.candidatRepository = candidatRepository;
        this.transactionManager = transactionManager;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User registerUser(String email, String password, String role, String nom, String prenom, String telephone, String adresse, String description, String photoProfil) {
        // Vérifiez si un utilisateur avec le même email existe déjà
        if (userRepository.existsByEmail(email)) {
            logger.error("L'utilisateur avec l'email {} existe déjà.", email);
            throw new IllegalArgumentException("L'utilisateur avec cet email existe déjà.");
        }

        logger.info("Enregistrement d'un nouvel utilisateur avec l'email : {}", email);

        // Créer l'utilisateur de base
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.valueOf(role));
        user.setPrenom(prenom);
        user.setNom(nom);
        user.setTelephone(telephone);
        user.setAdresse(adresse);
        user.setPhotoProfil(photoProfil);
        user.setDescription(description);

        // Enregistrer l'utilisateur dans la table `users`
        User savedUser = userRepository.save(user);

        // Ajouter dans la table spécifique selon le rôle
        if (role.equals("ADMIN")) {
            Admin admin = new Admin();
            admin.setNom(nom);
            admin.setPrenom(prenom);
            admin.setTelephone(telephone);
            admin.setAdresse(adresse);
            admin.setDescription(description);
            admin.setPhotoProfil(photoProfil);
            admin.setUser(savedUser);
            adminRepository.save(admin);
        } else if (role.equals("CANDIDAT")) {
            Candidat candidat = new Candidat();
            candidat.setNom(nom);
            candidat.setPrenom(prenom);
            candidat.setTelephone(telephone);
            candidat.setAdresse(adresse);
            candidat.setDescription(description);
            candidat.setPhotoProfil(photoProfil);
            candidat.setUser(savedUser);
            candidatRepository.save(candidat);
        }

        return savedUser;
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setPrenom(userDetails.getPrenom());
            user.setNom(userDetails.getNom());
            user.setTelephone(userDetails.getTelephone());
            user.setAdresse(userDetails.getAdresse());
            user.setPhotoProfil(userDetails.getPhotoProfil());
            user.setDescription(userDetails.getDescription());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("L'utilisateur n'existe pas"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
