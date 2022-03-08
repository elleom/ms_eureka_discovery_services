package com.lm.usersservice.controller;

import com.lm.usersservice.service.UserService;
import com.lm.usersservice.shared.UserDto;
import com.lm.usersservice.ui.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
    private final UserService userService;

    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/status/check")
    public String getStatus() {
        return "Online: \nport=" + env.getProperty("local.server.port");
    }

    @PostMapping(value = "/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto savedUserDto = userService.createUser(userDto);
        user = mapper.map(savedUserDto, User.class);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
