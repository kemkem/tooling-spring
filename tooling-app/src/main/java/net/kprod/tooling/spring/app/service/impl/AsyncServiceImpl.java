package net.kprod.tooling.spring.app.service.impl;

import net.kprod.tooling.spring.app.service.AsyncService;
import net.kprod.tooling.spring.starter.data.bean.AsyncResult;
import net.kprod.tooling.spring.starter.data.bean.MonitoringData;
import net.kprod.tooling.spring.starter.data.bean.SupplyAsync;
import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncServiceImpl implements AsyncService {
    private Logger LOG = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private MonitoringService monitoringService;

    @Async
    @Override
    public CompletableFuture<AsyncResult> asyncProcess(MonitoringData monitoringData) {
        SupplyAsync sa = new SupplyAsync(monitoringService, monitoringData, () -> runAsyncProcess());
        return CompletableFuture.supplyAsync(sa);
    }

    private void runAsyncProcess() {
        LOG.info("asyncProcess method");
        try{
            Thread.sleep(2000);
        } catch (Exception e) {
            LOG.error("sleep have gone wrong");
        }
        LOG.info("async processing completed");

    }
}