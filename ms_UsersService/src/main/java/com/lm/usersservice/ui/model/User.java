package com.lm.usersservice.ui.model;

import lombok.*;

import javax.validation.constraints.*;

/**
 * @author el_le
 * @since 07/03/2022 19:26
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Size(min = 3, max = 20, message = "Firstname cannot be under 3 digits or over 20")
    @NotNull(message = "Please provide a firstname")
    private String firstname;

    @Size(min = 3, max = 20, message = "Lastname cannot be under 3 digits or over 20 digits")
    @NotNull(message = "Please provide a lastname")
    private String lastname;

    @Size(min = 8, max = 16, message = "password cannot be under 8 digits or over 16 digits")
    @NotNull(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Email must be provided")
    @Email(message = "Not an email")
    private String email;
}
