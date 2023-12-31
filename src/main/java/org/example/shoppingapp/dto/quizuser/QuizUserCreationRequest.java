package org.example.shoppingapp.dto.quizuser;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizUserCreationRequest {

    @NotBlank(message = "username cannot be blank")
    private String username;
    private String lastname;
    private String firstname;
    @NotBlank(message = "password cannot be blank")
    private String password;
    boolean isActive;
}