package net.kprod.tooling.spring.app.service.impl;

import net.kprod.tooling.spring.app.data.Response;
import net.kprod.tooling.spring.app.service.FooService;
import net.kprod.tooling.spring.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl implements FooService {
    private Logger LOG = LoggerFactory.getLogger(FooService.class);

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
        try{
            Thread.sleep(2000);
        } catch (Exception e) {
            LOG.error("sleep have gone wrong");
        }
        return new Response("baz");
    }
}
