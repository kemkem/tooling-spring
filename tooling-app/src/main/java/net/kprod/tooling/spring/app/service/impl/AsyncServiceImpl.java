package net.kprod.tooling.spring.app.service.impl;

import net.kprod.tooling.spring.app.service.AsyncService;
import net.kprod.tooling.spring.commons.exception.ServiceException;
import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {
    private Logger LOG = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private MonitoringService monitoringService;

    @Async
    @Override
    public void asyncProcess() throws ServiceException {
        LOG.info("asyncProcess method");
        try{
            Thread.sleep(2000);
        } catch (Exception e) {
            LOG.error("sleep have gone wrong");
        }
        LOG.info("async processing completed");
    }
}
