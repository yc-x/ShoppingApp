package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.User;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.user.UserRequest;
import org.example.shoppingapp.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    //TODO: prevent sign up with same user name and email.
    @PostMapping("/signup")
    @ResponseBody
    public DataResponse createUser(@Valid @RequestBody UserRequest userRequest,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            return buildErrorContent(errors);
        }
        userService.createUser(
                User.builder()
                        .username(userRequest.getUsername())
                        .email(userRequest.getEmail())
                        .password(userRequest.getPassword())
                        .build()
        );
        return DataResponse.builder()
                .success(true)
                .message("User Created")
                .build();
    }

    private DataResponse buildErrorContent(List<FieldError> errors){
        StringBuilder errorMessage = new StringBuilder();
        errors.forEach(error -> errorMessage.append(error.getObjectName())
                .append(": ")
                .append(error.getDefaultMessage())
                .append("\n"));
        return DataResponse.getGeneralInvalidResponse(errorMessage.toString(),
                "User request not in correct format, please check your request.");
    }

}
