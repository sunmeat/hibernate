package com.sunmeat.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// запуск приложения - http://localhost:8080/
// должен быть поднят MySQL
@SpringBootApplication
public class HibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
	}
}