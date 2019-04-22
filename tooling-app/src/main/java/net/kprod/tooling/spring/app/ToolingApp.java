package net.kprod.tooling.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Our App SpringBootApplication
 */
@SpringBootApplication
@RestController
public class ToolingApp {

    public static void main(String[] args) {
        SpringApplication.run(ToolingApp.class, args);
    }
}