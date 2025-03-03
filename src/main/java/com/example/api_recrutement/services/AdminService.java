package com.example.api_recrutement.services;

import com.example.api_recrutement.models.Admin;
import com.example.api_recrutement.models.Role;
import com.example.api_recrutement.models.User;
import com.example.api_recrutement.repository.AdminRepository;
import com.example.api_recrutement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service pour la gestion des administrateurs
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;  // Ajout du UserRepository
    private final PasswordEncoder passwordEncoder;

    public AdminService(
            AdminRepository adminRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Transactional  // Important pour garantir l'atomicité des opérations
    public Admin createAdmin(Admin admin) {
        // Créer d'abord l'utilisateur
        User user = new User();
        user.setEmail(admin.getEmail());
        user.setPassword(passwordEncoder.encode(admin.getPassword()));  // Hachez le mot de passe
        user.setRole(Role.ADMIN);
        user.setNom(admin.getNom());
        user.setPrenom(admin.getPrenom());
        user.setAdresse(admin.getAdresse());
        user.setDescription(admin.getDescription());
        user.setPhotoProfil(admin.getPhotoProfil());
        user.setTelephone(admin.getTelephone());
        User savedUser = userRepository.save(user);

        // Lier l'utilisateur à l'admin
        admin.setUser(savedUser);
        return adminRepository.save(admin);
    }

//    public Admin createAdmin(Admin admin) {
//        return adminRepository.save(admin);
//    }

    @Transactional
    public Admin updateAdmin(Long id, Admin adminDetails) {
        return adminRepository.findById(id).map(admin -> {
            // Mise à jour des informations de l'admin
            admin.setNom(adminDetails.getNom());
            admin.setPrenom(adminDetails.getPrenom());
            admin.setTelephone(adminDetails.getTelephone());
            admin.setAdresse(adminDetails.getAdresse());
            admin.setDescription(adminDetails.getDescription());
            admin.setPhotoProfil(adminDetails.getPhotoProfil());

            // Mise à jour des informations de l'utilisateur associé
            User user = admin.getUser();
            if (user != null) {
                user.setNom(adminDetails.getNom());
                user.setPrenom(adminDetails.getPrenom());
                user.setTelephone(adminDetails.getTelephone());
                user.setAdresse(adminDetails.getAdresse());
                user.setDescription(adminDetails.getDescription());
                user.setPhotoProfil(adminDetails.getPhotoProfil());
                user.setTelephone(adminDetails.getTelephone());

                userRepository.save(user);
            }

            return adminRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin non existant"));
    }

//    public Admin updateAdmin(Long id, Admin adminDetails) {
//        return adminRepository.findById(id).map(admin -> {
//            admin.setNom(adminDetails.getNom());
//            admin.setPrenom(adminDetails.getPrenom());
//            admin.setTelephone(adminDetails.getTelephone());
//            admin.setAdresse(adminDetails.getAdresse());
//            admin.setDescription(adminDetails.getDescription());
//            admin.setPhotoProfil(adminDetails.getPhotoProfil());
//            return adminRepository.save(admin);
//        }).orElseThrow(() -> new RuntimeException("Admin non existant"));
//    }

//    public Admin updateAdmin(Long id, Admin adminDetails) {
//        return adminRepository.findById(id).map(admin -> {
//            return adminRepository.save(adminDetails);
//        }).orElseThrow(() -> new RuntimeException("Admin non existant"));
//    }

    @Transactional
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        // Supprimer d'abord l'admin
        adminRepository.deleteById(id);

        // Puis supprimer l'utilisateur associé
        if (admin.getUser() != null) {
            userRepository.deleteById(admin.getUser().getId());
        }
    }

//    public void deleteAdmin(Long id) {
//        adminRepository.deleteById(id);
//    }
}
