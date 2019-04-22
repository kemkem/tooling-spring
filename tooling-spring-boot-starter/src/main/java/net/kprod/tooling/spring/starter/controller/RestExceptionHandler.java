package net.kprod.tooling.spring.starter.controller;

import net.kprod.tooling.spring.commons.exception.HttpServiceException;
import net.kprod.tooling.spring.commons.log.Msg;
import net.kprod.tooling.spring.starter.data.response.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>This class annotated with {@link ControllerAdvice} catch all exceptions</p>
 * <p>Allows a custom processing</p>
 * <p>Excepts {@link net.kprod.tooling.spring.commons.exception.ServiceException} or {@link HttpServiceException}</p>
 * <p>But all others {@link Exception} are also processed</p>
 */
@ControllerAdvice
public class RestExceptionHandler {
    public static final String STACKTRACE_UNAVAILABLE_MESSAGE = "unavailable";
    private Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Value("${tooling.error.stacktrace.include:false}")
    private boolean includeStackTrace;

    /**
     * Handle {@link HttpServiceException} exceptions
     * @param httpServiceExc {@link HttpServiceException} exception
     * @return {@link ResponseException} within a {@link ResponseEntity}
     */
    @ExceptionHandler(value = HttpServiceException.class)
    public ResponseEntity<ResponseException> handleHttpException(HttpServiceException httpServiceExc) {
        return createResponse(httpServiceExc, httpServiceExc.getStatus(), httpServiceExc);
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

        return createResponse(translatedException, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    /**
     * Create error response
     * @param e exception
     * @param httpStatus http status
     * @param parentException origin exception before rewriting
     * @return created response entity
     */
    private ResponseEntity<ResponseException> createResponse(HttpServiceException e, HttpStatus httpStatus, Exception parentException) {
        //Create response
        ResponseException responseException = this.processException(e, parentException);
        //Return response entity with proper status
        return ResponseEntity.status(httpStatus).body(responseException);
    }

    /**
     * Transform exception to a {@link ResponseException}
     * @param translatedException exception
     * @param parentException origin exception before rewriting
     * @return response exception object
     */
    private ResponseException processException(HttpServiceException translatedException, Exception parentException) {
        ResponseException responseException = new ResponseException();
        responseException.setMessage(Msg.format("Exception message [{}] translated to [{}] status [{}]",
                parentException.getMessage(),
                translatedException.getMessage(),
                translatedException.getReason()));
        if(includeStackTrace) {
            responseException.setStacktrace(getStacktraceAsString(parentException));
        } else {
            responseException.setStacktrace(STACKTRACE_UNAVAILABLE_MESSAGE);
        }
        LOG.error(responseException.getMessage());

        return responseException;
    }

    /**
     * Transform exception stacktrace to a {@link String}
     * @param e exception with stacktrace
     * @return stacktrace as string
     */
    private String getStacktraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}