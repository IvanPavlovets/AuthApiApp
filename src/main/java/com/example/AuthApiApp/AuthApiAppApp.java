package com.example.AuthApiApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApiAppApp {

	public static void main(String[] args) {
		SpringApplication.run(AuthApiAppApp.class, args);
		System.out.println("Go to -> " + "http://localhost:8080");
	}

}
