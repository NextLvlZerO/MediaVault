package org.example.mediavaultbackend.config;


import jakarta.transaction.Transactional;
import org.example.mediavaultbackend.services.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {

    private static final Logger log = LoggerFactory.getLogger(DataConfig.class);

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(DataService dataService) {

        return args -> {
            dataService.importData();
            log.info("Imported Data");
        };
    }

}
