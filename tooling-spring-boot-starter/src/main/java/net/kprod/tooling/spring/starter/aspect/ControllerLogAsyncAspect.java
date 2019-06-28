package net.kprod.tooling.spring.starter.aspect;

import net.kprod.tooling.spring.starter.annotation.MonitoringDisable;
import net.kprod.tooling.spring.starter.data.bean.AsyncResult;
import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Async Method Aspect
 * Intercepts asynchronous methods using {@link org.springframework.scheduling.annotation.Async} annotations
 * Interception is ignored if {@link MonitoringDisable} annotation is present
 * A sub method must embed process, and must be called using {@link SupplyAsync} class
 */
@Aspect
@Component
public class ControllerLogAsyncAspect {
    @Autowired
    private MonitoringService monitoringService;

    private Logger LOG = LoggerFactory.getLogger(ControllerLogAsyncAspect.class);

    /**
     * Async annotation interceptor
     * Execute function and expects a {@link CompletableFuture<Long>}
     * After execution, report end of process with execution time
     * @param joinPoint AOP jointPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(org.springframework.scheduling.annotation.Async)"
            + " && !@annotation(net.kprod.tooling.spring.starter.annotation.MonitoringDisable)")
    public void asyncMethodInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        if(proceed instanceof CompletableFuture) {
                CompletableFuture future = (CompletableFuture) proceed;
                future.thenAccept(futureReturnValue -> {

                    if (futureReturnValue instanceof AsyncResult) {
                        AsyncResult result = (AsyncResult) futureReturnValue;
                        if(result.isSuccessful()) {
                            monitoringService.end(result.getRunTime());
                        } else {
                            //monitoringService.processException(result.getException());
                        }
                    }
                });
        } else {
            LOG.error("Async method does not return CompletableFuture");
        }
    }
}