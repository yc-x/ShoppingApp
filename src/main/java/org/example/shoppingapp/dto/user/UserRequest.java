package org.example.shoppingapp.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserRequest {
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    private String password;    // TODO: create encrypted password
}
