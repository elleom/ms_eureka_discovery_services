package com.lm.usersservice.service;

import com.lm.usersservice.data.model.UserEntity;
import com.lm.usersservice.repository.UserRepository;
import com.lm.usersservice.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author el_le
 * @since 07/03/2022 20:24
 */

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper mapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        //creates mapper and set strategy
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        userDto.setEncryptedPass(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userDto.setUserId(UUID.randomUUID().toString());

        // dto to entity
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        UserEntity savedUser = userRepository.save(userEntity);

        //entity to dto
        userDto = mapper.map(savedUser, UserDto.class);
        return userDto;
    }
}
