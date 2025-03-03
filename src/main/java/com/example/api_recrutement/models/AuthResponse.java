package com.example.api_recrutement.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private UserInfo infosUser;

    public AuthResponse(String token, UserInfo infosUser) {
        this.token = token;
        this.infosUser = infosUser;
    }

    @Getter
    @Setter
    public static class UserInfo {
        private Long id;
        private Long userId;
        private String prenom;
        private String nom;
        private String email;
        private String role;
        private String telephone;
        private String adresse;
        private String photoProfil;
        private String description;

        public UserInfo(
                Long id,
                Long userId,
                String prenom,
                String nom,
                String email,
                String role,
                String telephone,
                String description,
                String adresse,
                String photoProfil
                ) {
            this.id = id;
            this.userId = userId;
            this.prenom = prenom;
            this.nom = nom;
            this.email = email;
            this.role = role;
            this.telephone = telephone;
            this.description = description;
            this.adresse = adresse;
            this.photoProfil = photoProfil;
        }
    }
}

//        private LocalDateTime createdAt;
//        private String createdBy;
//        private LocalDateTime updatedAt;
//        private String updatedBy;
//        private LocalDateTime deletedAt;
//        private String deletedBy;

//            this.createdAt = createdAt;
//            this.createdBy = createdBy;
//            this.updatedAt = updatedAt;
//            this.updatedBy = updatedBy;
//            this.deletedAt = deletedAt;
//            this.deletedBy = deletedBy;

//package com.example.api_recrutement.models;
//
//    import lombok.Getter;
//    import lombok.Setter;
//
//    public class AuthResponse {
//        private String token;
//        private UserInfo infosUser;
//
//        public AuthResponse(String token, UserInfo infosUser) {
//            this.token = token;
//            this.infosUser = infosUser;
//        }
//
//        public AuthResponse(String token, Long id, String prenom, String nom, String email, String role, String telephone, String description, String adresse, String photoProfil) {
//            this.token = token;
//            this.infosUser = new UserInfo(id, prenom, nom, email, role, telephone, description, adresse, photoProfil);
//        }
//
//        // Getters and Setters
//        public String getToken() {
//            return token;
//        }
//
//        public void setToken(String token) {
//            this.token = token;
//        }
//
//        public UserInfo getInfosUser() {
//            return infosUser;
//        }
//
//        public void setInfosUser(UserInfo infosUser) {
//            this.infosUser = infosUser;
//        }
//
//        // Classe interne pour stocker les informations utilisateur
//        @Setter
//        @Getter
//        public static class UserInfo {
//            private Long id;
//            private String prenom;
//            private String nom;
//            private String email;
//            private String role;
//            private String telephone;
//            private String adresse;
//            private String photoProfil;
//            private String description;
//            private Long userId;
//
//            public UserInfo(Long id, String prenom, String nom, String email, String role, String telephone, String description, String adresse, String photoProfil, Long userId) {
//                this.id = id;
//                this.prenom = prenom;
//                this.nom = nom;
//                this.email = email;
//                this.role = role;
//                this.telephone = telephone;
//                this.description = description;
//                this.adresse = adresse;
//                this.photoProfil = photoProfil;
//                this.userId = userId;
//            }
//        }
//    }