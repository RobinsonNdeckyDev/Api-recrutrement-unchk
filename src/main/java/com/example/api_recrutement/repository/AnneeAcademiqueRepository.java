package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.AnneeAcademique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnneeAcademiqueRepository extends JpaRepository<AnneeAcademique, Long> {
    Optional<AnneeAcademique> findById(Long aLong);
}
