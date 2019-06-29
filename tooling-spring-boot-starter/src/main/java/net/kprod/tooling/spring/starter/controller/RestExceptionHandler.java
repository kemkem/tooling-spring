package net.kprod.tooling.spring.starter.controller;

import net.kprod.tooling.spring.commons.exception.HttpServiceException;
import net.kprod.tooling.spring.commons.log.Msg;
import net.kprod.tooling.spring.starter.data.response.ResponseException;
import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * <p>This class annotated with {@link ControllerAdvice} catch all exceptions</p>
 * <p>Allows a custom processing</p>
 * <p>Excepts {@link net.kprod.tooling.spring.commons.exception.ServiceException} or {@link HttpServiceException}</p>
 * <p>But all others {@link Exception} are also processed</p>
 */
@ControllerAdvice
public class RestExceptionHandler {
    private Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Autowired
    private MonitoringService monitoringService;

    /**
     * Handle {@link HttpServiceException} exceptions
     * @param httpServiceExc {@link HttpServiceException} exception
     * @return {@link ResponseException} within a {@link ResponseEntity}
     */
    @ExceptionHandler(value = HttpServiceException.class)
    public ResponseEntity<ResponseException> handleHttpException(HttpServiceException httpServiceExc) {
        return monitoringService.createErrorResponse(httpServiceExc, httpServiceExc);
    }

    /**
     * Handle {@link Exception} unexpected exceptions
     * @param exception {@link Exception} exception
     * @return {@link ResponseException} within a {@link ResponseEntity}
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseException> handleUnexpectedException(Exception exception) {
        //Translate exception into a HttpServiceException
        //This includes Http status 500 as this exception might be unexcepted
        HttpServiceException translatedException = new HttpServiceException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                Msg.format("Unexpected exception [{}:{}]",
                        exception.getClass().getSimpleName(),
                        exception.getMessage()),
                exception);

        return monitoringService.createErrorResponse(exception, translatedException);
    }
}