package org.example.mediavaultbackend.config;


import jakarta.transaction.Transactional;
import org.example.mediavaultbackend.Services.DataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(DataService dataService) {

        return args -> {
            dataService.importMovies();
            System.out.println("Imported Title Basics");
        };
    }

}
