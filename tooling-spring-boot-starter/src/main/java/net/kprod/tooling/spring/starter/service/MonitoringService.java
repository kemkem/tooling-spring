package net.kprod.tooling.spring.starter.service;

/**
 * Monitoring service
 * Control process monitoring
 */
public interface MonitoringService {
    /**
     * Start process monitoring
     * @param processName process name
     * @return processId
     */
    String start(String processName);

    /**
     * Stop process monitoring
     */
    void end();
}
