package org.example.mediavaultbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.h2.tools.Server;

@SpringBootApplication
@EntityScan(basePackages = "org.example.mediavaultbackend.models")
public class MediaVaultBackendApplication {

    private static final Logger logger = LoggerFactory.getLogger(MediaVaultBackendApplication.class);

    public static void main(String[] args) {
        try {
            // H2 TCP-Server starten
            Server.createTcpServer("-tcpAllowOthers", "-tcpPort", "9092").start();
        } catch (java.sql.SQLException e) {
            logger.error("H2 TCP-Server konnte nicht gestartet werden! " + e.getMessage());
        }

        SpringApplication.run(MediaVaultBackendApplication.class, args);

    }

}
