package com.lyc.batch.controller;

import com.lyc.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyc
 */
@RestController
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    private BusinessFeign businessFeign;

    @GetMapping("/hello")
    public String hello() {
        LOG.info(businessFeign.hello());
        return "Hello batch";
    }

}
