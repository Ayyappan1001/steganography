package com.steganography.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.steganography")
public class SteganographyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteganographyApplication.class, args);
		System.out.println("Steganography Application Started ");
	}

}
