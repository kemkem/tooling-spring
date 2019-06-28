package net.kprod.tooling.spring.app.service;

import net.kprod.tooling.spring.commons.exception.ServiceException;

public interface AsyncService {
    void asyncProcess() throws ServiceException;
}
