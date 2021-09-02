package com.h5tchibook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.*")
@SpringBootApplication
public class H5tchibookApplication {

	public static void main(String[] args) {
		SpringApplication.run(H5tchibookApplication.class, args);
	}

}
