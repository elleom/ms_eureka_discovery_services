package com.lm.usersservice.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author el_le
 * @since 01/03/2022 19:07
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Environment env;

    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping("/status/check")
    public String getStatus(){

        return "Online: \nport=" + env.getProperty("local.server.port");
    }
}
