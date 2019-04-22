package net.kprod.tooling.spring.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Our App SpringBootApplication
 */
@SpringBootApplication
@RestController
public class ToolingStarterApp {

    public static void main(String[] args) {
        SpringApplication.run(ToolingStarterApp.class, args);
    }
}