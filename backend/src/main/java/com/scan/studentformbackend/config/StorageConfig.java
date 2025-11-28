package com.scan.studentformbackend.config;

import com.scan.studentformbackend.service.FileStorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    CommandLineRunner init(FileStorageService fileStorageService) {
        return args -> {
            fileStorageService.initializeStorage();
        };
    }
}
