package net.kprod.tooling.spring.starter.service;

import net.kprod.tooling.spring.starter.data.bean.MonitoringData;

/**
 * Monitoring service
 * Control process monitoring
 */
public interface MonitoringService {
    /**
     * Start process monitoring
     * @param controllerName controller Spring name
     * @param methodName controller method name
     * @return processId
     */
    String start(String controllerName, String methodName);

    /**
     * Continue monitoring, in case of @Async for example
     * @param suffix process suffix
     * @return processId
     */
    String keep(MonitoringData monitoringData, String suffix);

    /**
     * Stop process monitoring
     * @param elsapedTime process execution time in ms
     */
    void end(long elsapedTime);

    MonitoringData getCurrentMonitoringData();
}
