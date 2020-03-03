package com.example.pekarsky.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableJpaRepositories("com.example.pekarsky.reservations.jpa")
@EnableTransactionManagement
public class ReservationsApp {
    public static void main(String[] args) {
        SpringApplication.run(ReservationsApp.class, args);
    }
}
