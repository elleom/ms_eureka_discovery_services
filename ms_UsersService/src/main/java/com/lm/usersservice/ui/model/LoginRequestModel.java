package com.lm.usersservice.ui.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author el_le
 * @since 10/03/2022 19:39
 */

@Getter
@Setter
public class LoginRequestModel {

    private String email;
    private String password;

}
