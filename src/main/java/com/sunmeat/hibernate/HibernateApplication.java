package com.sunmeat.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// запуск додатку - http://localhost:8080/
// має бути піднятий MySQL
@SpringBootApplication
public class HibernateApplication {
	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
	}
}
