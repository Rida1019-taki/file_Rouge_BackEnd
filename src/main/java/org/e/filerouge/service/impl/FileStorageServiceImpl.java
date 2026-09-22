package org.e.filerouge.service.impl;

import org.e.filerouge.exception.BadRequestException;
import org.e.filerouge.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path root;

    public FileStorageServiceImpl(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @Override
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Fichier manquant");
        }
        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        String extension = "";
        int dot = original.lastIndexOf('.');
        if (dot >= 0) {
            extension = original.substring(dot).toLowerCase();
        }
        String filename = UUID.randomUUID() + extension;
        try {
            Files.createDirectories(root);
            file.transferTo(root.resolve(filename));
        } catch (IOException e) {
            throw new BadRequestException("Echec de l'enregistrement du fichier");
        }
        return "/uploads/" + filename;
    }
}
