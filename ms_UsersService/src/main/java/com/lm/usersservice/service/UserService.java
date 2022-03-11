package com.lm.usersservice.service;

import com.lm.usersservice.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author el_le
 * @since 07/03/2022 20:22
 */
public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUserDetailsByEmail(String email);
}
