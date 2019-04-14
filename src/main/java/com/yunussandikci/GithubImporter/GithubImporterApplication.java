package com.yunussandikci.GithubImporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class GithubImporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubImporterApplication.class, args);
	}

	@RequestMapping("/")
	public ResponseEntity home() {
		return ResponseEntity.ok("hello");
	}

}
