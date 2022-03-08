package com.lm.usersservice.repository;

import com.lm.usersservice.data.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author el_le
 * @since 08/03/2022 18:33
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
