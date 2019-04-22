package net.kprod.tooling.spring.starter.controller;

import net.kprod.tooling.spring.commons.exception.HttpServiceException;
import net.kprod.tooling.spring.commons.log.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Handles Spring "White Label" Errors</p>
 * <p>Error is translated to {@link HttpServiceException} and thrown</p>
 * <p>Exception are then caught with dedicated handlet @see {@link RestExceptionHandler}</p>
 */
@RestController("ErrorController")
public class ControllerCustomError implements ErrorController {
    private static final String PATH = "/error";
    public static final String SPRING_ERROR_MAP_ERROR = "error";
    public static final String SPRING_ERROR_MAP_PATH = "path";
    public static final String SPRING_ERROR_MAP_MESSAGE = "message";
    public static final String SPRING_ERROR_MAP_TRACE = "trace";

    @Value("${tooling.error.stacktrace.include:true}")
    private boolean includeStackTrace;

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    void error(WebRequest webRequest, HttpServletResponse response) throws Exception {
        //Get Spring http status
        HttpStatus status = HttpStatus.valueOf(response.getStatus());

        //Get Spring error attributes
        Optional<Map<String, Object>> mapErrorAttribute = this.getErrorAttributes(webRequest, includeStackTrace);

        //Default message
        String message = Msg.format("Spring error (no details) status [{}]", status.toString());

        //Message with attributes if present in map
        if(mapErrorAttribute.isPresent()) {
            message = Msg.format("Spring error [{}] status [{}] path [{}] message [{}] trace [{}]",
                    mapErrorAttribute.get().get(SPRING_ERROR_MAP_ERROR),
                    status.toString(),
                    mapErrorAttribute.get().get(SPRING_ERROR_MAP_PATH),
                    mapErrorAttribute.get().get(SPRING_ERROR_MAP_MESSAGE),
                    mapErrorAttribute.get().get(SPRING_ERROR_MAP_TRACE));
        }
        //Throw custom exception
        throw new HttpServiceException(status, message);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    /**
     * Get error attributes from request
     * @param webRequest request
     * @param includeStackTrace if true, include elements from stack trace
     * @return map of attributes
     */
    private Optional<Map<String, Object>> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        if(errorAttributes == null) {
            return Optional.empty();
        }
        return Optional.of(errorAttributes.getErrorAttributes(webRequest, includeStackTrace));
    }
}