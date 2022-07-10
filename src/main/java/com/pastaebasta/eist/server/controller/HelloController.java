
package com.pastaebasta.eist.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller provided by Artemis.
 * This is useless and can be deleted later on.
 */
@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello World!";
    }



}
