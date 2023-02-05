package com.gymstarter.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.gymstarter.library", "com.gymstarter.client"})
@EnableJpaRepositories(value = "com.gymstarter.library.repository")
@EntityScan(value = "com.gymstarter.library.model")
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
