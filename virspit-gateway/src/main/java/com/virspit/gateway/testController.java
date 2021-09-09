package com.virspit.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping
    public String test() {
        return "Gateway 8080 test";
    }
}
