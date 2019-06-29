package net.kprod.tooling.spring.app.service;

import net.kprod.tooling.spring.app.data.Response;
import net.kprod.tooling.spring.commons.exception.ServiceException;

public interface FooService {
    Response foo() throws ServiceException;
    Response bar() throws ServiceException;
    Response barError() throws ServiceException;
}
