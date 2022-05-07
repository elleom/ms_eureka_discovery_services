package com.lm.usersservice.repository;

import com.lm.usersservice.data.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author el_le
 * @since 08/03/2022 18:33
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    /**
     *
     * @param email
     * @return UserEntity
     *
     * NOTE: when creating methods over this interface Spring Frameworks createss the query based on the naming
     * convention;
     *  update (modify a record) eg. updateById looks for the user with id x
     *  delete
     *  findBy (SELECT query)
     */
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}
