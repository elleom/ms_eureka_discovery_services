package com.lm.usersservice.service;

import com.lm.usersservice.data.feign.AlbumsServiceClient;
import com.lm.usersservice.data.model.UserEntity;
import com.lm.usersservice.repository.UserRepository;
import com.lm.usersservice.shared.UserDto;
import com.lm.usersservice.ui.model.AlbumResponseModel;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
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
//    private final RestTemplate restTemplate;
    private final AlbumsServiceClient albumsServiceClient;
    private final Environment env;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AlbumsServiceClient albumsServiceClient, Environment env) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.restTemplate = restTemplate;
        this.albumsServiceClient = albumsServiceClient;
        this.env = env;
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
        mapper = new ModelMapper();
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        UserDto userDto =  mapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        mapper = new ModelMapper();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        final String ALBUMS_URL = String.format(env.getProperty("albums.url"), userDto.getUserId());

//        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(
//                ALBUMS_URL,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<AlbumResponseModel>>() {});
        List<AlbumResponseModel> albumsList = null;
        try {
            albumsList = albumsServiceClient.getAlbums(userId);
        } catch (FeignException e) {
           logger.error(e.getMessage());
        }
        userDto.setAlbumsList(albumsList);

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
