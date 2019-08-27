package com.parkinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.parkinfo")
@EnableJpaAuditing
@EnableAsync
@EnableTransactionManagement
public class ParkinfoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkinfoApiApplication.class, args);
    }

}
