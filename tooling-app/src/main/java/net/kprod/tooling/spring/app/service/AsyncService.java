package net.kprod.tooling.spring.app.service;

import net.kprod.tooling.spring.commons.exception.ServiceException;
import net.kprod.tooling.spring.starter.data.bean.AsyncResult;
import net.kprod.tooling.spring.starter.data.bean.MonitoringData;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {
    CompletableFuture<AsyncResult> asyncProcess(MonitoringData monitoringData) throws ServiceException;
    CompletableFuture<AsyncResult> asyncProcessError(MonitoringData monitoringData) throws ServiceException;
}
