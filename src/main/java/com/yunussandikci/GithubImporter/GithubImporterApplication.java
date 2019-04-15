package com.yunussandikci.GithubImporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


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
