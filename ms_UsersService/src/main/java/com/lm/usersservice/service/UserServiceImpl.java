package com.lm.usersservice.service;

import com.lm.usersservice.data.model.UserEntity;
import com.lm.usersservice.repository.UserRepository;
import com.lm.usersservice.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
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

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        UserDto userDto =  mapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto getUserDetailsById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        UserDto userDto = mapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(),
                userEntity.getEncryptedPass(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }

}
