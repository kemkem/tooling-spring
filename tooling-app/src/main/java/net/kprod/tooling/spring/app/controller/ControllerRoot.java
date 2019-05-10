package net.kprod.tooling.spring.app.controller;

import net.kprod.tooling.spring.app.data.Response;
import net.kprod.tooling.spring.app.service.FooService;
import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * A test controller
 */
@RestController
@RequestMapping("/")
public class ControllerRoot {
    private Logger LOG = LoggerFactory.getLogger(ControllerRoot.class);

    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private FooService fooService;

    /**
     * Returns a json object {@link Response} with {@link FooService#foo} response
     * @return Json message
     * @throws Exception if something fails
     */
    @RequestMapping(value = "/foo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> foo() throws Exception {
        //monitoringService.start(this.getClass().getSimpleName() + "#foo");
        Response response = fooService.foo();
        LOG.info("Response [{}]", response.getMessage());
        //monitoringService.end();
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a json object {@link Response} with {@link FooService#bar} response
     * @return Json message
     * @throws Exception if something fails
     */
    @RequestMapping(value = "/bar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> bar() throws Exception {
        //monitoringService.start(this.getClass().getSimpleName() + "#bar");
        Response response = fooService.bar();
        LOG.info("Response [{}]", response.getMessage());
        //monitoringService.end();
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a json object {@link Response} with {@link FooService#bar} response
     * @return Json message
     * @throws Exception if something fails
     */
    @RequestMapping(value = "/baz", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> baz() throws Exception {
        //monitoringService.start(this.getClass().getSimpleName() + "#bar");
        Response response = fooService.baz();
        LOG.info("Response [{}]", response.getMessage());
        //monitoringService.end();
        return ResponseEntity.ok(response);
    }

    /**
     * Force an exception to rise
     * @return Json exception message
     * @throws Exception if something fails (it does !)
     */
    @RequestMapping(value = "/exception", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> exception() throws Exception {
        throw new NullPointerException("bang");
    }

}
