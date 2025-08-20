package org.example.mediavaultbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.mediavaultbackend.models")
public class MediaVaultBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaVaultBackendApplication.class, args);
    }

}
