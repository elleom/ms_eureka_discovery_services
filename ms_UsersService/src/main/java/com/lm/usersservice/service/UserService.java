package com.lm.usersservice.service;

import com.lm.usersservice.shared.UserDto;

/**
 * @author el_le
 * @since 07/03/2022 20:22
 */
public interface UserService {

    UserDto createUser(UserDto userDto);
}
