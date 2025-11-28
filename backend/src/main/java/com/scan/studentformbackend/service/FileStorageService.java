package com.scan.studentformbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    public String createCustomerFolder(String customerName) throws IOException {
        // Sanitize folder name
        String folderName = customerName.replaceAll("[^a-zA-Z0-9-_]", "_");
        Path folderPath = Paths.get(uploadDir, folderName);

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
            logger.info("Created folder: {}", folderPath);
        }

        return folderPath.toString();
    }

    public String saveFile(MultipartFile file, String folderPath, String prefix) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }

        String originalFilename = file.getOriginalFilename();
        String filename = prefix + "_" + originalFilename;
        Path targetPath = Paths.get(folderPath, filename);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        logger.info("Saved file: {}", targetPath);

        return targetPath.toString();
    }

    public void initializeStorage() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("Created upload directory: {}", uploadPath);
            }
        } catch (IOException e) {
            logger.error("Could not create upload directory", e);
            throw new RuntimeException("Could not create upload directory", e);
        }
    }
}
