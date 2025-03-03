package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    Optional<Candidature> findById(Long id);

    boolean existsByUserIdAndAnnonceId(Long userId, Long annonceId);
}
