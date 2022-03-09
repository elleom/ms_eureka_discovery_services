package com.lm.usersservice.ui.model;

import lombok.*;

/**
 * @author el_le
 * @since 09/03/2022 19:43
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {

    private String firstname;
    private String lastname;
    private String email;
    private String userID;

}
