package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    Optional<Annonce> findById(Long id);
}
