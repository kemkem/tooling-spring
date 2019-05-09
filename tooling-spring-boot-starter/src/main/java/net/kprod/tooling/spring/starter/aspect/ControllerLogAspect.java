package net.kprod.tooling.spring.starter.aspect;

import net.kprod.tooling.spring.starter.annotation.MonitoringDisable;
import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Aspect
 * Intercepts Controller and RestController methods using {@link org.springframework.web.bind.annotation.RequestMapping} annotations
 * Ignored if {@link MonitoringDisable} annotation is present
 */
@Aspect
@Component
public class ControllerLogAspect {

    public static final String SERVICENAME_SEPARATOR = ".";
    @Autowired
    private MonitoringService monitoringService;

    /**
     * RequestMapping annotation interceptor
     * Before execution, initiate monitoring
     * After execution, report end of process with execution time
     * @param joinPoint AOP jointPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)"
            + " && !@annotation(net.kprod.tooling.spring.starter.annotation.MonitoringDisable)" )
    public Object controllerInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        //serviceName is composed of Controller name and Controller method name
        //get the method name
        String methodName = joinPoint.getSignature().getName();

        //get the controller name ; it could be either a RestController or a Controller
        String controllerName = null;
        Controller controllerAnnotation = joinPoint.getTarget().getClass().getAnnotation(Controller.class);
        RestController restControllerAnnotation = joinPoint.getTarget().getClass().getAnnotation(RestController.class);
        if(restControllerAnnotation != null) {
            controllerName = restControllerAnnotation.value();
        } else if(controllerAnnotation != null) {
            controllerName = controllerAnnotation.value();
        }
        if (StringUtils.isEmpty(controllerName)) {
            controllerName = joinPoint.getTarget().getClass().getSimpleName();
        }

        StringBuilder sbServiceName = new StringBuilder();
        sbServiceName
                .append(controllerName)
                .append(SERVICENAME_SEPARATOR)
                .append(methodName);


        //MonitoringData monitoringData = new MonitoringData.Builder(serviceName).build();
        //monitoringService.initMonitoring(monitoringData);

        //keep current time
        long start = System.currentTimeMillis();
        //start monitoring
        monitoringService.start(sbServiceName.toString());
        //execute controller method
        Object proceed = joinPoint.proceed();
        //get execution time
        Long elapsedTime = System.currentTimeMillis() - start;

        //monitoringService.reportEndOfProcess(executionTime);
        monitoringService.end(elapsedTime);

        return proceed;
    }
}