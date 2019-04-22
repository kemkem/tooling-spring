package net.kprod.tooling.spring.app.controller;

import net.kprod.tooling.spring.app.data.Response;
import net.kprod.tooling.spring.starter.service.DummyService;
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

    //Tooling starter service
    @Autowired
    private DummyService dummyService;

    /**
     * Returns a json object {@link Response} with {@link DummyService#foo} response
     * @return Json message
     * @throws Exception if something fails
     */
    @RequestMapping(value = "/foo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> foo() throws Exception {
        return ResponseEntity.ok(new Response(
                dummyService.foo("bar")));
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
