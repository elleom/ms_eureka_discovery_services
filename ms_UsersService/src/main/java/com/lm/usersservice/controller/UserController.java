package com.lm.usersservice.controller;

import com.lm.usersservice.ui.model.User;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping(value = "/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
