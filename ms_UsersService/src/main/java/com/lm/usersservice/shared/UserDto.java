package com.lm.usersservice.shared;

import lombok.*;

import java.io.Serializable;

/**
 * @author el_le
 * @since 07/03/2022 20:31
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    private static final long serialVersionUID = 1149672779392564839L;

    private String id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String userId;
    private String encryptedPass;

}

