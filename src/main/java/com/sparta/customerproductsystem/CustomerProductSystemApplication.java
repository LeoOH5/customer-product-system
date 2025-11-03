package com.sparta.customerproductsystem;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CustomerProductSystemApplication {

    public static void main(String[] args) {

        // .env 파일 로드
        Dotenv dotenv = Dotenv.load();

        // Spring 환경에 적용
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(CustomerProductSystemApplication.class, args);
    }

}
