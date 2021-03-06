package net.kprod.tooling.spring.starter.async;

import net.kprod.tooling.spring.commons.exception.ServiceException;
import net.kprod.tooling.spring.starter.data.bean.AsyncResult;
import net.kprod.tooling.spring.starter.data.bean.MonitoringData;
import net.kprod.tooling.spring.starter.service.MonitoringService;

import java.util.function.Supplier;

/**
 * Async execution wrapper
 * Used to embed async process calls in monitoring context
 * See {@link ControllerLogAsyncAspect}
 */
public class SupplyAsync implements Supplier {
        private MonitoringData monitorData;
        private AsyncRunnable runnable;
        private MonitoringService monitoringService;

    /**
     * Constructor
     * @param monitoringService
     * @param runnable
     */
    public SupplyAsync(MonitoringService monitoringService, MonitoringData monitorData, AsyncRunnable runnable) throws ServiceException {
        if(monitoringService == null ||
                monitorData == null ||
                runnable == null) {
            //FIXME
            throw new ServiceException("All parameters must be defined");
        }
        this.monitoringService = monitoringService;
        this.monitorData = monitorData;
        this.runnable = runnable;
    }


    /**
     * Async execution supplier
     * Execute runnable method and returns execution time
     * @return execution time in milliseconds
     */
    @Override
    public Object get() {
        monitoringService.keep(monitorData,"async");
        long start = System.currentTimeMillis();

        try {
            runnable.runThrows();
        } catch (Exception e) {
            return AsyncResult.failure(System.currentTimeMillis() - start, e);
        }

        return AsyncResult.success(System.currentTimeMillis() - start);
    }
}

