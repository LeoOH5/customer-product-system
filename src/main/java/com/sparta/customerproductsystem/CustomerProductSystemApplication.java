package com.sparta.customerproductsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CustomerProductSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerProductSystemApplication.class, args);
    }

}
