package com.lyc.train;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyc
 */
@RestController
public class TestController {



    @GetMapping("/hello")
    public String hello() {
        return "Hello world 123";
    }


}
