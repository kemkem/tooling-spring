package net.kprod.tooling.spring.starter.service.impl;

import net.kprod.tooling.spring.starter.service.DummyService;
import org.springframework.stereotype.Service;

/** {@inheritDoc} */
@Service
public class DummyServiceImpl implements DummyService {

    /** {@inheritDoc} */
    @Override
    public String foo(String name) {
        return "foo " + name;
    }
}
