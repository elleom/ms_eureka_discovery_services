package com.lm.usersservice.controller;

import com.lm.usersservice.service.UserService;
import com.lm.usersservice.shared.UserDto;
import com.lm.usersservice.ui.model.User;
import com.lm.usersservice.ui.model.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author el_le
 * @since 01/03/2022 19:07
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final Environment env;
    private final UserService userService;

    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/status/check")
    public String getStatus() {
        System.out.println(env.getProperty("token.secret"));
        return "Online: \nport=" + env.getProperty("local.server.port") + ", with token = " + env.getProperty("token.secret") ;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody User user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto savedUserDto = userService.createUser(userDto);
        UserResponseModel userResponseModel = mapper.map(savedUserDto, UserResponseModel.class);
        return new ResponseEntity<>(userResponseModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseModel> getUserDetails(@PathVariable String userId){
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = userService.getUserDetailsById(userId);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);

        return new ResponseEntity<>(userResponseModel, HttpStatus.OK);
    }

}
