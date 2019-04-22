package net.kprod.tooling.spring.starter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Starter auto configuration class</p>
 * <p>Defines component scan in order to expose components to client projects</p>
 */
@Configuration
@ComponentScan("net.kprod.tooling.spring.starter")
public class ToolingAutoConfiguration {
}