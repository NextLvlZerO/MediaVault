package org.example.mediavaultbackend.config;


import jakarta.transaction.Transactional;
import org.example.mediavaultbackend.Services.ImdbDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(ImdbDataService imdbDataService) {

        return args -> {
            imdbDataService.importTitleBasics();
        };
    }

}
