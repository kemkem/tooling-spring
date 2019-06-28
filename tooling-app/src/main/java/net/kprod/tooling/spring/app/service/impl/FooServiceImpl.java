package net.kprod.tooling.spring.app.service.impl;

import net.kprod.tooling.spring.app.data.Response;
import net.kprod.tooling.spring.app.service.AsyncService;
import net.kprod.tooling.spring.app.service.FooService;
import net.kprod.tooling.spring.commons.exception.ServiceException;
import net.kprod.tooling.spring.starter.data.bean.MonitoringData;
import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl implements FooService {
    private Logger LOG = LoggerFactory.getLogger(FooService.class);

    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private AsyncService asyncService;

    @Override
    public Response foo() throws ServiceException {
        LOG.info("foo method");
        try{
          Thread.sleep(1000);
        } catch (Exception e) {
            LOG.error("sleep have gone wrong");
        }
        return new Response("bar");
    }

    @Override
    public Response bar() throws ServiceException {
        LOG.info("bar method");
        asyncService.asyncProcess(monitoringService.getCurrentMonitoringData());
        try{
            Thread.sleep(500);
        } catch (Exception e) {
            LOG.error("sleep have gone wrong");
        }
        return new Response("processing");
    }
}
