package net.kprod.tooling.spring.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Disable AOP monitoring when affected to a controller method with {@link org.springframework.web.bind.annotation.RequestMapping} annotation
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitoringDisable {
}