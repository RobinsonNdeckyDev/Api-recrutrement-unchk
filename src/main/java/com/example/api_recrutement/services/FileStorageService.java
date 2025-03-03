package com.example.api_recrutement.services;

import com.example.api_recrutement.models.FileDB;
import com.example.api_recrutement.repository.FileDBReposetory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileDBReposetory fileDBRepository;

    public FileStorageService(FileDBReposetory fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    public FileDB storeFile(MultipartFile file) throws IOException {
        // Vérifier si le fichier est vide
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }

        // Créer un nom de fichier unique
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Créer le répertoire de stockage s'il n'existe pas
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Chemin complet du fichier
        Path filePath = uploadPath.resolve(fileName);

        // Copier le fichier
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Créer l'URL publique
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/")
                .path(fileName)
                .toUriString();

        // Sauvegarder les informations dans la base de données
        FileDB fileDB = new FileDB(
                fileName,
                file.getContentType(),
                filePath.toString(),
                fileUrl
        );

        return fileDBRepository.save(fileDB);
    }

    public FileDB getFile(Long id) {
        return fileDBRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fichier non trouvé"));
    }

    public void deleteFile(Long id) throws IOException {
        FileDB fileDB = getFile(id);
        // Supprimer le fichier physique
        Files.deleteIfExists(Paths.get(fileDB.getChemin()));
        // Supprimer l'entrée en base de données
        fileDBRepository.deleteById(id);
    }
}
