package org.example.mediavaultbackend.config;


import jakarta.transaction.Transactional;
import org.example.mediavaultbackend.services.DataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(DataService dataService) {

        return args -> {
            dataService.importMedia();
            System.out.println("Imported Media");
        };
    }

}
