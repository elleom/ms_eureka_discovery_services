package com.lm.usersservice.service;

import com.lm.usersservice.shared.UserDto;

import java.util.UUID;

/**
 * @author el_le
 * @since 07/03/2022 20:24
 */
public class UserServiceImpl implements UserService {

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(UUID.randomUUID().toString());
        return null;
    }
}
