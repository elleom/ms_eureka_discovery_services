package com.lm.usersservice.data.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author el_le
 * @since 07/03/2022 21:39
 */

@Entity
@Table(name= "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -3910017464214096980L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstname;
    @Column(nullable = false, length = 50)
    private String lastname;
    @Column(nullable = false, length = 120, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String encryptedPass;


}
